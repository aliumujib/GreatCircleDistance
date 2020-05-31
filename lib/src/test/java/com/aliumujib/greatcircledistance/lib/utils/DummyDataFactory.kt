package com.aliumujib.greatcircledistance.lib.utils

import com.aliumujib.greatcircledistance.lib.models.Customer
import konveyor.base.randomBuild

object DummyDataFactory {

    fun generateDummyCustomerList(count: Int): List<Customer> {
        val customerList = mutableListOf<Customer>()
        repeat(count) {
            customerList.add(generateDummyCustomer())
        }
        return customerList
    }

    private fun generateDummyCustomer():Customer{
        return Customer(
            randomBuild<Double>().toString(),
            randomBuild<Double>().toString(),
            randomBuild(),
            randomBuild(),
            randomBuild()
        )
    }

}