package com.example.fancywork.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.model.Result
import com.example.fancywork.model.Service
import com.example.fancywork.services.DBService
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DetailsViewModel(
    private val dbService: DBService = DBService()
) : ViewModel() {
    var state: MutableState<DetailsState> = mutableStateOf(DetailsState())
        private set

    fun getService(email: String) {
        dbService.getService(email).onEach { result ->
            when (result) {
                is Result.Error -> {
                    state.value =
                        DetailsState(errorMessage = result.message ?: "Error to get user")
                }

                is Result.Loading -> {
                    state.value = DetailsState(isLoading = true)
                }

                is Result.Success -> {
                    state.value = DetailsState(service = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

}

data class DetailsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val service: List<Service> = emptyList(),
    val success: Boolean = false,
    val displayProgressBar: Boolean = false,
    val email: String = ""
)