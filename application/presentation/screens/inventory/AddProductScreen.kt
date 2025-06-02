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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import application.R
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
            Toast.makeText(context, stringResource(R.string.add_product_success_message), Toast.LENGTH_SHORT).show()
            navController.popBackStack()
            viewModel.dismissSubmissionStatus()
        }
        uiState.submissionError?.let {
            // Assuming 'it' can be a resource ID or a direct string.
            // For now, let's assume ViewModel provides resource IDs for errors that need translation.
            // If 'it' is a raw string from ViewModel that needs to be translated, this approach needs refinement.
            // For this task, we are instructed to assume they are hardcoded and make them string resources.
            // This means the ViewModel should ideally be updated to return R.string.some_error_key.
            // However, without modifying ViewModel, we'll try to match common error messages.
            // This is a placeholder for a more robust solution.
            val errorMessage = when (it) {
                "Name cannot be empty" -> stringResource(R.string.error_name_empty)
                "Purchase price must be a valid number" -> stringResource(R.string.error_purchase_price_invalid)
                "Sale price must be a valid number" -> stringResource(R.string.error_sale_price_invalid)
                "Stock must be a valid number" -> stringResource(R.string.error_stock_invalid)
                // Add more specific error messages here if needed
                else -> it // Fallback to the original error message if not mapped
            }
            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            viewModel.dismissSubmissionStatus()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isEditMode) stringResource(R.string.add_product_edit_title) else stringResource(R.string.add_product_add_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = stringResource(R.string.content_description_back))
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
                label = { Text(stringResource(R.string.add_product_id_label)) },
                modifier = Modifier.fillMaxWidth(),
                readOnly = true,
                enabled = false
            )
            OutlinedTextField(
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                label = { Text(stringResource(R.string.add_product_name_label)) },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.nameError != null,
                supportingText = { uiState.nameError?.let { Text(stringResource(R.string.error_name_empty)) } } // Assuming this specific error
            )

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = barcode,
                    onValueChange = { viewModel.onBarcodeChange(it) },
                    label = { Text(stringResource(R.string.add_product_barcode_label)) },
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Button(onClick = {
                    Toast.makeText(context, stringResource(R.string.add_product_scan_not_implemented), Toast.LENGTH_SHORT).show()
                }) {
                    Text(stringResource(R.string.add_product_scan_button))
                }
            }

            OutlinedTextField(
                value = purchasePrice,
                onValueChange = { viewModel.onPurchasePriceChange(it) },
                label = { Text(stringResource(R.string.add_product_purchase_price_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.purchasePriceError != null,
                supportingText = { uiState.purchasePriceError?.let { Text(stringResource(R.string.error_purchase_price_invalid)) } } // Assuming this specific error
            )
            OutlinedTextField(
                value = salePrice,
                onValueChange = { viewModel.onSalePriceChange(it) },
                label = { Text(stringResource(R.string.add_product_sale_price_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.salePriceError != null,
                supportingText = { uiState.salePriceError?.let { Text(stringResource(R.string.error_sale_price_invalid)) } } // Assuming this specific error
            )
            OutlinedTextField(
                value = category,
                onValueChange = { viewModel.onCategoryChange(it) },
                label = { Text(stringResource(R.string.add_product_category_label)) },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.categoryError != null,
                supportingText = { uiState.categoryError?.let { Text(it) } } // Assuming generic error for now
            )
            OutlinedTextField(
                value = stock,
                onValueChange = { viewModel.onStockChange(it) },
                label = { Text(stringResource(R.string.add_product_stock_label)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.stockError != null,
                supportingText = { uiState.stockError?.let { Text(stringResource(R.string.error_stock_invalid)) } } // Assuming this specific error
            )
            OutlinedTextField(
                value = supplierId,
                onValueChange = { viewModel.onSupplierIdChange(it) },
                label = { Text(stringResource(R.string.add_product_supplier_id_label)) },
                modifier = Modifier.fillMaxWidth(),
                isError = uiState.supplierIdError != null,
                supportingText = { uiState.supplierIdError?.let { Text(it) } } // Assuming generic error for now
            )

            Button(
                onClick = { viewModel.saveProduct() },
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            ) {
                Text(if (isEditMode) stringResource(R.string.add_product_update_button) else stringResource(R.string.add_product_save_button))
            }
        }
    }
}
