package com.example.fancywork.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fancywork.screens.help.HelpScreen
import com.example.fancywork.screens.main.MainScreen
import com.example.fancywork.screens.notifications.NotificationScreen
import com.example.fancywork.screens.settings.SettingsScreen
import com.example.fancywork.screens.login.LoginScreen
import com.example.fancywork.screens.login.LoginViewModel
import com.example.fancywork.screens.register.RegisterScreen
import com.example.fancywork.screens.register.RegisterViewModel
import com.example.fancywork.screens.welcome.WelcomeScreen
import com.example.fancywork.screens.welcome.WelcomeViewModel
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
            route = Destinations.Notifications.route
        ) {
            NotificationScreen(
                backStack = {
                    navController.popBackStack()
                }
            )
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
