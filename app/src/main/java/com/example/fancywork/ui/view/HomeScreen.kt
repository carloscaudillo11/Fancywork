package com.example.fancywork.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.fancywork.ui.components.DrawerItem
import com.example.fancywork.ui.components.ErrorDialog
import com.example.fancywork.ui.components.EventDialog
import com.example.fancywork.ui.components.HeadDrawer
import com.example.fancywork.ui.components.HomeTopAppBar
import com.example.fancywork.ui.navegation.Destinations
import com.example.fancywork.ui.viewmodel.HomeViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel,
    navController: NavHostController
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val itemsDrawer = listOf(
        Destinations.Settings,
        Destinations.Rate,
        Destinations.Help,
        Destinations.SingOut
    )

    val darkTheme = isSystemInDarkTheme()
    val scrollBehavior = TopAppBarDefaults
    val state = homeViewModel.state

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
        val name = state.value.user?.name.toString()
        val url = state.value.user?.url.toString()
        val email = state.value.user?.email.toString()

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet(
                    drawerShape = RectangleShape
                ) {
                    Column(
                        modifier = Modifier.width(300.dp)
                    ) {
                        HeadDrawer(
                            url = url,
                            name = name,
                            email = email
                        )
                        Divider()
                        itemsDrawer.forEach { item ->
                            if (item.route == "welcome") {
                                Divider()
                                Spacer(Modifier.height(5.dp))
                            }
                            DrawerItem(
                                item = item,
                                drawerState = drawerState,
                                scope = scope
                            ) {
                                if (item.route == "welcome") {
                                    state.value = state.value.copy(
                                        sure = true
                                    )
                                }
                                if (item.route == "rate") {
                                    state.value = state.value.copy(
                                        rate = true
                                    )
                                }
                                if (item.route == "settings") {
                                    navController.navigate(item.route) {
                                        launchSingleTop = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    HomeTopAppBar(
                        drawerOpen = { scope.launch { drawerState.open() } },
                        scrollBehavior = scrollBehavior,
                        function = {
                            //homeViewModel.signOut(navigateWelcome = navigateWelcome)
                        }
                    )
                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        modifier = Modifier.padding(bottom = 100.dp),
                        onClick = { /* do something */ },
                        icon = { Icon(Icons.Filled.Add, "Add") },
                        text = { Text(text = "Publicar") },
                    )
                }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = it.calculateTopPadding())
                        .background(if (darkTheme) Color.Black else Color.White)
                        .verticalScroll(rememberScrollState())
                ) {

                }
            }
        }
    }

    if (state.value.errorMessage != null) {
        ErrorDialog(
            errorMessage = state.value.errorMessage.toString(),
            onDismiss = homeViewModel::hideErrorDialog
        )
    }

    if (state.value.rate) {
        EventDialog(
            title = "Calificanos",
            eventMessage = "Redireccionando a la tienda de aplicaciones",
            onDismiss = homeViewModel::hideEventDialog,
            icon = {
                Icon(Icons.Outlined.Star, contentDescription = "Rate")
            }
        )
    }

    if(state.value.sure){
        EventDialog(
            title = "Salir",
            eventMessage = "¿Deseas cerrar sesión?",
            onConfirm = {
                homeViewModel.signOut()
                navController.navigate("welcome") {
                    launchSingleTop = true
                }
            },
            onDismiss = homeViewModel::hideEventDialog,
        )
    }
}