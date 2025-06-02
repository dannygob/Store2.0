package com.example.store20.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.store20.R
import com.example.store20.databinding.ItemOrderBinding
import com.example.store20.model.Order
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class OrderHistoryAdapter(private var orders: List<Order>) :
    RecyclerView.Adapter<OrderHistoryAdapter.OrderViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.bind(order)
    }

    override fun getItemCount(): Int = orders.size

    fun updateOrders(newOrders: List<Order>) {
        orders = newOrders
        notifyDataSetChanged() // Consider DiffUtil for larger lists
    }

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        fun bind(order: Order) {
            val context = binding.root.context
            binding.textViewOrderId.text = context.getString(R.string.order_id_label, order.id.substring(0, 8)) // Show first 8 chars of UUID
            binding.textViewOrderDate.text = context.getString(R.string.order_date_label, dateFormat.format(Date(order.date)))
            binding.textViewOrderTotalAmount.text = context.getString(R.string.order_total_label, order.totalAmount)
            binding.textViewOrderItemsCount.text = context.getString(R.string.order_items_count_label, order.items.sumOf { it.quantity })
        }
    }
}
