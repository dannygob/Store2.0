package application.presentation.screens.suppliers

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
import application.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuppliersScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(stringResource(id = R.string.suppliers_title)) })
        }
    ) { paddingValues ->
        Text(
            text = stringResource(id = R.string.coming_soon_suppliers),
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
        )
    }
}
