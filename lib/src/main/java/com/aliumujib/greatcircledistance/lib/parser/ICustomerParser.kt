package com.aliumujib.greatcircledistance.lib.parser

import com.aliumujib.greatcircledistance.lib.models.Customer
import java.io.BufferedReader

interface ICustomerParser {

    fun parseCustomers(reader: BufferedReader):List<Customer>

}