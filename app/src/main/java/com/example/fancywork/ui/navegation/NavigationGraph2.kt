package com.example.fancywork.ui.navegation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fancywork.model.User
import com.example.fancywork.ui.view.HomeScreen
import com.example.fancywork.ui.view.NotificationScreen
import com.example.fancywork.ui.view.SearchScreen
import com.example.fancywork.ui.viewmodel.HomeViewModel


/* funcion que se encarga de administrar las rutas y navegación de las
    pantallas en el menu de la aplicación.
 */
@Composable
fun NavigationGraph2(
    user: User?,
    modifier: Modifier,
    navController: NavHostController,
    mainNavController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.Home.route
    ) {
        composable(
            route = Destinations.Home.route
        ) {
            HomeScreen(
                user = user,
                homeViewModel = HomeViewModel(),
                navController = mainNavController
            )
        }
        composable(
            route = Destinations.Search.route
        ) {
            SearchScreen()
        }
        composable(
            route = Destinations.Notifications.route
        ) {
            NotificationScreen()
        }
    }
}
