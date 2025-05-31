package application.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registration : Screen("registration")
    object StoreHome : Screen("store_home")
    object InventoryList : Screen("inventory_list")
    object AddProduct : Screen("add_product")
    object Categories : Screen("categories") // New
    object Wishlist : Screen("wishlist")     // New
    object ShoppingCart : Screen("shopping_cart") // New
}
