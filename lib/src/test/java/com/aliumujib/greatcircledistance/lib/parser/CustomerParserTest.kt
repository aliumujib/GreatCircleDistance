package com.aliumujib.greatcircledistance.lib.parser

import com.aliumujib.greatcircledistance.lib.models.Customer
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader

class CustomerParserTest {

    private val customerParser = CustomerParser()
    private lateinit var bufferedReader: BufferedReader

    @Before
    fun setUp() {
        bufferedReader = this.javaClass.classLoader.getResourceAsStream("customers.txt")!!.bufferedReader()
    }

    @Test
    fun `assert that parseCustomers returns sorted data`() {
        val results = customerParser.parseCustomers(bufferedReader)
        assertThat(results).isInOrder(Comparator<Customer> { t, t2 ->
            when {
                t.user_id == t2.user_id -> {
                    0
                }
                t.user_id > t2.user_id -> 1
                else -> -1
            }
        })
    }

}