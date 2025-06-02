package com.example.store20

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store20.adapter.ProductAdapter
import com.example.store20.databinding.ActivityMainBinding
import com.example.store20.model.Product

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductAdapter
    private val productList = mutableListOf<Product>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.app_name) // Set a title for MainActivity

        setupRecyclerView()
        loadMockProducts()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_cart -> {
                startActivity(Intent(this, CartActivity::class.java))
                true
            }
            R.id.action_order_history -> {
                startActivity(Intent(this, OrderHistoryActivity::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupRecyclerView() {
        productAdapter = ProductAdapter(productList)
        binding.productsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = productAdapter
        }
    }

    private fun loadMockProducts() {
        // Create some mock products
        val mockProducts = listOf(
            Product("1", "Vintage T-Shirt", "A cool vintage t-shirt.", 19.99, "url_to_image_1_placeholder"),
            Product("2", "Modern Jeans", "Stylish modern jeans.", 49.99, ""), // Empty URL to test placeholder
            Product("3", "Classic Hat", "A classic hat for all occasions.", 25.00, "url_to_image_3_placeholder"),
            Product("4", "Leather Boots", "Durable leather boots.", 79.50, "url_to_image_4_placeholder"),
            Product("5", "Summer Dress", "A light and airy summer dress.", 39.99, ""),
            Product("6", "Winter Jacket", "Warm jacket for cold weather.", 120.00, "url_to_image_6_placeholder"),
            Product("7", "Sports Sneakers", "Comfortable sneakers for sports.", 65.00, "url_to_image_7_placeholder"),
            Product("8", "Formal Shirt", "Elegant formal shirt.", 45.00, ""),
            Product("9", "Wool Scarf", "A cozy wool scarf.", 22.00, "url_to_image_9_placeholder"),
            Product("10", "Designer Bag", "A fancy designer bag.", 250.00, "url_to_image_10_placeholder")
        )

        // Clear list before adding new ones to prevent duplication if this method is called again
        if (productList.isEmpty()){ // Only load if empty, or manage updates properly
             productList.addAll(mockProducts)
        }
        productAdapter.notifyDataSetChanged() // Or use DiffUtil for better performance
    }
}
