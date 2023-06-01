package com.example.fancywork.ui.viewmodel


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.fancywork.model.User

class SettingsViewModel : ViewModel() {

    var state: MutableState<SettingsState> = mutableStateOf(SettingsState())
        private set

    fun hideEventDialog() {
        state.value = state.value.copy(
            rate = false
        )
        state.value = state.value.copy(
            sure = false
        )
    }
}

data class SettingsState(
    val isLoading: Boolean = false,
    val rate: Boolean = false,
    val sure: Boolean = false,
    val user: User? = null,
)
