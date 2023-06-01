package com.example.fancywork.ui.view


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fancywork.ui.components.RoundedButton
import com.example.fancywork.ui.viewmodel.DetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    backStack: () -> Unit,
    argument: String,
    detailsViewModel: DetailsViewModel = DetailsViewModel(),
) {
    val scrollBehavior = TopAppBarDefaults
    val state = detailsViewModel.state
    detailsViewModel.getService(argument)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {},
                scrollBehavior = scrollBehavior.enterAlwaysScrollBehavior(),
                navigationIcon = {
                    IconButton(onClick = { backStack.invoke() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "back",
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "favorite",
                        )
                    }
                }
            )
        }
    ) {
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
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = it.calculateTopPadding())
                    .background(MaterialTheme.colorScheme.background)
            ) {
                LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
                    items(state.value.service) { item ->
                        if (item.email == argument) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState())
                                    .padding(20.dp),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                item.title?.let { it1 ->
                                    Text(
                                        text = it1,
                                        style = MaterialTheme.typography.headlineMedium.copy(
                                            fontSize = 30.sp
                                        ),
                                        textAlign = TextAlign.Center
                                    )
                                }

                                item.price?.let { it1 ->
                                    Text(
                                        text = "Precio por hora $$it1",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontSize = 16.sp
                                        ),
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.outline
                                    )
                                }
                                Spacer(modifier = Modifier.height(8.dp))
                                item.name?.let { it1 ->
                                    Text(
                                        text = it1,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontSize = 18.sp,
                                            fontWeight = FontWeight.Medium,
                                        ),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                item.city?.let { it1 ->
                                    Text(
                                        text = it1 + ", " + item.state,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Normal,
                                        ),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                item.date?.let { it1 ->
                                    Text(
                                        text = it1,
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Normal,
                                        ),
                                        textAlign = TextAlign.Center,
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 60.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    RoundedButton(
                                        text = "Contratar",
                                        displayProgressBar = state.value.displayProgressBar,
                                        enabled = true,
                                        onClick = {

                                        }
                                    )
                                }
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = "Descripción",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontSize = 18.sp,
                                        fontWeight = FontWeight.Medium,
                                    ),
                                    textAlign = TextAlign.Center,
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Column(modifier = Modifier.width(350.dp)) {
                                    item.description?.let { it1 ->
                                        Text(
                                            text = it1,
                                            style = MaterialTheme.typography.labelMedium.copy(
                                                fontSize = 18.sp,
                                                fontWeight = FontWeight.Normal,
                                            ),
                                            textAlign = TextAlign.Justify,
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

