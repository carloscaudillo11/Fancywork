package com.example.fancywork.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable

@Composable
fun EventDialog(
    errorMessage: String,
    onDismiss: (() -> Unit)? = null
) {
    AlertDialog(
        onDismissRequest = {
            onDismiss?.invoke()
        },
        title = {
            Text(
                text = "Error",
            )
        },
        text = {
            Text(text = errorMessage)
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