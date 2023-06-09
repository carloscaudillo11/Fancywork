package com.example.fancywork.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.fancywork.ui.components.TopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen() {
    val darkTheme = isSystemInDarkTheme()
    val scrollBehavior = TopAppBarDefaults
    Scaffold(
        topBar = {
            TopAppBar(
                scrollBehavior = scrollBehavior,
                title = "Notificaciones"
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .background(if (darkTheme) Color.Black else Color.White)
        ) {

        }
    }
}