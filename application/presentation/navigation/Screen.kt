package application.presentation.navigation

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Registration : Screen("registration")
    object StoreHome : Screen("store_home")
    object InventoryList : Screen("inventory_list")
    object AddProduct : Screen("add_product")
    object Categories : Screen("categories")
    object Wishlist : Screen("wishlist")
    object ShoppingCart : Screen("shopping_cart")
    object ProductList : Screen("product_list/{categoryName}") {
        fun createRoute(categoryName: String) = "product_list/$categoryName"
    }
    object ProductDetail : Screen("product_detail/{productId}") { // New
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    object Checkout : Screen("checkout")
}
