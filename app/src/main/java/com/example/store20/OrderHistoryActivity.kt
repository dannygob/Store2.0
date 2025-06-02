package com.example.store20

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store20.adapter.OrderHistoryAdapter
import com.example.store20.databinding.ActivityOrderHistoryBinding
import com.example.store20.util.CartManager

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOrderHistoryBinding
    private lateinit var orderHistoryAdapter: OrderHistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOrderHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_activity_order_history)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        loadOrderHistory()
    }

    private fun setupRecyclerView() {
        orderHistoryAdapter = OrderHistoryAdapter(emptyList()) // Initialize with empty list
        binding.orderHistoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@OrderHistoryActivity)
            adapter = orderHistoryAdapter
        }
    }

    private fun loadOrderHistory() {
        val orders = CartManager.getOrderHistory()
        orderHistoryAdapter.updateOrders(orders)

        if (orders.isEmpty()) {
            binding.textViewOrderHistoryEmpty.visibility = View.VISIBLE
            binding.orderHistoryRecyclerView.visibility = View.GONE
        } else {
            binding.textViewOrderHistoryEmpty.visibility = View.GONE
            binding.orderHistoryRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Close this activity and return to previous one
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
