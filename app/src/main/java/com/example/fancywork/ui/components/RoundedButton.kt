package com.example.fancywork.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


/* funcion utilizada para devolver un boton que acepta como parametros un
    modificador para poder cambiar su apariencia, el texto que queremos
    que muestre, una variable booleana para que en caso que sea true
    muestre una barra de progreso, la funcion que invocara al hacer click
    y una variable booleana para activar o desactivar el boton.
 */
@Composable
fun RoundedButton(
    modifier: Modifier = Modifier,
    text: String,
    displayProgressBar: Boolean = false,
    onClick: () -> Unit,
    enabled: Boolean = false
) {
    if(!displayProgressBar) {
        Button(
            modifier = modifier
                .width(250.dp)
                .height(50.dp),
            onClick = onClick,
            enabled = enabled
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleLarge
            )
        }
    } else {
        CircularProgressIndicator(
            modifier = Modifier.size(50.dp),
            color = MaterialTheme.colorScheme.primary,
            strokeWidth = 6.dp
        )
    }
}