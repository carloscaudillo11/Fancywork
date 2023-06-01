package com.example.fancywork.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fancywork.model.Service
import com.example.fancywork.model.User
import com.example.fancywork.ui.navegation.Destinations
import com.example.fancywork.ui.viewmodel.HomeViewModel
import com.example.fancywork.ui.viewmodel.MainViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel,
    mainNavController: NavHostController
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val itemsBar = listOf(
        Destinations.Home,
        Destinations.Search,
        Destinations.Notifications,
    )
    val state = mainViewModel.state

    if (state.value.isLoading) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.surface
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator()
            }
        }
    } else {
        Scaffold(
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.background
                ) {
                    itemsBar.forEach { item ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title,
                                )
                            },
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
            NavigationGraph2(
                user = state.value.user,
                modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                navController = navController,
                mainNavController = mainNavController
            )
        }
    }
}

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
        composable(
            route = Destinations.Menu.route
        ) {
            MenuScreen()
        }
    }
}



