package com.example.fancywork.ui.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fancywork.services.AuthService
import com.example.fancywork.services.DBService
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authService: AuthService = AuthService(),
    private val dbService: DBService = DBService()
) : ViewModel() {

    var state: MutableState<RegisterState> = mutableStateOf(RegisterState())
        private set

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
        para que al escribir en el este muestre el texto
    */
    fun onUserEmailChange(email: String) {
        state.value = state.value.copy(userEmail = email)
    }

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
        para que al escribir en el este muestre el texto
    */
    fun onUserPasswordChange(password: String) {
        state.value = state.value.copy(userPassword = password)
    }

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
            para que al escribir en el este muestre el texto
        */
    fun onUserConfirmPasswordChange(confirmPass: String) {
        state.value = state.value.copy(userConfirmPassword = confirmPass)
    }

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
            para que al escribir en el este muestre el texto
        */
    fun onUserPhoneChange(phone: String) {
        state.value = state.value.copy(userPhone = phone)
    }

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
        para que al escribir en el este muestre el texto
    */
    fun onUserNameChange(name: String) {
        state.value = state.value.copy(userName = name)
    }

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
        para que al escribir en el este muestre el texto
    */
    fun onCountryChange(pais: String) {
        state.value = state.value.copy(pais = pais)
    }

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
        para que al escribir en el este muestre el texto
    */
    fun onStateChange(estado: String) {
        state.value = state.value.copy(estado = estado)
    }

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
        para que al escribir en el este muestre el texto
    */
    fun onCityChange(ciudad: String) {
        state.value = state.value.copy(ciudad = ciudad)
    }

    /*  Esta funcion sirve para cambiar el estado del campo en cuestión al momento de escribir
        para que al escribir en el este muestre el texto
    */
    fun onUserLastNameChange(lastname: String) {
        state.value = state.value.copy(userLastName = lastname)
    }

    /* Esta funcion sirve para llamar a la funcion del modelo que guarda
       en la base de datos los datos de un usuario cuando se registra
       ya sea con google o por correo.
    */
    fun register() = viewModelScope.launch {
        state.value = state.value.copy(displayProgressBar = true)
        try {
            if (state.value.userPassword != state.value.userConfirmPassword) {
                throw IllegalArgumentException(
                    "The passwords do not match"
                )
            }
            authService.signUp(
                state.value.userEmail,
                state.value.userPassword,
            ) { isSuccessful ->
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
        } finally {
            state.value = state.value.copy(displayProgressBar = false)
        }
    }

    /* Esta funcion sirve para llamar a la funcion del modelo que guarda
       en la base de datos los datos de un usuario cuando se registra
       ya sea con google o por correo.
    */
    fun registerDb() = viewModelScope.launch {
        try {
            val user = hashMapOf(
                "name" to state.value.userName,
                "lastname" to state.value.userLastName,
                "phone" to state.value.userPhone,
                "city" to state.value.ciudad,
                "country" to state.value.pais,
                "state" to state.value.estado,
                "active" to true,
                "email" to state.value.userEmail,
                "password" to state.value.userPassword,
                "url" to "https://e7.pngegg.com/pngimages/323/705/png-clipart-user-profile-get-em-cardiovascular-disease-zingah-avatar-miscellaneous-white.png"
            )
            dbService.registerUserDB(
                email = state.value.userEmail,
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
data class RegisterState(
    val userName: String = "",
    val userLastName: String = "",
    val userPhone: String = "",
    val userEmail: String = "",
    val ciudad: String = "",
    val pais: String = "",
    val estado: String = "",
    val userPassword: String = "",
    val userConfirmPassword: String = "",
    val successRegister: Boolean = false,
    val successRegisterDB: Boolean = false,
    val displayProgressBar: Boolean = false,
    val errorMessage: String? = null
)