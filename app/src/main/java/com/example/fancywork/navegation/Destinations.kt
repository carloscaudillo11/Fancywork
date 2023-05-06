package com.example.fancywork.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Destinations(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : Destinations(
        "home", "Inicio",
        Icons.Outlined.Home
    )

    object Search : Destinations(
        "search", "Buscar",
        Icons.Outlined.Search
    )

    object Post : Destinations(
        "post", "Publicar",
        Icons.Outlined.Add
    )

    object Notifications : Destinations(
        "notifications", "Notificaciones",
        Icons.Outlined.Notifications
    )

    object Profile : Destinations(
        "profile", "Perfil",
        Icons.Outlined.AccountCircle
    )

    object Settings : Destinations(
        "settings", "Configuración",
        Icons.Outlined.Settings
    )

    object Help : Destinations(
        "help", "Ayuda y comentarios",
        Icons.Outlined.Info
    )

    object SingOut : Destinations(
        "welcome", "Cerrar Sesión",
        Icons.Outlined.ExitToApp
    )

}
