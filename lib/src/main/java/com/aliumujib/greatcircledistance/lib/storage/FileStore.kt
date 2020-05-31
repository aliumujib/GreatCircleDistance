package com.aliumujib.greatcircledistance.lib.storage

import com.aliumujib.greatcircledistance.lib.models.Customer
import java.io.File
import java.io.FileWriter
import java.io.Writer
import java.util.*


class FileStore : IStore {

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