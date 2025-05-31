package application.presentation.screens.store

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope // Added for Snackbar
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import application.domain.models.Product
import coil.compose.AsyncImage
import coil.request.ImageRequest
import java.text.NumberFormat
import kotlinx.coroutines.launch // Added for Snackbar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    navController: NavController,
    productDetailViewModel: ProductDetailViewModel = hiltViewModel(),
    cartViewModel: CartViewModel = hiltViewModel(), // For adding to cart
    wishlistViewModel: WishlistViewModel = hiltViewModel() // For adding/removing from wishlist
) {
    val uiState by productDetailViewModel.uiState.collectAsState()
    val cartUiState by cartViewModel.uiState.collectAsState() // To reflect cart changes if any
    val wishlistUiState by wishlistViewModel.uiState.collectAsState() // To reflect wishlist status

    val snackbarHostState = remember { SnackbarHostState() }
    val currencyFormatter = NumberFormat.getCurrencyInstance()
    val scope = rememberCoroutineScope() // Added for Snackbar

    LaunchedEffect(uiState.error) {
        if (uiState.error != null) {
            snackbarHostState.showSnackbar(
                message = uiState.error!!,
                duration = SnackbarDuration.Short
            )
            productDetailViewModel.errorShown()
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(uiState.product?.title ?: "Product Details") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    uiState.product?.let { product ->
                        val isWishlisted = wishlistUiState.items.any { it.name == product.title } // Assuming name is unique for now
                        IconButton(onClick = {
                            if (isWishlisted) {
                                wishlistUiState.items.find { it.name == product.title }?.let {
                                    wishlistViewModel.removeFromWishlist(it)
                                }
                            } else {
                                wishlistViewModel.addToWishlist(product.title) // Using title as stand-in
                            }
                        }) {
                            Icon(
                                imageVector = if (isWishlisted) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                contentDescription = "Toggle Wishlist",
                                tint = if (isWishlisted) MaterialTheme.colorScheme.primary else LocalContentColor.current
                            )
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (uiState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else if (uiState.product != null) {
                val product = uiState.product!!
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(product.image)
                            .crossfade(true)
                            .build(),
                        contentDescription = product.title,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp) // Adjust height as needed
                            .padding(bottom = 16.dp),
                        contentScale = ContentScale.Fit
                    )
                    Text(product.title, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            currencyFormatter.format(product.price),
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold
                        )
                        // Display rating if available
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text("Rating: ${product.rating.rate} (${product.rating.count} reviews)", style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Description", style = MaterialTheme.typography.titleMedium)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(product.description, style = MaterialTheme.typography.bodyLarge)
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = {
                            cartViewModel.addToCart(product.title, product.price) // Using title as stand-in for ID
                            // Optionally show a snackbar "Added to cart"
                            // This could be a common utility function or flow observation
                            scope.launch { // Requires a CoroutineScope, typically from rememberCoroutineScope()
                                snackbarHostState.showSnackbar("Added to cart!", duration = SnackbarDuration.Short)
                            }
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Icon(Icons.Filled.ShoppingCart, contentDescription = "Add to cart icon", modifier = Modifier.size(ButtonDefaults.IconSize))
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Add to Cart")
                    }
                    Spacer(modifier = Modifier.height(72.dp)) // Space for potential FAB or bottom elements
                }
            } else if (uiState.error != null) {
                // Error already handled by Snackbar, could show a retry button here
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Failed to load product details. Please try again later.", style = MaterialTheme.typography.bodyLarge)
                }
            } else {
                 Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No product details available.", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
