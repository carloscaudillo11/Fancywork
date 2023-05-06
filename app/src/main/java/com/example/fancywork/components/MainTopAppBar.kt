package com.example.fancywork.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.fancywork.R

@Composable
@ExperimentalMaterial3Api
fun MainTopAppBar(
    drawerOpen: () -> Unit,
    scrollBehavior : TopAppBarDefaults,
    callback: () -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.icono),
                modifier = Modifier
                    .height(110.dp),
                contentDescription = "Logo Image",
                contentScale = ContentScale.Inside
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    drawerOpen.invoke()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Icon Menu",

                    )
            }
        },
        actions = {
            IconButton(
                onClick = { callback.invoke() }
            ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "Icon Search",
                )
            }
        },
        scrollBehavior = scrollBehavior.enterAlwaysScrollBehavior()
    )
}