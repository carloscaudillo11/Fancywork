package com.example.fancywork.ui.navegation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fancywork.ui.view.DetailsScreen
import com.example.fancywork.ui.view.MainScreen
import com.example.fancywork.ui.view.LoginScreen
import com.example.fancywork.ui.view.PostScreen
import com.example.fancywork.ui.view.ProfileScreen
import com.example.fancywork.ui.viewmodel.LoginViewModel
import com.example.fancywork.ui.view.RegisterScreen
import com.example.fancywork.ui.viewmodel.RegisterViewModel
import com.example.fancywork.ui.view.WelcomeScreen
import com.example.fancywork.ui.viewmodel.MainViewModel
import com.example.fancywork.ui.viewmodel.WelcomeViewModel
import com.google.firebase.auth.FirebaseAuth

/* funcion que se encarga de administrar las rutas y navegación de las
    pantallas en la rama principal de la aplicación.
 */
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
                    navController.navigate(DestinationsMain.Register.route) {
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
                mainViewModel = MainViewModel(),
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
                        navController.navigate(DestinationsMain.Login.route) {
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
            route = DestinationsMain.Post.route,
        ) {
            PostScreen(
                navigate = {
                    navController.navigate(DestinationsMain.Main.route) {
                        launchSingleTop = true
                    }
                },
                postViewModel = MainViewModel(),
                backStack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = DestinationsMain.Profile.route
        ) {
            ProfileScreen(
                backStack = {
                    navController.popBackStack()
                },
                navController = navController
            )
        }
        composable(
            route = DestinationsMain.Details.route
        ) {
            val id = it.arguments?.getString("id")
            requireNotNull(id)
            DetailsScreen(
                backStack = {
                    navController.popBackStack()
                },
                argument = id
            )
        }
    }
}
