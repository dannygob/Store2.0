package com.store20.presentation.screens.orders // Updated package

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.store20.R // Updated import

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.orders_title)) })
        }
    ) { paddingValues ->
        Text(
            text = stringResource(id = R.string.coming_soon_orders),
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        )
    }
}
