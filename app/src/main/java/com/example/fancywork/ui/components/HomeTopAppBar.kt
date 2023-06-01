package com.example.fancywork.ui.components


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.fancywork.R

@Composable
@ExperimentalMaterial3Api
fun HomeTopAppBar(
    scrollBehavior : TopAppBarScrollBehavior,
    function: () -> Unit,
    url: Any?
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.icono),
                modifier = Modifier
                    .height(110.dp),
                contentDescription = "Logo Image",
                contentScale = ContentScale.Inside
            )
        },
        actions = {
            AsyncImage(
                modifier = Modifier.clickable{
                    function.invoke()
                }
                    .size(30.dp)
                    .clip(CircleShape),
                model = url,
                contentDescription = null,
                contentScale = ContentScale.Crop,
            )
        },
        scrollBehavior = scrollBehavior
    )
}