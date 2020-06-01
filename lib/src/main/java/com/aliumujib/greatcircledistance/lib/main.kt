package com.aliumujib.greatcircledistance.lib

import com.aliumujib.greatcircledistance.lib.main.GreatCircleDistance
import java.nio.file.Paths

fun main(args: Array<String>) {
    val workingDir = Paths.get("").toAbsolutePath().toString()
    val greatCircleDistance = GreatCircleDistance.getInstance()
    greatCircleDistance.init("${workingDir}/lib/src/main/resources")
    val results = greatCircleDistance.fetchEligibleCustomers(
        "${workingDir}/lib/src/main/resources/customers.txt",
        53.339428, -6.257664, 100.0
    )
}