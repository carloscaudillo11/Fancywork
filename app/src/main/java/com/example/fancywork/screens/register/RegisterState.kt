package com.example.fancywork.screens.register

data class RegisterState(
    val userName: String = "",
    val userPhone: String = "",
    val userEmail: String = "",
    val userPassword: String = "",
    val userConfirmPassword: String = "",
    val successRegister: Boolean = false,
    val successRegisterDB: Boolean = false,
    val displayProgressBar: Boolean = false,
    val errorMessage: String? = null
)