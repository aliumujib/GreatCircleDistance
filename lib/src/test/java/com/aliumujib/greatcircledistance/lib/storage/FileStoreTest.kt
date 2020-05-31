package com.aliumujib.greatcircledistance.lib.storage

import com.aliumujib.greatcircledistance.lib.utils.DummyDataFactory
import com.google.common.truth.Truth.*
import org.junit.Before
import org.junit.Test
import java.io.*
import java.nio.file.Files


class FileStoreTest {

    private val fileStore = FileStore()

    @Before
    fun setUp() {

    }

    @Test
    fun `assert that storeResults returns non null file URL when everything is correct`() {
        val customerList = DummyDataFactory.generateDummyCustomerList(10)
        var file: File? = null
        try {
            file = File.createTempFile("tmp", null)
            val url = fileStore.storeResults(customerList, file)
            assertThat(url).isNotNull()
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        } finally {
            file?.delete()
        }
    }

    @Test
    fun `assert that storeResults returns null file URL when an error occurred`() {
        val customerList = DummyDataFactory.generateDummyCustomerList(10)
        val file = File("")
        val url = fileStore.storeResults(customerList, file)
        assertThat(url).isNull()
    }

    @Test
    fun `assert that storeResults contains correct data`() {
        val customerList = DummyDataFactory.generateDummyCustomerList(10)
        var file: File? = null
        try {
            file = File.createTempFile("tmp", null)
            val url = fileStore.storeResults(customerList, file)
            val bufferedReader =  BufferedReader(FileReader(url))
            val fileContentCount = bufferedReader.lineSequence().count()
            assertThat(customerList.size).isEqualTo(fileContentCount)
        } catch (ioe: IOException) {
            ioe.printStackTrace()
        } finally {
            file?.delete()
        }
    }

}