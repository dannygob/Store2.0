package application.presentation.screens.store

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.RemoveCircleOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import application.domain.models.CartItem
import java.text.NumberFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingCartScreen(
    navController: NavController,
    cartViewModel: CartViewModel = hiltViewModel()
) {
    val uiState by cartViewModel.uiState.collectAsState()
    val currencyFormatter = NumberFormat.getCurrencyInstance() // For formatting price

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Shopping Cart") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            if (uiState.items.isNotEmpty()) {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Total: ${currencyFormatter.format(uiState.totalPrice)}",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Button(onClick = { /* TODO: Proceed to Checkout */ }) {
                            Text("Checkout")
                        }
                    }
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            if (uiState.items.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Your shopping cart is empty.")
                }
            } else {
                LazyColumn(
                    contentPadding = PaddingValues(start = 16.dp, end = 16.dp, top = 16.dp, bottom = if (uiState.items.isNotEmpty()) 72.dp else 16.dp), // Extra padding for bottom bar
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.items, key = { it.id }) { item ->
                        CartItemRow(
                            item = item,
                            currencyFormatter = currencyFormatter,
                            onRemoveClick = { cartViewModel.removeFromCart(item) },
                            onIncreaseQuantity = { cartViewModel.increaseQuantity(item) },
                            onDecreaseQuantity = { cartViewModel.decreaseQuantity(item) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemRow(
    item: CartItem,
    currencyFormatter: NumberFormat,
    onRemoveClick: () -> Unit,
    onIncreaseQuantity: () -> Unit,
    onDecreaseQuantity: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // TODO: Add product image if available
            Column(modifier = Modifier.weight(1f).padding(start = 8.dp)) {
                Text(item.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text("${currencyFormatter.format(item.price)} each", style = MaterialTheme.typography.bodySmall)
                Text("Total: ${currencyFormatter.format(item.getTotalPrice())}", style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)

            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onDecreaseQuantity, Modifier.size(36.dp)) {
                    Icon(Icons.Default.RemoveCircleOutline, contentDescription = "Decrease quantity")
                }
                Text(item.quantity.toString(), style = MaterialTheme.typography.bodyLarge, modifier = Modifier.padding(horizontal = 4.dp))
                IconButton(onClick = onIncreaseQuantity, Modifier.size(36.dp)) {
                    Icon(Icons.Default.AddCircleOutline, contentDescription = "Increase quantity")
                }
            }
            IconButton(onClick = onRemoveClick, Modifier.padding(start = 4.dp).size(36.dp)) {
                Icon(Icons.Filled.Delete, contentDescription = "Remove from cart")
            }
        }
    }
}
