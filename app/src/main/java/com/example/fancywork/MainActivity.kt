package com.example.fancywork

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.fancywork.navegation.NavigationGraph
import com.example.fancywork.ui.theme.FancyWorkTheme
import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            FancyWorkTheme {
                NavigationGraph()
            }
        }
    }
}

