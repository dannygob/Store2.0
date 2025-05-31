package application

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import application.presentation.screens.inventory.AddProductScreen
import application.presentation.screens.inventory.InventoryScreen
import dagger.hilt.android.AndroidEntryPoint

import androidx.navigation.NavType
import androidx.navigation.navArgument

object AppDestinations {
    const val INVENTORY_LIST = "inventoryList"
    const val ADD_PRODUCT_ROUTE = "addProduct" // Base route
    const val PRODUCT_ID_ARG = "productId"
    // Use this for navigating. Example: navController.navigate("${AppDestinations.ADD_PRODUCT_ROUTE}?${AppDestinations.PRODUCT_ID_ARG}=someId")
    // Or for new product: navController.navigate(AppDestinations.ADD_PRODUCT_ROUTE)
    const val ADD_PRODUCT_WITH_OPTIONAL_ARG_ROUTE = "$ADD_PRODUCT_ROUTE?$PRODUCT_ID_ARG={$PRODUCT_ID_ARG}"
    // This is the actual route pattern NavHost will listen for.
    const val ADD_PRODUCT_ROUTE_DEFINITION = "$ADD_PRODUCT_ROUTE?$PRODUCT_ID_ARG={$PRODUCT_ID_ARG}"
    // Simplified constant for navigating to add a new product without any argument explicitly passed.
    const val ADD_NEW_PRODUCT_ROUTE = ADD_PRODUCT_ROUTE
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = AppDestinations.INVENTORY_LIST) {
                    composable(AppDestinations.INVENTORY_LIST) {
                        InventoryScreen(navController = navController)
                    }
                    composable(
                        route = AppDestinations.ADD_PRODUCT_ROUTE_DEFINITION, // Use the definition that expects an optional arg
                        arguments = listOf(navArgument(AppDestinations.PRODUCT_ID_ARG) {
                            type = NavType.StringType
                            nullable = true
                            // No defaultValue needed here if we always construct the route with ?productId= or without it.
                            // If NavHost sees addProduct?productId= then arg is present (even if value is "null" string sometimes, handle that)
                            // If NavHost sees addProduct, then arg is null.
                        })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString(AppDestinations.PRODUCT_ID_ARG)
                        AddProductScreen(navController = navController, productId = productId) // Pass it to the screen
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MaterialTheme {
        Text("Android Preview")
    }
}
