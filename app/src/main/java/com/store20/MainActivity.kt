package com.store20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import application.presentation.navigation.AppDestinations
import application.presentation.screens.customers.CustomersScreen
import application.presentation.screens.dashboard.DashboardScreen
import application.presentation.screens.inventory.AddProductScreen
import application.presentation.screens.inventory.InventoryScreen
import application.presentation.screens.orders.OrdersScreen
import application.presentation.screens.suppliers.SuppliersScreen
import com.store20.ui.theme.Store20Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Store20Theme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = AppDestinations.DASHBOARD) {
                    composable(AppDestinations.DASHBOARD) {
                        DashboardScreen(navController = navController)
                    }
                    composable(AppDestinations.INVENTORY_LIST) {
                        InventoryScreen(navController = navController)
                    }
                    composable(
                        route = AppDestinations.ADD_PRODUCT_ROUTE_DEFINITION,
                        arguments = listOf(navArgument(AppDestinations.PRODUCT_ID_ARG) {
                            type = NavType.StringType
                            nullable = true
                        })
                    ) { backStackEntry ->
                        val productId = backStackEntry.arguments?.getString(AppDestinations.PRODUCT_ID_ARG)
                        AddProductScreen(navController = navController, productId = productId)
                    }
                    composable(AppDestinations.CUSTOMERS_LIST) {
                        CustomersScreen(navController = navController)
                    }
                    composable(AppDestinations.ORDERS_LIST) {
                        OrdersScreen(navController = navController)
                    }
                    composable(AppDestinations.SUPPLIERS_LIST) {
                        SuppliersScreen(navController = navController)
                    }
                }
            }
        }
    }
}