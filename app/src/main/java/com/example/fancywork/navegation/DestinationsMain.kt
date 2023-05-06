package com.example.fancywork.navegation

sealed class DestinationsMain(
    val route: String,
){
    object Login: DestinationsMain("login")
    object Register: DestinationsMain("register")
    object Welcome: DestinationsMain("welcome")
    object Main: DestinationsMain("main")
}
