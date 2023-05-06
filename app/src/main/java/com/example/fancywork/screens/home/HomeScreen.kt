package com.example.fancywork.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.fancywork.components.DrawerItem
import com.example.fancywork.components.EventDialog
import com.example.fancywork.components.HeadDrawer
import com.example.fancywork.components.MainTopAppBar
import com.example.fancywork.navegation.Destinations
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
        Destinations.Notifications,
        Destinations.Help,
        Destinations.SingOut
    )

    val darkTheme = isSystemInDarkTheme()
    val scrollBehavior = TopAppBarDefaults
    val state = homeViewModel.state
    var name by remember { mutableStateOf("") }
    var url by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }

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
        name = state.value.user?.name.toString()
        url = state.value.user?.url.toString()
        email = state.value.user?.email.toString()

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
                                if(item.route == "welcome"){
                                    homeViewModel.signOut()
                                }
                                navController.navigate(item.route) {
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                topBar = {
                    MainTopAppBar(
                        drawerOpen = { scope.launch { drawerState.open() } },
                        scrollBehavior = scrollBehavior,
                        callback = {
                            //homeViewModel.signOut(navigateWelcome = navigateWelcome)
                        }
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
        EventDialog(
            errorMessage = state.value.errorMessage.toString(),
            onDismiss = homeViewModel::hideErrorDialog
        )
    }
}