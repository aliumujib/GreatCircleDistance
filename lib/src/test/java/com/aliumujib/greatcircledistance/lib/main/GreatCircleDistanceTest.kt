package com.aliumujib.greatcircledistance.lib.main

import com.aliumujib.greatcircledistance.lib.distances.IDistanceCalc
import com.aliumujib.greatcircledistance.lib.models.Customer
import com.aliumujib.greatcircledistance.lib.parser.ICustomerParser
import com.aliumujib.greatcircledistance.lib.storage.IStore
import com.aliumujib.greatcircledistance.lib.utils.DummyDataFactory
import com.google.common.truth.Truth
import com.google.common.truth.Truth.*
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import konveyor.base.randomBuild
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader
import java.lang.IllegalStateException


class GreatCircleDistanceTest {

    @MockK
    private lateinit var parser: ICustomerParser
    @MockK
    private lateinit var store: IStore
    @MockK
    private lateinit var distanceCalc: IDistanceCalc

    private lateinit var greatCircleDistance: GreatCircleDistance

    private lateinit var bufferedReader: BufferedReader

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)

        bufferedReader = this.javaClass.classLoader.getResourceAsStream("customers.txt")!!.bufferedReader()
        greatCircleDistance = GreatCircleDistance.newInstance(parser, store, distanceCalc)
    }

    @Test(expected = IllegalStateException::class)
    fun `check that calling fetchEligibleCustomers without having called init throws an exception`() {
        val results = greatCircleDistance.runQuery(bufferedReader, randomBuild(), randomBuild(), randomBuild())
        verify(exactly = 0) {
            parser.parseCustomers(bufferedReader)
            distanceCalc.findDistanceInKm(any(), any(), any(), any())
            store.storeResults(any(), any())
            bufferedReader.close()
        }
    }

    @Test
    fun `check that calling fetchEligibleCustomers calls correct methods and all results are within the specified radius`() {
        val customerList = DummyDataFactory.generateDummyCustomerList(10)
        stubParseResults(customerList)
        val saveDir = "testDir"
        val radius = (10..100).random().toDouble()
        greatCircleDistance.init(saveDir)
        val results = greatCircleDistance.runQuery(bufferedReader, 53.339428, -6.257664, radius)
        verify(exactly = 1) {
            parser.parseCustomers(bufferedReader)
            store.storeResults(any(), any())
            bufferedReader.close()
        }
        val maximumDistance :Double? = results.customerData?.map { it.distance_from_location }?.max()
        assertThat(maximumDistance).isAtMost(radius)
    }

    private fun stubParseResults(list: List<Customer>) {
        every {
            parser.parseCustomers(any())
        } returns list
    }


}