package com.example.fancywork.ui.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Destinations(
        "home", "Inicio",
        Icons.Filled.Home
    )

    object Search : Destinations(
        "search", "Buscar",
        Icons.Outlined.Search
    )

    object Notifications : Destinations(
        "notifications", "Notificaciones",
        Icons.Outlined.Notifications
    )

    object Menu : Destinations(
        "Menu", "Menu",
        Icons.Filled.Menu
    )
}
