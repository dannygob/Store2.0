package com.example.store20

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.store20.adapter.CartAdapter
import com.example.store20.databinding.ActivityCartBinding
// import com.example.store20.model.CartItem // Not directly used here, but adapter uses it.
import com.example.store20.util.CartManager

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_activity_cart)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        // updateCartData() will be called in onResume

        binding.buttonCheckout.setOnClickListener {
            if (CartManager.getCartItems().isNotEmpty()) {
                startActivity(Intent(this, CheckoutActivity::class.java))
            } else {
                Toast.makeText(this, getString(R.string.cart_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // Refresh cart data in case it was cleared by CheckoutActivity or quantity changed
        updateCartData()
    }

    private fun setupRecyclerView() {
        cartAdapter = CartAdapter(
            CartManager.getCartItems(), // Initial list
            onQuantityChanged = {
                updateCartData() // Refresh list and total
            },
            onItemRemoved = { cartItem ->
                updateCartData() // Refresh list and total
                Toast.makeText(this, getString(R.string.toast_item_removed, cartItem.product.name), Toast.LENGTH_SHORT).show()
            }
        )
        binding.cartRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@CartActivity)
            adapter = cartAdapter
        }
    }

    private fun updateCartData() {
        val items = CartManager.getCartItems()
        cartAdapter.updateData(items) // Update adapter's list
        binding.totalPriceTextView.text = getString(R.string.label_total_price, CartManager.getTotalPrice())

        if (items.isEmpty()) {
            binding.textViewCartEmpty.visibility = View.VISIBLE
            binding.cartRecyclerView.visibility = View.GONE
            binding.buttonCheckout.isEnabled = false // Disable checkout if cart is empty
        } else {
            binding.textViewCartEmpty.visibility = View.GONE
            binding.cartRecyclerView.visibility = View.VISIBLE
            binding.buttonCheckout.isEnabled = true // Enable checkout if cart has items
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // close this activity and return to previous one (MainActivity)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
