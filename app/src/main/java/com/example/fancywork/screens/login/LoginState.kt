package com.example.fancywork.screens.login


data class LoginState(
    val email: String = "",
    val password: String = "",
    val successLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    val errorMessage: String? = null
)

