package com.aliumujib.greatcircledistance.lib.distances

import kotlin.math.*

class DistanceCalculator : IDistanceCalc {

    /**
     * Equatorial radius of the earth in Kilometers.
     */
    private val RADIUS_OF_EARTH = 6371

    /**
     *
     *
     * Method using the Haversine formula to calculate the great-circle distance
     * between two points on a sphere.
     *
     * @param originLatitude Latitude of the starting point
     * @param originLongitude Longitude of the starting point
     * @param destinationLatitude Latitude of the destination/end point
     * @param destinationLongitude Longitude of the destination/end point
     * @return The distance in Kilometers (Km)
     */

    override fun findDistanceInKm(originLatitude: Double, originLongitude: Double, destinationLatitude: Double, destinationLongitude: Double): Double {
        val deltaLatitude = Math.toRadians(destinationLatitude - originLatitude)
        val deltaLongitude = Math.toRadians(destinationLongitude - originLongitude)

        val radiansOriginLatitude = Math.toRadians(originLatitude)
        val radiansDestinationLatitude = Math.toRadians(destinationLatitude)
        // D and E are the 'sides' from the spherical triangle.
        val sideD = sin(deltaLatitude / 2).pow(2.0) + sin(deltaLongitude / 2).pow(2.0) * cos(radiansOriginLatitude) * cos(radiansDestinationLatitude)
        val sideE = 2 * asin(sqrt(sideD))
        return RADIUS_OF_EARTH * sideE
    }

}