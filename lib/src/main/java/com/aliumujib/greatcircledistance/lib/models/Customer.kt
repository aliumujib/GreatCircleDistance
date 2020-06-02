package com.aliumujib.greatcircledistance.lib.models


data class Customer(
    val latitude: String,
    val longitude: String,
    val name: String,
    val user_id: Int,
    val distance_from_location: Double = Double.MAX_VALUE
)