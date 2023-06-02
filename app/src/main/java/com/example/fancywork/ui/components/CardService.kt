package com.example.fancywork.ui.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fancywork.model.Service


/* funcion utilizada para devolver un slider de tarjetas con la
    informacion de cada item de una lista en este caso es usado
    para mostrar la lista de servicios por localidad tambien acepta
    un controlador de navegación para poder navegar a la pantalla
    de detalles de cada item.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardService(
    items: List<Service>,
    navController: NavHostController
) {
    LazyRow(contentPadding = PaddingValues(horizontal = 8.dp)) {
        items(items) { item ->
            ElevatedCard(
                onClick = {
                    navController.navigate("details/${item.email}") {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .size(width = 300.dp, height = 150.dp)
                    .padding(horizontal = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                ) {
                    Text(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState()),
                        text = item.title.toString(),
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "$" + item.price.toString(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp
                        ),
                    )
                    Text(
                        text = item.name.toString(),
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp
                        )
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = item.city.toString() + " " + item.state,
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 12.sp
                            )
                        )
                        Text(
                            text = item.date.toString(),
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontSize = 12.sp
                            )
                        )
                    }
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                    ) {
                        ClickableText(
                            text = buildAnnotatedString {
                                withStyle(
                                    style = SpanStyle(
                                        color = MaterialTheme.colorScheme.primary,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp
                                    )
                                ) { append("Contratar") }
                            },
                            onClick = {}
                        )
                        IconButton(
                            onClick = { /*TODO*/ }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite",
                            )
                        }
                    }
                }
            }
        }
    }
}


