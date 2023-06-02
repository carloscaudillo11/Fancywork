package com.example.fancywork.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

/* funcion utilizada para devolver un mensaje de alert para mostrar mensajes
    de errores que se le pasen por parametro tambien acepta la funcion
    que ejecutara cuando pulsemos el boton de esconder en el alert.
 */
@Composable
fun ErrorDialog(
    errorMessage: String?,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss?.invoke()
        },
        title = {
            Text(text = "Error")
        },
        text = {
            if (errorMessage != null) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = errorMessage, fontSize = 15.sp)
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onDismiss?.invoke()
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss?.invoke()
                }
            ) {
                Text("Cerrar")
            }
        },
        icon = {
            Icon(Icons.Rounded.Warning, contentDescription = "Icon error")
        }
    )
}