package com.aliumujib.greatcircledistance.lib.parser

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.io.BufferedReader

class CustomerParserTest {

    private val customerParser = CustomerParser()

    @Before
    fun setUp() {

    }

    /**
    * Created multiple buffered here because I wanted to programmatically fetch the line count and calling count() on a [BufferedReader] effectively
     * reads the file and makes content length == 0.
    **/

    @Test
    fun `assert that parseCustomers parses all possible records`() {
        val countReader:BufferedReader = this.javaClass.classLoader.getResourceAsStream("customers.txt")!!.bufferedReader()
        val lineCount:Int = countReader.lineSequence().count()

        val recordReader = this.javaClass.classLoader.getResourceAsStream("customers.txt")!!.bufferedReader()
        val customerRecords = customerParser.parseCustomers(recordReader)
        assertThat(lineCount).isEqualTo(customerRecords.size)
    }

}