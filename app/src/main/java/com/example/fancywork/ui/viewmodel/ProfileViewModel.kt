package com.example.fancywork.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.model.Result
import com.example.fancywork.model.User
import com.example.fancywork.services.DBService
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ProfileViewModel(
    private val dbService: DBService = DBService()
) : ViewModel() {

    var state: MutableState<ProfileState> = mutableStateOf(ProfileState())
        private set


}
data class ProfileState(
    val isLoading: Boolean = false,
    val user: User? = null,
    val errorMessage: String? = null
)
