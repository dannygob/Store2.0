package com.store20.presentation.navigation // Updated package

object AppDestinations {
    const val DASHBOARD = "dashboard"
    const val INVENTORY_LIST = "inventoryList"
    const val CUSTOMERS_LIST = "customersList"
    const val ORDERS_LIST = "ordersList"
    const val SUPPLIERS_LIST = "suppliersList"
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
