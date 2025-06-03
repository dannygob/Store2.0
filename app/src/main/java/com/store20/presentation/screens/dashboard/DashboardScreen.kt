package com.store20.presentation.screens.dashboard // Updated package

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.store20.presentation.navigation.AppDestinations // Updated import
import com.store20.R // Updated import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.dashboard_title)) })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = { navController.navigate(AppDestinations.INVENTORY_LIST) }) {
                Text(stringResource(id = R.string.dashboard_manage_inventory))
            }
            Button(onClick = { navController.navigate(AppDestinations.CUSTOMERS_LIST) }) {
                Text(stringResource(id = R.string.dashboard_manage_customers))
            }
            Button(onClick = { navController.navigate(AppDestinations.ORDERS_LIST) }) {
                Text(stringResource(id = R.string.dashboard_manage_orders))
            }
            Button(onClick = { navController.navigate(AppDestinations.SUPPLIERS_LIST) }, enabled = true) { // Enabled and navigates
                Text(stringResource(id = R.string.dashboard_manage_suppliers))
            }
        }
    }
}
