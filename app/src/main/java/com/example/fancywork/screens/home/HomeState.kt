package com.example.fancywork.screens.home

import com.example.fancywork.objects.User

data class HomeState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)
