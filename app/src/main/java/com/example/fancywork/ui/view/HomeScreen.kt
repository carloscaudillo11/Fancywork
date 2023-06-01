package com.example.fancywork.ui.view

import android.annotation.SuppressLint
import androidx.compose.foundation.background
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fancywork.R
import com.example.fancywork.model.User
import com.example.fancywork.ui.components.CardService
import com.example.fancywork.ui.components.ErrorDialog
import com.example.fancywork.ui.components.GoogleCard
import com.example.fancywork.ui.components.HomeTopAppBar
import com.example.fancywork.ui.viewmodel.HomeViewModel
import org.jetbrains.annotations.NotNull


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    user: User?,
    homeViewModel: HomeViewModel,
    navController: NavHostController
) {

    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )
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
        Scaffold(
            modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                HomeTopAppBar(
                    scrollBehavior = scrollBehavior,
                    function = {
                        navController.navigate("profile") {
                            launchSingleTop = true
                        }
                    },
                    url = user?.url
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("post") {
                            launchSingleTop = true
                        }
                    },
                ) {
                    Icon(Icons.Filled.Add, contentDescription = "Localized description")
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(rememberScrollState())

            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Text(
                        text = "Según tu ubicación",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Recomendaciones basadas en tu ubicación",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp
                        )
                    )
                }

                CardService(
                    user = user,
                    items = state.value.services,
                    navController= navController
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp, start = 20.dp, bottom = 20.dp)
                ) {
                    Text(
                        text = "Según su popularidad",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "Recomendaciones basadas en la popularidad del servicio",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp
                        )
                    )
                }
                CardService(
                    user = user,
                    items = state.value.services,
                    navController= navController
                )
                GoogleCard(
                    title = "Recibe notificaciones directamente al correo",
                    subtitle = "Proximamente",
                    imageRes = R.drawable.email
                )
            }
        }
    }

    if (state.value.errorMessage != null) {
        ErrorDialog(
            errorMessage = state.value.errorMessage.toString(),
            onDismiss = homeViewModel::hideErrorDialog
        )
    }
}
