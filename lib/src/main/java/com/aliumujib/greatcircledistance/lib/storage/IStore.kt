package com.aliumujib.greatcircledistance.lib.storage

import com.aliumujib.greatcircledistance.lib.models.Customer
import java.io.File

interface IStore {

    fun storeResults(list: List<Customer>,  resultDir: File): String?

}