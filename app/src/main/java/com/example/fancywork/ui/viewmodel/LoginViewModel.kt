package com.example.fancywork.ui.viewmodel

import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.services.AuthService
import kotlinx.coroutines.launch


class LoginViewModel(
    private val repository: AuthService = AuthService()
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

    /*  En esta clase se definen los diferentes estados de la interfaz o pantalla
        que el viewmodel se encargara de modificar para que la pantalla lo pueda
        mostrar.
    */
data class LoginState(
    val email: String = "",
    val password: String = "",
    val successLogin: Boolean = false,
    val displayProgressBar: Boolean = false,
    val errorMessage: String? = null
)