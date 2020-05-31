package com.aliumujib.greatcircledistance.lib.parser

import com.aliumujib.greatcircledistance.lib.models.Customer
import com.google.gson.Gson
import java.io.BufferedReader

class CustomerParser : ICustomerParser {

    override fun parseCustomers(reader: BufferedReader): List<Customer> {
        val customerList: MutableList<Customer> = mutableListOf()
        val iterator = reader.lineSequence().iterator()
        while (iterator.hasNext()) {
            val customer = iterator.next()
            customerList.add(Gson().fromJson(customer, Customer::class.java))
        }
        reader.close()
        return customerList.sortedBy {
            it.user_id
        }
    }

}