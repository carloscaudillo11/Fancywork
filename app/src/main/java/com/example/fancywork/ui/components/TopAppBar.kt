package com.example.fancywork.ui.components

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextOverflow


/* funcion utilizada para devolver un topappbar comun que
   acepta como parametro el titulo.
 */
@Composable
@ExperimentalMaterial3Api
fun TopAppBar(
    scrollBehavior: TopAppBarDefaults,
    title: String,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        scrollBehavior = scrollBehavior.enterAlwaysScrollBehavior()
    )
}