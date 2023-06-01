package com.example.fancywork.ui.navegation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument


sealed class DestinationsMain(
    val route: String,
    val arguments: List<NamedNavArgument>
){
    object Login: DestinationsMain("login", emptyList())
    object Register: DestinationsMain("register", emptyList())
    object Welcome: DestinationsMain("welcome", emptyList())
    object Main: DestinationsMain("main", emptyList())
    object Post : DestinationsMain("post", emptyList())
    object Profile : DestinationsMain("profile", emptyList())

    object Details : DestinationsMain("details/{id}", arguments = listOf(
        navArgument("id"){
            type = NavType.StringType
        }
    ))
}
