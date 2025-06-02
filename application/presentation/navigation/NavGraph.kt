package application.presentation.navigation

import androidx.compose.runtime.Composable
// import androidx.compose.runtime.collectAsState // No longer needed here
// import androidx.compose.runtime.getValue // No longer needed here
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType // Import NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument // Import navArgument
import application.presentation.screens.auth.AuthViewModel
import application.presentation.screens.auth.LoginScreen
import application.presentation.screens.auth.RegistrationScreen
import application.presentation.screens.inventory.AddProductScreen
import application.presentation.screens.inventory.InventoryScreen
import application.presentation.screens.store.StoreHomeScreen
import application.presentation.screens.store.CategoriesScreen // Ensure this is imported
import application.presentation.screens.store.WishlistScreen   // Ensure this is imported
import application.presentation.screens.store.ShoppingCartScreen // Ensure this is imported
import application.presentation.screens.store.ProductListScreen // Ensure this is imported
import application.presentation.screens.store.ProductDetailScreen // Import new screen
import application.presentation.screens.store.CheckoutScreen // Ensure this is imported


@Composable
fun NavGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {
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
                navController = navController,
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
                navController = navController,
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
            StoreHomeScreen(navController = navController, authViewModel = authViewModel)
        }
        composable(Screen.InventoryList.route) {
            InventoryScreen(navController = navController)
        }
        composable(Screen.AddProduct.route) {
            AddProductScreen(navController = navController)
        }
        composable(Screen.Categories.route) {
            CategoriesScreen(navController = navController)
        }
        composable(Screen.Wishlist.route) {
            WishlistScreen(navController = navController)
        }
        composable(Screen.ShoppingCart.route) {
            ShoppingCartScreen(navController = navController)
        }
        composable(Screen.Checkout.route) {
            CheckoutScreen(navController = navController)
        }
        composable(
            route = Screen.ProductList.route, // e.g., "product_list/{categoryName}"
            arguments = listOf(navArgument("categoryName") { type = NavType.StringType })
        ) {
            ProductListScreen(navController = navController)
        }

        // New destination for ProductDetailScreen
        composable(
            route = Screen.ProductDetail.route, // e.g., "product_detail/{productId}"
            arguments = listOf(navArgument("productId") { type = NavType.IntType }) // Define argument type
        ) {
            ProductDetailScreen(navController = navController)
        }
    }
}
