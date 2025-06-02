package com.example.store20.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.store20.R
import com.example.store20.databinding.ItemProductBinding
import com.example.store20.model.Product
import com.example.store20.util.CartManager
// Import Coil or Glide here if you were using them
// import coil.load


class ProductAdapter(private val products: List<Product>) :
    RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int = products.size

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.textViewProductName.text = product.name
            binding.textViewProductPrice.text = String.format("$%.2f", product.price) // Simple price formatting

            // TODO: Implement image loading with a library like Coil or Glide
            if (product.imageUrl.isBlank()) {
                binding.imageViewProduct.setImageResource(R.drawable.ic_placeholder_image)
            } else {
                // Example with Coil (add dependency first):
                // binding.imageViewProduct.load(product.imageUrl) {
                //     placeholder(R.drawable.ic_placeholder_image)
                //     error(R.drawable.ic_placeholder_image)
                // }
                // Fallback to placeholder if no image loading library is implemented yet
                binding.imageViewProduct.setImageResource(R.drawable.ic_placeholder_image)
            }

            binding.buttonAddToCart.setOnClickListener {
                CartManager.addItem(product)
                Toast.makeText(
                    binding.root.context,
                    binding.root.context.getString(R.string.toast_added_to_cart, product.name),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
