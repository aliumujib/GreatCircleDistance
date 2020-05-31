package com.aliumujib.greatcircledistance

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliumujib.greatcircledistance.adapters.CustomerClickListener
import com.aliumujib.greatcircledistance.adapters.CustomerListAdapter
import com.aliumujib.greatcircledistance.adapters.ListSpacingItemDecorator
import com.aliumujib.greatcircledistance.databinding.ActivityMainBinding
import com.aliumujib.greatcircledistance.ext.dpToPx
import com.aliumujib.greatcircledistance.lib.GreatCircleDistance
import com.aliumujib.greatcircledistance.lib.models.Customer

import java.io.BufferedReader
import java.io.FileNotFoundException

class MainActivity : AppCompatActivity(), CustomerClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var rvAdapter: CustomerListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        setupRV()
        fetchData()
    }

    private fun fetchData() {
        val greatCircleCalculator = GreatCircleDistance.getInstance()
        greatCircleCalculator.init("be")
        readInputFile(this, "customers.txt")?.let { it ->
            val eligible = greatCircleCalculator.fetchEligibleCustomers(it, 53.339428, -6.257664, 100.0)
            eligible.forEach {
                Log.d("MAIN", "$it")
            }
            (binding.recyclerView.adapter as CustomerListAdapter).submitList(eligible)
        }
    }

    private fun setupRV() {
        rvAdapter = CustomerListAdapter(this)
        binding.recyclerView.apply {
            addItemDecoration(ListSpacingItemDecorator(context.dpToPx(32), context.dpToPx(16)))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = rvAdapter
        }
    }

    private fun readInputFile(context: Context, inputFile: String): BufferedReader? {
        return try {
            context.assets.open(inputFile).bufferedReader()
        } catch (ex: FileNotFoundException) {
            ex.printStackTrace()
            null
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun invoke(customer: Customer) {

    }

}