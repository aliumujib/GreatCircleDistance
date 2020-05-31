package com.aliumujib.greatcircledistance.lib.distances

interface IDistanceCalc {

    fun findDistanceInKm(originLatitude: Double, originLongitude: Double, destinationLatitude: Double, destinationLongitude: Double): Double

}