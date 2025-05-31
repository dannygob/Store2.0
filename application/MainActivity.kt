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

object AppDestinations {
    const val INVENTORY_LIST = "inventoryList"
    const val ADD_PRODUCT = "addProduct"
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
                    composable(AppDestinations.ADD_PRODUCT) {
                        AddProductScreen(navController = navController)
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
