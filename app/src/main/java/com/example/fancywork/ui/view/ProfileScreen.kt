package com.example.fancywork.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.fancywork.ui.components.ErrorDialog
import com.example.fancywork.ui.components.RoundedButton
import com.example.fancywork.ui.navegation.DestinationsMain
import com.example.fancywork.ui.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    backStack: () -> Unit,
    profileViewModel: MainViewModel = MainViewModel(),
    navController: NavHostController
) {
    val scrollBehavior = TopAppBarDefaults
    val state = profileViewModel.state

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Perfíl",
                        overflow = TextOverflow.Ellipsis
                    )
                },
                scrollBehavior = scrollBehavior.enterAlwaysScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = { backStack.invoke() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                }
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .background(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .align(Alignment.CenterHorizontally),
                    model = state.value.user?.url,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                ) {
                    state.value.user?.name?.let { it1 ->
                        Text(
                            text = it1 + " " + state.value.user?.lastname,
                            style = MaterialTheme.typography.headlineMedium,
                            textAlign = TextAlign.Center
                        )
                    }

                    state.value.user?.city?.let { it1 ->
                        Text(
                            text = it1 + " " + state.value.user?.state,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 15.sp
                            ),
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.outline
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    val verde = Color(0xFF008f39)
                    val rojo = Color(0xFFe61919)
                    Box(
                        modifier = Modifier
                            .background(
                                if (state.value.user?.active == true) verde else rojo,
                                shape = RoundedCornerShape(40.dp)
                            )
                            .padding(6.dp)
                    ) {
                        Text(
                            text = if (state.value.user?.active == true) "Perfil Activo" else "Perfil active",
                            style = MaterialTheme.typography.labelMedium,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(modifier = Modifier.padding(5.dp))
                }
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier.padding(top = 10.dp, start = 30.dp)
                ) {
                    Text(
                        text = "Datos de Contacto",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontSize = 18.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    state.value.user?.phone?.let { it1 ->
                        Text(
                            text = it1,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        )
                    }
                    state.value.user?.email?.let { it1 ->
                        Text(
                            text = it1,
                            style = MaterialTheme.typography.labelLarge.copy(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal,
                            )
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Divider(modifier = Modifier.padding(5.dp))
                }
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    RoundedButton(
                        text = "Cerrar Sesión",
                        displayProgressBar = state.value.displayProgressBar,
                        enabled = true,
                        onClick = {
                            profileViewModel.signOut(callback = {
                                navController.navigate(DestinationsMain.Welcome.route) {
                                    launchSingleTop = true
                                }
                            })
                        }
                    )
                }
            }
        }
        if (state.value.errorMessage != null) {
            ErrorDialog(
                errorMessage = state.value.errorMessage.toString(),
                onDismiss = profileViewModel::hideErrorDialog
            )
        }
    }
}



