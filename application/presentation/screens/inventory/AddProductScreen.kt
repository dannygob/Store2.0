package application.presentation.screens.inventory

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddProductScreen(
    navController: NavController,
    productId: String?, // Added productId parameter, though ViewModel uses SavedStateHandle
    viewModel: AddProductViewModel = hiltViewModel()
) {
    val currentProductId by viewModel.productId.collectAsState() // Renamed for clarity from your plan
    val uiState by viewModel.uiState.collectAsState()
    val isEditMode by viewModel.isEditMode.collectAsState()

    // Collect field states from ViewModel
    val name by viewModel.name.collectAsState()
    val barcode by viewModel.barcode.collectAsState()
    val purchasePrice by viewModel.purchasePrice.collectAsState()
    val salePrice by viewModel.salePrice.collectAsState()
    val category by viewModel.category.collectAsState()
    val stock by viewModel.stock.collectAsState()
    val supplierId by viewModel.supplierId.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(uiState) {
        if (uiState.submissionSuccess) {
            Toast.makeText(context, "Product added successfully!", Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            viewModel.dismissSubmissionStatus()
        }
        uiState.submissionError?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            viewModel.dismissSubmissionStatus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) "Edit Product" else "Add New Product") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedTextField(
                value = currentProductId, // Use currentProductId collected from ViewModel
                onValueChange = { /* ViewModel controls this */ },
                label = { Text("Product ID") },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false
            )
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text("Product Name") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.nameError != null,
                supportingText = { uiState.nameError?.let { Text(it) } }
            )

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = barcode,
                    onValueChange = { viewModel.onBarcodeChange(it) },
                    label = { Text("Barcode (Optional)") },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    Toast.makeText(context, "Barcode scanning not yet implemented.", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Scan")
                }
            }

            OutlinedTextField(
                value = purchasePrice,
                onValueChange = { viewModel.onPurchasePriceChange(it) },
                label = { Text("Purchase Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.purchasePriceError != null,
                supportingText = { uiState.purchasePriceError?.let { Text(it) } }
            )
            OutlinedTextField(
                value = salePrice,
                onValueChange = { viewModel.onSalePriceChange(it) },
                label = { Text("Sale Price") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.salePriceError != null,
                supportingText = { uiState.salePriceError?.let { Text(it) } }
            )
            OutlinedTextField(
                value = category,
                onValueChange = { viewModel.onCategoryChange(it) },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.categoryError != null,
                supportingText = { uiState.categoryError?.let { Text(it) } }
            )
            OutlinedTextField(
                value = stock,
                onValueChange = { viewModel.onStockChange(it) },
                label = { Text("Stock") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.stockError != null,
                supportingText = { uiState.stockError?.let { Text(it) } }
            )
            OutlinedTextField(
                value = supplierId,
                onValueChange = { viewModel.onSupplierIdChange(it) },
                label = { Text("Supplier ID") },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.supplierIdError != null,
                supportingText = { uiState.supplierIdError?.let { Text(it) } }
            )

            Button(
                onClick = { viewModel.saveProduct() },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text(if (isEditMode) "Update Product" else "Save Product")
            }
        }
    }
}
