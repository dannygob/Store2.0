package com.store20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.store20.presentation.navigation.AppDestinations // Updated import
import com.store20.presentation.navigation.BottomNavItem // Updated import
import com.store20.presentation.screens.customers.CustomersScreen // Updated import
import com.store20.presentation.screens.dashboard.DashboardScreen // Updated import
import com.store20.presentation.screens.inventory.AddProductScreen // Updated import
import com.store20.presentation.screens.inventory.InventoryScreen // Updated import
import com.store20.presentation.screens.orders.OrdersScreen // Updated import
import com.store20.presentation.screens.suppliers.SuppliersScreen // Updated import
import com.store20.ui.theme.Store20Theme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Store20Theme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        val items = listOf(
                            BottomNavItem.Dashboard,
                            BottomNavItem.Inventory,
                            BottomNavItem.Orders,
                            BottomNavItem.Customers
                        )
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route
                            items.forEach { item ->
                                NavigationBarItem(
                                    selected = currentRoute == item.route,
                                    onClick = {
                                        navController.navigate(item.route) {
                                            popUpTo(navController.graph.startDestinationId) {
                                                saveState = true
                                            }
                                            launchSingleTop = true
                                            restoreState = true
                                        }
                                    },
                                    icon = { Icon(item.icon, contentDescription = stringResource(item.titleResId)) },
                                    label = { Text(stringResource(item.titleResId)) },
                                    alwaysShowLabel = true
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = AppDestinations.DASHBOARD,
                        modifier = Modifier.padding(innerPadding) // Apply padding here
                    ) {
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
}