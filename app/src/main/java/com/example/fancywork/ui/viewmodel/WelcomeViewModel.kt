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

    /* Esta funcion sirve para llamar a la funcion del modelo que crea un usuaria
        en auth firebase por medio de google.
    */
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

    /* Esta funcion sirve para llamar a la funcion del modelo que guarda
       en la base de datos los datos de un usuario cuando se registra
       ya sea con google o por correo.
    */
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

    /*  Esta funcion sirve para poder quitar los alert de error de esta pantalla. */
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
data class WelcomeState(
    val successRegister: Boolean = false,
    val successRegisterDB: Boolean = false,
    val displayProgressBar: Boolean = false,
    val errorMessage: String? = null
)
