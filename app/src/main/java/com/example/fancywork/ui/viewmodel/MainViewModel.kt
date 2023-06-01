package com.example.fancywork.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.model.Result
import com.example.fancywork.model.User
import com.example.fancywork.services.DBService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.LocalDate

class MainViewModel(
    private val dbService: DBService = DBService()
) : ViewModel() {
    var state: MutableState<MainState> = mutableStateOf(MainState())
        private set

    private val auth = FirebaseAuth.getInstance()
    val email = auth.currentUser?.email

    private fun getUser() {
        if (email != null) {
            dbService.getUser(email).onEach { result ->
                when (result) {
                    is Result.Error -> {
                        state.value =
                            MainState(errorMessage = result.message ?: "Error to get user")
                    }

                    is Result.Loading -> {
                        state.value = MainState(isLoading = true)
                    }

                    is Result.Success -> {
                        state.value = MainState(user = result.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun signOut(callback: () -> Unit) {
        auth.signOut()
        callback.invoke()
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

    private val fechaActual: LocalDate = LocalDate.now()
    private val fechaActualString = fechaActual.toString()

    fun onTitleChange(title: String) {
        state.value = state.value.copy(title = title)
    }

    fun onDescriptionChange(description: String) {
        state.value = state.value.copy(description = description)
    }

    fun onPriceChange(price: String) {
        state.value = state.value.copy(price = price)
    }

    fun registerDb() = viewModelScope.launch {
        state.value = state.value.copy(displayProgressBar = true)
        try {
            val service = hashMapOf(
                "title" to state.value.title,
                "price" to state.value.price,
                "description" to state.value.description,
                "name" to state.value.user?.name + " " + state.value.user?.lastname,
                "email" to state.value.user?.email,
                "city" to state.value.user?.city,
                "country" to state.value.user?.country,
                "state" to state.value.user?.state,
                "date" to fechaActualString
            )
            dbService.registerServiceDB(
                map = service
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
        } finally {
            state.value = state.value.copy(displayProgressBar = false)
        }
    }

    init {
        getUser()
    }
}

data class MainState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val user: User? = null,
    val title: String = "",
    val price: String = "",
    val description: String = "",
    val successRegisterDB: Boolean = false,
    val displayProgressBar: Boolean = false,
)