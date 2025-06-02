package com.example.store20.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.store20.databinding.ItemCartBinding
import com.example.store20.model.CartItem
import com.example.store20.util.CartManager

class CartAdapter(
    private var cartItems: List<CartItem>,
    private val onQuantityChanged: () -> Unit, // Callback to update total in Activity
    private val onItemRemoved: (CartItem) -> Unit // Callback for item removal
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]
        holder.bind(cartItem)
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateData(newCartItems: List<CartItem>) {
        cartItems = newCartItems
        notifyDataSetChanged() // Consider using DiffUtil for better performance
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cartItem: CartItem) {
            binding.textViewCartItemName.text = cartItem.product.name
            binding.textViewCartItemPrice.text = String.format("$%.2f", cartItem.product.price)
            binding.textViewCartItemQuantity.text = cartItem.quantity.toString()

            binding.buttonIncreaseQuantity.setOnClickListener {
                CartManager.updateQuantity(cartItem.product.id, cartItem.quantity + 1)
                onQuantityChanged() // Notify activity to update total and refresh adapter
            }

            binding.buttonDecreaseQuantity.setOnClickListener {
                if (cartItem.quantity > 1) {
                    CartManager.updateQuantity(cartItem.product.id, cartItem.quantity - 1)
                } else {
                    // If quantity is 1, decreasing means removing the item
                    CartManager.removeItemByProductId(cartItem.product.id)
                    onItemRemoved(cartItem) // Notify for removal
                }
                onQuantityChanged() // Notify activity to update total and refresh adapter
            }

            binding.buttonRemoveItem.setOnClickListener {
                CartManager.removeItemByProductId(cartItem.product.id)
                onItemRemoved(cartItem) // Notify for removal
                onQuantityChanged() // Notify activity to update total and refresh adapter
            }
        }
    }
}
