package com.aliumujib.greatcircledistance.lib.models

data class Result(val error: Throwable?=null,
                  val customerData:List<Customer>?=null,
                  val outputFileURl:String?=null)