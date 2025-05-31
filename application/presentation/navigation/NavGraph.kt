package application.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState // Not strictly needed here anymore if only using initial state
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import application.presentation.screens.auth.AuthViewModel
import application.presentation.screens.auth.LoginScreen
import application.presentation.screens.auth.RegistrationScreen
import application.presentation.screens.inventory.AddProductScreen
import application.presentation.screens.inventory.InventoryScreen
import application.presentation.screens.store.StoreHomeScreen
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier


@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // AuthViewModel's initial state now correctly reflects firebaseAuth.currentUser.
    // So, this check should be reliable for the initial start destination.
    val startDestination = if (authViewModel.uiState.value.authenticatedUser != null) {
        Screen.StoreHome.route
    } else {
        Screen.Login.route
    }

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                navController = navController, // Still here for potential non-auth navigation from login
                authViewModel = authViewModel,
                onLoginSuccess = {
                    navController.navigate(Screen.StoreHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Registration.route)
                }
            )
        }
        composable(Screen.Registration.route) {
            RegistrationScreen(
                navController = navController, // Still here for potential non-auth navigation
                authViewModel = authViewModel,
                onRegistrationSuccess = {
                    navController.navigate(Screen.StoreHome.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screen.Login.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.StoreHome.route) {
            // Pass the authViewModel instance to StoreHomeScreen
            StoreHomeScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screen.InventoryList.route) {
            InventoryScreen(navController = navController)
        }
        composable(Screen.AddProduct.route) {
            AddProductScreen(navController = navController)
        }

        // Add placeholders for new screens
        composable(Screen.Categories.route) {
            // Remove the Box placeholder and call the actual screen
            CategoriesScreen(navController = navController)
        }
        composable(Screen.Wishlist.route) {
            // Remove the Box placeholder and call the actual screen
            WishlistScreen(navController = navController)
        }
        composable(Screen.ShoppingCart.route) {
            // Remove the Box placeholder and call the actual screen
            ShoppingCartScreen(navController = navController)
        }
    }
}
