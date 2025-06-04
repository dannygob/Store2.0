package application.presentation.screens.inventory

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import application.R
import application.AppDestinations
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    navController: NavController,
    viewModel: InventoryViewModel = hiltViewModel()
) {
    val products by viewModel.products.collectAsState()
    var showDeleteConfirmationDialog by remember { mutableStateOf(false) }
    var productToDeleteId by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) { // Use Unit to launch once, or viewModel if it can change
        viewModel.errorEvents.collectLatest { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate(AppDestinations.ADD_NEW_PRODUCT_ROUTE) }) {
                Icon(Icons.Filled.Add, contentDescription = stringResource(R.string.inventory_add_product_fab_description))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues) // Apply padding from Scaffold
                .padding(16.dp) // Original padding
        ) {
            if (products.isEmpty()) {
                Text(text = stringResource(R.string.inventory_no_products), modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(products) { product ->
                        ProductListItem(
                            product = product,
                            onItemClick = { productId ->
                                navController.navigate("${AppDestinations.ADD_PRODUCT_ROUTE}?${AppDestinations.PRODUCT_ID_ARG}=$productId")
                            },
                            onDeleteClick = { productId ->
                                productToDeleteId = productId
                                showDeleteConfirmationDialog = true
                            }
                        )
                    }
                }
            }
        }
    }

    if (showDeleteConfirmationDialog) {
        DeleteConfirmationDialog(
            onConfirm = {
                productToDeleteId?.let { viewModel.deleteProduct(it) }
                showDeleteConfirmationDialog = false
                productToDeleteId = null
            },
            onDismiss = {
                showDeleteConfirmationDialog = false
                productToDeleteId = null
            }
        )
    }
}

@Composable
fun ProductListItem(
    product: application.domain.models.Product,
    onItemClick: (String) -> Unit,
    onDeleteClick: (String) -> Unit
) {
    ListItem(
        headlineContent = { Text(product.name) },
        supportingContent = { Text(stringResource(R.string.inventory_product_stock_label, product.stock)) },
        modifier = Modifier.clickable { onItemClick(product.id) },
        trailingContent = {
            IconButton(onClick = { onDeleteClick(product.id) }) {
                Icon(Icons.Filled.Delete, contentDescription = stringResource(R.string.inventory_delete_product_icon_description))
            }
        }
    )
}

@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(stringResource(R.string.delete_dialog_title)) },
        text = { Text(stringResource(R.string.delete_dialog_message)) },
        confirmButton = {
            Button(onClick = onConfirm) { Text(stringResource(R.string.delete_dialog_confirm_button)) }
        },
        dismissButton = {
            Button(onClick = onDismiss) { Text(stringResource(R.string.dialog_cancel_button)) }
        }
    )
}
