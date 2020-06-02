package com.aliumujib.greatcircledistance.lib.main

import com.aliumujib.greatcircledistance.lib.distances.IDistanceCalc
import com.aliumujib.greatcircledistance.lib.exceptions.StorageErrorException
import com.aliumujib.greatcircledistance.lib.models.Customer
import com.aliumujib.greatcircledistance.lib.models.Result
import com.aliumujib.greatcircledistance.lib.parser.ICustomerParser
import com.aliumujib.greatcircledistance.lib.storage.IStore
import com.aliumujib.greatcircledistance.lib.utils.DummyDataFactory
import com.google.common.truth.Truth.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import konveyor.base.randomBuild
import org.junit.Before
import org.junit.Test


class GreatCircleDistanceCalculatorTest {

    @MockK
    private lateinit var parser: ICustomerParser
    @MockK
    private lateinit var store: IStore
    @MockK
    private lateinit var distanceCalc: IDistanceCalc

    private lateinit var greatCircleDistanceCalculator: GreatCircleDistanceCalculator

    private val inputFileURL = this.javaClass.classLoader.getResource("customers.txt").path
    private val saveDir = "testDir"

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        greatCircleDistanceCalculator = GreatCircleDistanceCalculator(parser, store, distanceCalc)
    }

    @Test
    fun `check that calling fetchEligibleCustomers calls correct methods`() {
        val customerList = DummyDataFactory.generateDummyCustomerList(10)
        stubParseResults(customerList)
        val radius = (10..100).random().toDouble()
        greatCircleDistanceCalculator.invoke(IOConfig(inputFileURL, saveDir), PointOfInterest(randomBuild(), randomBuild()), radius)
        verify(atLeast = 1) {
            parser.parseCustomers(any())
            distanceCalc.findDistanceInKm(any(), any(), any(), any())
            store.storeResults(any(), any())
        }
    }

    @Test
    fun `check that calling fetchEligibleCustomers returns results within the specified radius`() {
        val customerList = DummyDataFactory.generateDummyCustomerList(10)
        stubParseResults(customerList)
        val radius = (10..100).random().toDouble()
        val results = greatCircleDistanceCalculator.invoke(IOConfig(inputFileURL, saveDir), PointOfInterest(randomBuild(), randomBuild()), radius)
        assertThat(results).isInstanceOf(Result.Success::class.java)
        val maximumDistance: Double? =
            (results as Result.Success).customerData.map { it.distance_from_location }.max()
        assertThat(maximumDistance).isAtMost(radius)
    }

    @Test
    fun `check that calling fetchEligibleCustomers returns sorted results`() {
        val customerList = DummyDataFactory.generateDummyCustomerList(10)
        stubParseResults(customerList)
        val radius = (10..100).random().toDouble()
        val results = greatCircleDistanceCalculator.invoke(IOConfig(inputFileURL, saveDir), PointOfInterest(randomBuild(), randomBuild()), radius)
        assertThat(results).isInstanceOf(Result.Success::class.java)
        assertThat((results as Result.Success).customerData).isInOrder(Comparator<Customer> { t, t2 ->
            when {
                t.user_id == t2.user_id -> {
                    0
                }
                t.user_id > t2.user_id -> 1
                else -> -1
            }
        })
    }

    @Test
    fun `check that calling makeResults returns correct exception types`() {
        val storageErrorResult: Result = greatCircleDistanceCalculator.makeResult(
            null,
            DummyDataFactory.generateDummyCustomerList(10))
        assertThat((storageErrorResult as Result.Error).error).isInstanceOf(StorageErrorException::class.java)
    }

    private fun stubParseResults(list: List<Customer>) {
        every {
            parser.parseCustomers(any())
        } returns list
    }


}