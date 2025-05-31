package application.presentation.screens.inventory

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import application.domain.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InventoryViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {
    // ViewModel logic will be added here later
}

@Composable
fun InventoryScreen(viewModel: InventoryViewModel) {
    Text(text = "Inventory Screen")
}
