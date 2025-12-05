package com.tecsup.productmanager.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.productmanager.ui.screens.*
import com.tecsup.productmanager.ui.viewmodel.AuthViewModel
import com.tecsup.productmanager.ui.viewmodel.ProductViewModel

@Composable
fun AppNavigation() {

    val navController: NavHostController = rememberNavController()

    val authViewModel: AuthViewModel = viewModel()
    val productViewModel: ProductViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LoginScreen(navController, authViewModel)
        }

        composable("register") {
            RegisterScreen(navController, authViewModel)
        }

        composable("products") {
            ProductListScreen(navController, productViewModel, authViewModel)
        }

        composable(
            "productForm/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            ProductFormScreen(navController, id, productViewModel)
        }
    }
}
