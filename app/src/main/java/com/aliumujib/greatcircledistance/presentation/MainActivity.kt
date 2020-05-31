package com.aliumujib.greatcircledistance.presentation

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aliumujib.greatcircledistance.R
import com.aliumujib.greatcircledistance.adapters.CustomerClickListener
import com.aliumujib.greatcircledistance.adapters.CustomerListAdapter
import com.aliumujib.greatcircledistance.adapters.ListSpacingItemDecorator
import com.aliumujib.greatcircledistance.databinding.ActivityMainBinding
import com.aliumujib.greatcircledistance.ext.dpToPx
import com.aliumujib.greatcircledistance.lib.models.Customer

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

    }

    private fun setupRV() {
        rvAdapter = CustomerListAdapter(this)
        binding.recyclerView.apply {
            addItemDecoration(ListSpacingItemDecorator(context.dpToPx(32), context.dpToPx(16)))
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = rvAdapter
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