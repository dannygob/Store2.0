package application.presentation.screens.store

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.AddShoppingCart // New import
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesScreen(
    navController: NavController,
    storeViewModel: StoreViewModel = hiltViewModel(),
    wishlistViewModel: WishlistViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel() // Inject CartViewModel
) {
    val uiState by storeViewModel.uiState.collectAsState()
    val wishlistState by wishlistViewModel.uiState.collectAsState()
    // val cartState by cartViewModel.uiState.collectAsState() // Not strictly needed for add-only action here

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            snackbarHostState.showSnackbar(
                message = uiState.error!!,
                duration = SnackbarDuration.Short
            )
            storeViewModel.errorShown() // Reset error
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Product Categories") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.categories.isNotEmpty()) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(uiState.categories) { category ->
                        val isWishlisted = wishlistState.items.any { it.name == category }
                        CategoryItem(
                            category = category,
                            isWishlisted = isWishlisted,
                            onItemClick = {
                                // TODO: Navigate to products screen for this category
                            },
                            onWishlistClick = {
                                if (isWishlisted) {
                                    wishlistState.items.find { it.name == category }?.let {
                                        wishlistViewModel.removeFromWishlist(it)
                                    }
                                } else {
                                    wishlistViewModel.addToWishlist(category)
                                }
                            },
                            onAddToCartClick = { // New callback
                                cartViewModel.addToCart(category) // Using category name as product name
                                // Optionally show a snackbar "Added to cart"
                                // For simplicity, not adding another snackbar here.
                            }
                        )
                    }
                }
            } else if (uiState.error == null) { // Not loading, no categories, no error yet
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No categories found.")
                }
            }
            // Error is handled by Snackbar
        }
    }
}

@Composable
fun CategoryItem(
    category: String,
    isWishlisted: Boolean,
    onItemClick: () -> Unit,
    onWishlistClick: () -> Unit,
    onAddToCartClick: () -> Unit // New parameter
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onItemClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp), // Adjusted padding
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = category.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            )
            // Grouping action buttons
            Row {
                 IconButton(onClick = onWishlistClick, modifier = Modifier.size(40.dp)) {
                    Icon(
                        imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (isWishlisted) "Remove from Wishlist" else "Add to Wishlist",
                        tint = if (isWishlisted) MaterialTheme.colorScheme.primary else LocalContentColor.current
                    )
                }
                Spacer(modifier = Modifier.width(4.dp))
                IconButton(onClick = onAddToCartClick, modifier = Modifier.size(40.dp)) { // Add to Cart button
                    Icon(
                        imageVector = Icons.Filled.AddShoppingCart,
                        contentDescription = "Add to Cart"
                    )
                }
            }
        }
    }
}
