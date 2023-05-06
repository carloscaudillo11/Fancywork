package com.example.fancywork.screens.welcome


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.reprository.AuthRepository
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val repository: AuthRepository = AuthRepository(),
) : ViewModel() {

    var state: MutableState<WelcomeState> = mutableStateOf(WelcomeState())
        private set


    fun googleSignIn(
        token: String,
    ) = viewModelScope.launch {
        try {
            repository.googleSignUp(token) { isSuccessful ->
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
        }
    }


    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

}