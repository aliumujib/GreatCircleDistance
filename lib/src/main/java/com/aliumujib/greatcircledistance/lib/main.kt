package com.aliumujib.greatcircledistance.lib

import com.aliumujib.greatcircledistance.lib.main.GreatCircleDistanceCalculator
import com.aliumujib.greatcircledistance.lib.main.IOConfig
import com.aliumujib.greatcircledistance.lib.main.PointOfInterest
import com.aliumujib.greatcircledistance.lib.models.Result
import java.nio.file.Paths



const val DUBLIN_OFFICE_LAT = 53.339428
const val DUBLIN_OFFICE_LONG = -6.257664
const val REQUIRED_RADIUS = 100.0

fun main(args: Array<String>) {
    val workingDir = Paths.get("").toAbsolutePath().toString()
    val ioConfig = IOConfig("${workingDir}/lib/src/main/resources/customers.txt", "${workingDir}/lib/src/main/resources")
    val pointOfInterest = PointOfInterest(DUBLIN_OFFICE_LAT, DUBLIN_OFFICE_LONG)
    val results = GreatCircleDistanceCalculator().invoke(ioConfig,pointOfInterest, REQUIRED_RADIUS)
    printResults(results)
}

fun printResults(result: Result) {
    when (result) {
        is Result.Success -> {
            println("Successfully fetched eligible customers, please look at ${result.outputFileURL}")
        }
        is Result.Error -> {
            println(result.error.message)
        }
        else -> {
            print("Working on it ;)")
        }
    }
}


