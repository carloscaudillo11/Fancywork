package com.example.fancywork.screens.login

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.reprository.AuthRepository
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {
    var state: MutableState<LoginState> = mutableStateOf(LoginState())
        private set

    fun onUserEmailChange(email: String) {
        state.value = state.value.copy(email = email)
    }

    fun onUserPasswordChange(password: String) {
        state.value = state.value.copy(password = password)
    }


    fun login() = viewModelScope.launch {
        state.value = state.value.copy(displayProgressBar = true)
        try {
            if (!Patterns.EMAIL_ADDRESS.matcher(state.value.email).matches()) {
                throw IllegalArgumentException(
                    "Please enter a valid E-Mail"
                )
            }
            repository.signIn(
                state.value.email,
                state.value.password
            ) { isSuccessful ->
                if (isSuccessful) {
                    state.value = state.value.copy(successLogin = true)
                } else
                    state.value = state.value.copy(successLogin = false)
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

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }
}