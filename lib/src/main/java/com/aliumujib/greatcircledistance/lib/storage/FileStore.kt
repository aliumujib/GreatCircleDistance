package com.aliumujib.greatcircledistance.lib.storage

import com.aliumujib.greatcircledistance.lib.models.Customer
import java.io.BufferedReader
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.util.*


class FileStore : IStore {


    /**
     * Method stores a list of customers as a [String] in a [File].
     *
     * @param list [List] of customers
     * @return The url of the stored file or null when an error occurs
     *
     */

    override fun storeResults(list: List<Customer>, resultDir: File): String? {
        return try {
            val writer = FileWriter(resultDir)
            list.forEach {
                writer.append(it.toString())
                writer.append('\n')
            }
            writer.flush()
            writer.close()
            resultDir.absolutePath
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

}