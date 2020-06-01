package com.aliumujib.greatcircledistance.lib.exceptions

import java.lang.Exception

class StorageErrorException :Exception("There was an error storing eligible customer results")
class NoEligibleCustomerException(minDistance:Double) :Exception("There are no eligible customers within a $minDistance km radius")