package com.aliumujib.greatcircledistance.lib.models


data class Customer(
    val latitude: String,
    val longitude: String,
    val name: String,
    val user_id: Int,
    var distance_from_location: Double = Double.MAX_VALUE
)