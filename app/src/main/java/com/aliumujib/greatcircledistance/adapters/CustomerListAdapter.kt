package com.aliumujib.greatcircledistance.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.aliumujib.greatcircledistance.R
import com.aliumujib.greatcircledistance.databinding.CustomerItemLayoutBinding
import com.aliumujib.greatcircledistance.lib.models.Customer


class CustomerListAdapter constructor(private val listener: CustomerClickListener) :
    ListAdapter<Customer, RecyclerView.ViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            CustomerItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding, listener)
    }

    fun isEmpty() = super.getItemCount()  == 0

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomerViewHolder).bind(getItem(position))
    }


    class CustomerViewHolder(private val binding: CustomerItemLayoutBinding, private val categoryClickListener: CustomerClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Customer) {
            itemView.setOnClickListener {
                categoryClickListener.invoke(model)
            }
            binding.customerName.text = model.name
            binding.distanceKm.text = itemView.context.getString(R.string.distance_in_kilometers, model.distance_from_location)
        }

    }

    class DiffCallback : DiffUtil.ItemCallback<Customer>() {
        override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
            return oldItem.user_id == newItem.user_id
        }

        override fun areContentsTheSame(
            oldItem: Customer,
            newItem: Customer
        ): Boolean {
            return oldItem == newItem
        }
    }
}