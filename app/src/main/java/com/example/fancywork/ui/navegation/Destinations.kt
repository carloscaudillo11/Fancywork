package com.example.fancywork.ui.navegation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector

/* clase utilizada para definir las diferentes rutas del menu principal
 */

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
}
