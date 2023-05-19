package com.example.fancywork.ui.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fancywork.ui.view.HelpScreen
import com.example.fancywork.ui.view.MainScreen
import com.example.fancywork.ui.view.SettingsScreen
import com.example.fancywork.ui.view.LoginScreen
import com.example.fancywork.ui.viewmodel.LoginViewModel
import com.example.fancywork.ui.view.RegisterScreen
import com.example.fancywork.ui.viewmodel.RegisterViewModel
import com.example.fancywork.ui.view.WelcomeScreen
import com.example.fancywork.ui.viewmodel.WelcomeViewModel
import com.google.firebase.auth.FirebaseAuth

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()
    val auth = FirebaseAuth.getInstance()

    NavHost(
        navController = navController,
        startDestination = DestinationsMain.Welcome.route
    ) {
        composable(
            route = DestinationsMain.Login.route
        ) {
            LoginScreen(
                loginViewModel = LoginViewModel(),
                navigateWelcome = {
                    navController.popBackStack()
                },
                navigateRegister = {
                    navController.navigate(DestinationsMain.Register.route){
                        launchSingleTop = true
                    }
                },
                navigateMain = {
                    navController.navigate(DestinationsMain.Main.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = DestinationsMain.Register.route
        ) {
            RegisterScreen(
                navigateLogin = {
                    navController.popBackStack()
                },
                registerViewModel = RegisterViewModel(),
                navigateMain = {
                    navController.navigate(DestinationsMain.Main.route) {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable(
            route = DestinationsMain.Main.route
        ) {
            MainScreen(
                mainNavController = navController
            )
        }
        composable(
            route = DestinationsMain.Welcome.route
        ) {
            if (auth.currentUser != null) {
                LaunchedEffect(key1 = Unit) {
                    navController.navigate(DestinationsMain.Main.route) {
                        launchSingleTop = true
                    }
                }
            } else {
                WelcomeScreen(
                    navigateLogin = {
                        navController.navigate(DestinationsMain.Login.route){
                            launchSingleTop = true
                        }
                    },
                    welcomeViewModel = WelcomeViewModel(),
                    navigate = {
                        navController.navigate(DestinationsMain.Main.route) {
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
        composable(
            route = Destinations.Settings.route
        ) {
            SettingsScreen(
                backStack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Destinations.Help.route
        ) {
            HelpScreen(
                backStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
