package com.aliumujib.greatcircledistance.lib.main

import com.aliumujib.greatcircledistance.lib.di.di
import com.aliumujib.greatcircledistance.lib.distances.IDistanceCalc
import com.aliumujib.greatcircledistance.lib.exceptions.GenericException
import com.aliumujib.greatcircledistance.lib.exceptions.StorageErrorException
import com.aliumujib.greatcircledistance.lib.models.Customer
import com.aliumujib.greatcircledistance.lib.models.Result
import com.aliumujib.greatcircledistance.lib.parser.ICustomerParser
import com.aliumujib.greatcircledistance.lib.storage.IStore
import java.io.File
import java.util.*

data class IOConfig(val inputFileDir: String, val outputFileDir: String)
data class PointOfInterest(val latitude: Double, val longitude: Double)


class GreatCircleDistanceCalculator(
    private val parser: ICustomerParser = di().parser,
    private val store: IStore = di().store,
    private val distanceCalc: IDistanceCalc = di().distanceCalc
) {


    /**
     * Method makes a [File] to which eligible customers are written.
     *
     * @param storageURI [String] path where file should be stored
     * @return The constructed file object [File]
     */
    private fun makeStorageFile(storageURI: String): File {
        val file = File(storageURI)
        if (!file.exists()) {
            file.mkdir()
        }
        val fileName = "results_${Calendar.getInstance().time.time}.txt"
        return File(file, fileName)
    }


    /**
     * Method takes an [IOConfig] containing the customer data file path and an output file path,
     * coordinates of a location and a minimum distance (radius) from that location then
     * returns a [List] of customers who are in the required radius.
     *
     * @param ioConfig [IOConfig] ready to spit some file contents
     * @param pointOfInterest [PointOfInterest]  latitude of destination
     * @param minDistance [Double] minimum distance from destination
     *
     * @return The [List] of customers within the required radius
     */

    fun invoke(ioConfig: IOConfig, pointOfInterest: PointOfInterest, minDistance: Double): Result {
        val bufferedReader = File(ioConfig.inputFileDir).bufferedReader()
        val customerList = parser.parseCustomers(bufferedReader).map {
            it.copy(distance_from_location = distanceCalc.findDistanceInKm(
                it.latitude.toDouble(), it.longitude.toDouble(),
                pointOfInterest.latitude, pointOfInterest.longitude
            ))
        }.filter {
            it.distance_from_location <= minDistance
        }.sortedBy {
            //decided to sort at this point since it will be cheaper having already removed in-eligible customers
            it.user_id
        }
        var fileURL: String? = null
        if (customerList.isNotEmpty()) {
            fileURL = store.storeResults(customerList, makeStorageFile(ioConfig.outputFileDir))
        }
        return makeResult(fileURL, customerList)
    }


    fun makeResult(fileURL: String?, filteredResults: List<Customer>): Result {
        return if (fileURL != null && filteredResults.isNotEmpty()) {
            Result.Success(filteredResults, fileURL)
        } else if (fileURL == null && filteredResults.isNotEmpty()) {
            Result.Error(StorageErrorException())
        } else {
            Result.Error(GenericException())
        }
    }


}