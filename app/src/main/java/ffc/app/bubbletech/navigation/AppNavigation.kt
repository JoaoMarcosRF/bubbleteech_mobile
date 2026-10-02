package ffc.app.bubbletech.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import ffc.app.bubbletech.screens.LoginScreen


@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login"){
            LoginScreen(
                onLogin = {
                    navController.navigate("home") {

                        popUpTo("login") {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}