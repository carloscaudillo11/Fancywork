package com.example.fancywork.screens.welcome

data class WelcomeState(
    val successRegister: Boolean = false,
    val successRegisterDB: Boolean = false,
    val displayProgressBar: Boolean = false,
    val errorMessage: String? = null
)
