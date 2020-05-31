package com.aliumujib.greatcircledistance.data

import android.content.Context
import android.util.Log
import com.aliumujib.greatcircledistance.lib.main.GreatCircleDistance
import com.aliumujib.greatcircledistance.lib.models.Result
import java.io.BufferedReader
import java.io.FileNotFoundException


class CustomersRepository(resultsUrl:String,
                          private val context: Context,
                          private val greatCircleCalculator: GreatCircleDistance
) {

    init {
        greatCircleCalculator.init(resultsUrl)
    }

    fun fetchEligibleCustomers(destinationLatitude: Double, destinationLongitude: Double,  minDistance: Double): Result {
        val bufferedReader = readInputFile(context, "customers.txt")
        return if (bufferedReader!=null){
            val result = greatCircleCalculator.runQuery(bufferedReader, destinationLatitude, destinationLongitude, minDistance)
            result.customerData?.forEach {
                Log.d("MAIN", "$it")
            }
            result
        }else{
            Result(Throwable("There was an error fetching customer details"))
        }
    }

    private fun readInputFile(context: Context, inputFile: String): BufferedReader? {
        return try {
            context.assets.open(inputFile).bufferedReader()
        } catch (ex: FileNotFoundException) {
            ex.printStackTrace()
            null
        }
    }

}