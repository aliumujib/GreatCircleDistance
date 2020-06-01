package com.aliumujib.greatcircledistance.lib.models

sealed class Result {

    data class Error(val error: Throwable) : Result()
    data class Success(val customerData: List<Customer>, val outputFileURL: String) : Result()
    object Working : Result()

}