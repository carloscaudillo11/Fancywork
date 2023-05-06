package com.example.fancywork.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun SocialButton(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int,
    shape: Shape,
    borderColor: Color,
    backgroundColor: Color,
    color: Color,
    onClicked: () -> Unit
) {

    Surface(
        modifier = modifier
            .clickable {
                onClicked()
            },
        shape = shape,
        border = BorderStroke(width = 1.dp, color = borderColor),
        color = backgroundColor
    ) {
        Row(
            modifier = Modifier
                .padding(
                    start = 22.dp,
                    end = 22.dp,
                    top = 15.dp,
                    bottom = 15.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = "Social Button",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = text,
                color = color
            )
        }
    }
}
