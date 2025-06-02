package com.example.store20

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.store20.databinding.ActivityCheckoutBinding
import com.example.store20.util.CartManager

class CheckoutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCheckoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCheckoutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.title_activity_checkout)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        displayCartSummary()

        binding.buttonPlaceOrder.setOnClickListener {
            val shippingAddress = binding.editTextShippingAddress.text.toString()
            val paymentDetails = binding.editTextPaymentDetails.text.toString()

            if (shippingAddress.isBlank() || paymentDetails.isBlank()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // TODO: Implement actual order processing (e.g., send to backend)
            val order = CartManager.placeOrderAndClearCart()

            if (order != null) {
                Toast.makeText(this, getString(R.string.toast_order_placed) + "\nOrder ID: ${order.id.substring(0,8)}", Toast.LENGTH_LONG).show()
            } else {
                // Should not happen if button is enabled based on cart items
                Toast.makeText(this, "Error placing order: Cart was empty.", Toast.LENGTH_SHORT).show()
            }

            // Navigate back to MainActivity and clear activity stack above it
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish() // Finish CheckoutActivity
            // CartActivity should also be finished if it's in the backstack.
            // This can be done by starting CheckoutActivity with startActivityForResult
            // and then finishing CartActivity in onActivityResult, or by using a shared event/ViewModel.
            // For simplicity now, we rely on MainActivity being brought to top.
        }
    }

    private fun displayCartSummary() {
        val totalItems = CartManager.getCartItemCount()
        val totalAmount = CartManager.getTotalPrice()

        binding.textViewTotalItems.text = getString(R.string.checkout_total_items, totalItems)
        binding.textViewTotalAmount.text = getString(R.string.checkout_total_amount, totalAmount)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish() // Go back to CartActivity
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}
