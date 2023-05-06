package com.example.fancywork.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.ShapeDefaults.Large
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.fancywork.navegation.Destinations
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun DrawerItem(
    item: Destinations,
    drawerState: DrawerState,
    scope: CoroutineScope,
    onClick: () -> Unit,
) {
    NavigationDrawerItem(
        modifier = Modifier
            .padding(NavigationDrawerItemDefaults.ItemPadding),
        shape = Large,
        icon = { Icon(item.icon, contentDescription = item.title) },
        label = {
            Text(
                text = item.title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                ),
            )
        },
        selected = false,
        onClick = {
            scope.launch { drawerState.close() }
            onClick.invoke()
        }
    )
}