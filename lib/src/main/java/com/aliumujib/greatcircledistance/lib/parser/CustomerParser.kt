package com.aliumujib.greatcircledistance.lib.parser

import com.aliumujib.greatcircledistance.lib.models.Customer
import com.google.gson.Gson
import java.io.BufferedReader

class CustomerParser : ICustomerParser {

    /**
     * Method that iterates the content of a [BufferedReader], parses it and returns a list of customers
     * sorted by UserID
     *
     * @param reader [BufferedReader] ready to spit some file contents
     * @return The sorted list of customers
     */

    override fun parseCustomers(reader: BufferedReader): List<Customer> {
        val customerList: MutableList<Customer> = mutableListOf()
        val iterator = reader.lineSequence().iterator()
        val gSon = Gson()
        while (iterator.hasNext()) {
            val customer = iterator.next()
            customerList.add(gSon.fromJson(customer, Customer::class.java))
        }
        reader.close()
        return customerList
    }

}