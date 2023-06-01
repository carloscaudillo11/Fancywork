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

class SearchViewModel(
    private val dbService: DBService = DBService()
) : ViewModel() {
    var state: MutableState<SearchState> = mutableStateOf(SearchState())
        private set

    fun onChangeQuery(query: String){
        state.value = state.value.copy(query = query)
    }

    fun getServices() {
        dbService.getServices().onEach { result ->
            when (result) {
                is Result.Error -> {
                    state.value =
                        SearchState(errorMessage = result.message ?: "Error to get user")
                }
                is Result.Loading -> {
                    state.value = SearchState(isLoading = true)
                }
                is Result.Success -> {
                    state.value = SearchState(services = result.data ?: emptyList())
                }
            }
        }.launchIn(viewModelScope)
    }
}

data class SearchState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val services: List<Service> = emptyList(),
    val displayProgressBar: Boolean = false,
    val query : String = ""
)