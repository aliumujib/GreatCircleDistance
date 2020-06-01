package com.aliumujib.greatcircledistance.lib.distances

import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import org.junit.Before
import org.junit.Test


class DistanceCalculatorTest {

    private val distanceCalc = DistanceCalculator()

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
    }


    /**
     * Checking method results against calculated distances obtained from
     * https://www.gpsvisualizer.com/calculators
     * */
    @Test
    fun `check that findDistanceInKm returns a distance with tolerable error rates`() {
        val expectedDistance1 = 41.816
        val result1: Double = distanceCalc.findDistanceInKm(52.986375, -6.043701, 53.339428, -6.257664)
        assertThat(result1).isWithin(1.0).of(expectedDistance1)

        val expectedDistance2 = 89.119
        val result2: Double = distanceCalc.findDistanceInKm(54.133333, -6.433333, 53.339428, -6.257664)
        assertThat(result2).isWithin(1.0).of(expectedDistance2)
    }

}