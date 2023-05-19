package com.example.fancywork.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.services.AuthService
import com.example.fancywork.services.DBService
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authService: AuthService = AuthService(),
    private val dbService: DBService = DBService()
) : ViewModel() {

    var state: MutableState<RegisterState> = mutableStateOf(RegisterState())
        private set

    fun onUserEmailChange(email: String) {
        state.value = state.value.copy(userEmail = email)
    }

    fun onUserPasswordChange(password: String) {
        state.value = state.value.copy(userPassword = password)
    }

    fun onUserConfirmPasswordChange(confirmPass: String) {
        state.value = state.value.copy(userConfirmPassword = confirmPass)
    }

    fun onUserPhoneChange(phone: String) {
        state.value = state.value.copy(userPhone = phone)
    }

    fun onUserNameChange(name: String) {
        state.value = state.value.copy(userName = name)
    }

    fun register() = viewModelScope.launch {
        state.value = state.value.copy(displayProgressBar = true)
        try {
            if (state.value.userPassword != state.value.userConfirmPassword) {
                throw IllegalArgumentException(
                    "The passwords do not match"
                )
            }
            authService.signUp(
                state.value.userEmail,
                state.value.userPassword,
            ) { isSuccessful ->
                if (isSuccessful) {
                    state.value = state.value.copy(successRegister = true)
                } else
                    state.value = state.value.copy(successRegister = false)
            }
        } catch (ex: Exception) {
            state.value = state.value.copy(
                errorMessage = ex.localizedMessage
            )
            ex.printStackTrace()
        } finally {
            state.value = state.value.copy(displayProgressBar = false)
        }
    }


    fun registerDb() = viewModelScope.launch {
        try {
            val user = hashMapOf(
                "name" to state.value.userName,
                "phone" to state.value.userPhone,
                "email" to state.value.userEmail,
                "password" to state.value.userPassword,
                "url" to "https://e7.pngegg.com/pngimages/323/705/png-clipart-user-profile-get-em-cardiovascular-disease-zingah-avatar-miscellaneous-white.png"
            )
            dbService.registerDB(
                email = state.value.userEmail,
                map = user
            ) { isSuccessful ->
                if (isSuccessful) {
                     state.value = state.value.copy(successRegisterDB = true)
                } else
                    state.value = state.value.copy(successRegisterDB = false)
            }
        } catch (ex: Exception) {
            state.value = state.value.copy(
                errorMessage = ex.localizedMessage
            )
            ex.printStackTrace()
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }
}

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