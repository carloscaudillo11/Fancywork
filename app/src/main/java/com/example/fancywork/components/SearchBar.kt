package com.example.fancywork.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable


@Composable
@ExperimentalMaterial3Api
fun SearchBar() {
    var text by rememberSaveable { mutableStateOf("") }
    var active by rememberSaveable { mutableStateOf(false) }

    SearchBar(
            query = text,
            onQueryChange = { text = it },
            onSearch = { active = true },
            active = active,
            onActiveChange = {
                active = it
            },
            placeholder = { Text("Hinted search text") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
        ) {

        }
}