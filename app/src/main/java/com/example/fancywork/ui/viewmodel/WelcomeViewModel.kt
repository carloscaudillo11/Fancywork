package com.example.fancywork.ui.viewmodel


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.services.AuthService
import com.example.fancywork.services.DBService
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

class WelcomeViewModel(
    private val repository: AuthService = AuthService(),
    private val dbService: DBService = DBService()
) : ViewModel() {

    var state: MutableState<WelcomeState> = mutableStateOf(WelcomeState())
        private set
    private val auth = FirebaseAuth.getInstance()

    val email = auth.currentUser?.email

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

    fun registerDb() = viewModelScope.launch {
        try {
            val user = hashMapOf(
                "name" to auth.currentUser?.displayName,
                "lastname" to "",
                "phone" to auth.currentUser?.phoneNumber,
                "city" to "León",
                "country" to "México",
                "state" to "Guanajuato",
                "active" to true,
                "email" to auth.currentUser?.email,
                "password" to "",
                "url" to auth.currentUser?.photoUrl.toString()
            )
            dbService.registerUserDB(
                email = auth.currentUser?.email,
                map = user
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
        }
    }

    fun hideErrorDialog() {
        state.value = state.value.copy(
            errorMessage = null
        )
    }
}

data class WelcomeState(
    val successRegister: Boolean = false,
    val successRegisterDB: Boolean = false,
    val displayProgressBar: Boolean = false,
    val errorMessage: String? = null
)
