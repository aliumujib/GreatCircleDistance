package com.aliumujib.greatcircledistance.lib.di

import com.aliumujib.greatcircledistance.lib.distances.DistanceCalculator
import com.aliumujib.greatcircledistance.lib.distances.IDistanceCalc
import com.aliumujib.greatcircledistance.lib.parser.CustomerParser
import com.aliumujib.greatcircledistance.lib.parser.ICustomerParser
import com.aliumujib.greatcircledistance.lib.storage.FileStore
import com.aliumujib.greatcircledistance.lib.storage.IStore



fun di() = DI

interface DIComponent {
    val parser: ICustomerParser
    val store: IStore
    val distanceCalc: IDistanceCalc
}

object DI : DIComponent {
    override val parser: ICustomerParser
        get() = CustomerParser()
    override val store: IStore
        get() = FileStore()
    override val distanceCalc: IDistanceCalc
        get() = DistanceCalculator()
}