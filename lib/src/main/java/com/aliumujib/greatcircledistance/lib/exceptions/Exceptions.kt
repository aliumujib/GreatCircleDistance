package com.aliumujib.greatcircledistance.lib.exceptions

import java.lang.Exception

class StorageErrorException :Exception("There was an error storing eligible customer results")
class GenericException :Exception("An error occurred, please look at the program logs")