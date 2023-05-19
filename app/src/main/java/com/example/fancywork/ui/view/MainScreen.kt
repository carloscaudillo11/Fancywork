package com.example.fancywork.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.fancywork.ui.navegation.Destinations
import com.example.fancywork.ui.viewmodel.HomeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainNavController: NavHostController
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val itemsBar = listOf(
        Destinations.Home,
        Destinations.Search,
        Destinations.Notifications,
        Destinations.Profile,
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                itemsBar.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.title) },
                        label = { Text(item.title) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
            }
        }
    ) {
        NavigationGraph(
            navController = navController,
            mainNavController = mainNavController
        )
    }
}

@Composable
fun NavigationGraph(
    navController: NavHostController,
    mainNavController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Home.route
    ) {
        composable(
            route = Destinations.Home.route
        ) {
            HomeScreen(
                homeViewModel = HomeViewModel(),
                navController = mainNavController
            )
        }
        composable(
            route = Destinations.Search.route
        ) {
            SearchScreen(
                backStack = {
                    navController.popBackStack()
                }
            )
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
            route = Destinations.Post.route
        ) {
            PostScreen(
                callBack = {
                    navController.popBackStack()
                }
            )
        }
        composable(
            route = Destinations.Profile.route
        ) {
            ProfileScreen(
                backStack = {
                    navController.popBackStack()
                }
            )
        }
    }
}



