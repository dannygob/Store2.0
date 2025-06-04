package com.store20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
// Removed screen imports from :application module
import com.store20.ui.theme.Store20Theme // Assuming this theme exists and wraps MaterialTheme or is a MaterialTheme itself
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.Text // Added for placeholder

object AppDestinations {
    const val DASHBOARD = "dashboard"
    const val INVENTORY_LIST = "inventoryList"
    const val ADD_PRODUCT_ROUTE = "addProduct" // Base route
    const val PRODUCT_ID_ARG = "productId"
    const val ADD_PRODUCT_WITH_ID_ROUTE = "$ADD_PRODUCT_ROUTE/{$PRODUCT_ID_ARG}"
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Store20Theme { // Using Store20Theme as it's likely the intended app theme
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "placeholder") { // Changed startDestination
        composable("placeholder") { // Added placeholder composable
            Text("Placeholder Screen")
        }
        // Removed composable blocks for DashboardScreen, InventoryScreen, AddProductScreen
    }
}
