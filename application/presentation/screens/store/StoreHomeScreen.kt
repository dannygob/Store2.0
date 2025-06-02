package application.presentation.screens.store

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import application.presentation.navigation.Screen
import application.presentation.screens.auth.AuthViewModel

@Composable
fun StoreHomeScreen(
    navController: NavController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val authUiState by authViewModel.uiState.collectAsState()

    // Observe auth state for logout or if user becomes null for any other reason
    LaunchedEffect(authUiState.authenticatedUser, authUiState.isLoading) {
        if (authUiState.authenticatedUser == null && !authUiState.isLoading) {
            // This means logout was successful or user session expired and recognized by ViewModel
            navController.navigate(Screen.Login.route) {
                popUpTo(Screen.StoreHome.route) { inclusive = true }
                launchSingleTop = true
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Store Home", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { navController.navigate(Screen.Categories.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Categories")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.Wishlist.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Wishlist")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.ShoppingCart.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("View Shopping Cart")
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate(Screen.InventoryList.route) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Manage Inventory")
        }
        Spacer(modifier = Modifier.height(32.dp))

        // Show loading specifically for logout if desired, or a general loading from authUiState
        if (authUiState.isLoading) {
            // Optionally show a CircularProgressIndicator or disable the button
            // For now, button remains active, and screen navigates on state change.
        }
        Button(
            onClick = { authViewModel.logoutUser() },
            modifier = Modifier.fillMaxWidth(),
            enabled = !authUiState.isLoading // Disable button if any auth operation is in progress
        ) {
            Text("Logout")
        }
    }
}
