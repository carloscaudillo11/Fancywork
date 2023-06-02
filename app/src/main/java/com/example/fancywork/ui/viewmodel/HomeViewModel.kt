package com.example.fancywork.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.model.User
import com.example.fancywork.services.DBService
import com.example.fancywork.model.Result
import com.example.fancywork.model.Service
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class HomeViewModel(
    private val dbService: DBService = DBService()
) : ViewModel() {

    var state: MutableState<HomeState> = mutableStateOf(HomeState())
        private set

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

    private fun getService() {
        dbService.getServices().onEach { result ->
            when (result) {
                is Result.Error -> {
                    state.value =
                        HomeState(errorMessage = result.message ?: "Error to get user")
                }
                is Result.Loading -> {
                    state.value = HomeState(isLoading = true)
                }
                is Result.Success -> {
                    state.value = HomeState(services = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }

    init {
        getService()
    }
}

    /*  En esta clase se definen los diferentes estados de la interfaz o pantalla
        que el viewmodel se encargara de modificar para que la pantalla lo pueda
        mostrar.
    */
data class HomeState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val services: List<Service> = emptyList(),
    val user: User? = null
)
