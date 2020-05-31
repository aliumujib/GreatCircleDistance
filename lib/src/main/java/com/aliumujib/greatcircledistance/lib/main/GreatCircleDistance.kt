package com.aliumujib.greatcircledistance.lib.main

import com.aliumujib.greatcircledistance.lib.di.di
import com.aliumujib.greatcircledistance.lib.distances.IDistanceCalc
import com.aliumujib.greatcircledistance.lib.models.Result
import com.aliumujib.greatcircledistance.lib.parser.ICustomerParser
import com.aliumujib.greatcircledistance.lib.storage.IStore
import java.io.BufferedReader
import java.io.File
import java.util.*

class GreatCircleDistance(private val parser: ICustomerParser,
                          private val store:IStore,
                          private val distanceCalc: IDistanceCalc) {

    private var resultStorageDir: String? = null

    fun init(resultStorageDir: String) {
        this.resultStorageDir = resultStorageDir
    }

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
        val fileName ="results_${Calendar.getInstance().time.time}.txt"
        return File(file, fileName)
    }


    fun fetchEligibleCustomers(inputFileURL:String,destinationLatitude: Double, destinationLongitude: Double, minDistance: Double): Result? {
        val bufferedReader = File(inputFileURL).bufferedReader()
        return runQuery(bufferedReader, destinationLatitude, destinationLongitude, minDistance)
    }

    /**
     * Method takes a [BufferedReader] containing customer data, coordinates of a location and a minimum distance (radius) from that location then
     * returns a [List] of customers who are in the required radius.
     *
     * @param reader [BufferedReader] ready to spit some file contents
     * @param destinationLatitude [Double]  latitude of destination
     * @param destinationLongitude [Double]  longitude of destination
     * @param minDistance [Double] minimum distance from destination
     *
     * @return The [List] of customers within the required radius
     */


   fun runQuery(reader: BufferedReader, destinationLatitude: Double, destinationLongitude: Double, minDistance: Double) : Result {
        resultStorageDir?.let { storageDir->
            val customerList = parser.parseCustomers(reader)
            customerList.forEach {
                it.distance_from_location = distanceCalc.findDistanceInKm(it.latitude.toDouble(), it.longitude.toDouble(), destinationLatitude, destinationLongitude)
            }
           val fileURL =  store.storeResults(customerList, makeStorageFile(storageDir))
            val filteredResults = customerList.filter {
                it.distance_from_location <= minDistance
            }
            return Result(null,filteredResults, fileURL)
        } ?: throw IllegalStateException("You can't fetchEligibleCustomers until you call init ;0")
    }

    companion object{
        private val mainInstance =
            GreatCircleDistance(
                di().parser,
                di().store,
                di().distanceCalc
            )

        @JvmStatic
        fun getInstance()  =
            mainInstance

        @JvmStatic
        fun newInstance(parser: ICustomerParser, store: IStore, distanceCalc: IDistanceCalc)  =
            GreatCircleDistance(
                parser,
                store,
                distanceCalc
            )
    }

}