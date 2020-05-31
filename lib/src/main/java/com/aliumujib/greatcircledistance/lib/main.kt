package com.aliumujib.greatcircledistance.lib

import com.aliumujib.greatcircledistance.lib.main.GreatCircleDistance

fun main(args: Array<String>) {
    val greatCircleDistance = GreatCircleDistance.getInstance()
    greatCircleDistance.init("/Users/aliumujib/AndroidStudioProjects/GreatCircleDistance/lib/src/main/resources")
    val results = greatCircleDistance.fetchEligibleCustomers(
        "/Users/aliumujib/AndroidStudioProjects/GreatCircleDistance/lib/src/main/resources/customers.txt",
        53.339428,
        -6.257664,
        100.0
    )
}