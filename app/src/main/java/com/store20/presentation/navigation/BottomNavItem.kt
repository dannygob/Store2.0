package com.store20.presentation.navigation // Updated package

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.ui.graphics.vector.ImageVector
import com.store20.R // This import should remain correct

// AppDestinations will now be imported from the same package if moved correctly.
// No explicit import needed if they are in the same 'com.store20.presentation.navigation' package.
// However, to be safe and explicit:
import com.store20.presentation.navigation.AppDestinations


sealed class BottomNavItem(
    val route: String,
    val titleResId: Int,
    val icon: ImageVector
) {
    object Dashboard : BottomNavItem(
        route = AppDestinations.DASHBOARD,
        titleResId = R.string.dashboard_title,
        icon = Icons.Filled.Dashboard
    )

    object Inventory : BottomNavItem(
        route = AppDestinations.INVENTORY_LIST,
        titleResId = R.string.inventory_title,
        icon = Icons.Filled.Inventory
    )

    object Orders : BottomNavItem(
        route = AppDestinations.ORDERS_LIST,
        titleResId = R.string.orders_title,
        icon = Icons.Filled.ShoppingCart
    )

    object Customers : BottomNavItem(
        route = AppDestinations.CUSTOMERS_LIST,
        titleResId = R.string.customers_title,
        icon = Icons.Filled.People
    )
}
