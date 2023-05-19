package com.example.fancywork.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.model.User
import com.example.fancywork.services.DBService
import com.example.fancywork.model.Result
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


class HomeViewModel(
    private val dbService: DBService = DBService()
) : ViewModel() {

    var state: MutableState<HomeState> = mutableStateOf(HomeState())
        private set

    private val auth = FirebaseAuth.getInstance()

    val email = auth.currentUser?.email

    private fun getUser() {
        if (email != null) {
            dbService.geUser(email).onEach { result ->
                when (result) {
                    is Result.Error -> {
                        state.value =
                            HomeState(errorMessage = result.message ?: "Error to get user")
                    }

                    is Result.Loading -> {
                        state.value = HomeState(isLoading = true)
                    }

                    is Result.Success -> {
                        state.value = HomeState(user = result.data)
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    private fun getUserGoogle(){
        if (auth.currentUser != null) {
            val user = User(
                email = auth.currentUser?.email,
                name = auth.currentUser?.displayName,
                password = "",
                phone = auth.currentUser?.phoneNumber,
                url = auth.currentUser?.photoUrl.toString()
            )
            state.value = HomeState(user = user)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }

    fun hideEventDialog() {
        state.value = state.value.copy(
            rate = false
        )
        state.value = state.value.copy(
            sure = false
        )
    }

    init {
        val isGoogleSignIn = auth.currentUser?.providerData?.any { userInfo ->
            userInfo.providerId == GoogleAuthProvider.PROVIDER_ID
        } ?: false

        if (isGoogleSignIn)
            getUserGoogle()
        else{
            getUser()
        }
    }
}

data class HomeState(
    val isLoading: Boolean = false,
    val rate: Boolean = false,
    val sure: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)
