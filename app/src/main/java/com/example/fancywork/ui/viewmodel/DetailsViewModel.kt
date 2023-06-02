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

/* En esta clase se controla el estado de la pantalla de detalles asi como las
    funciones que esta tiene disponibles ademas se encarga de conectar la
    pantalla con el modelo que se encarga de traer los datos de la base,
 */
class DetailsViewModel(
    private val dbService: DBService = DBService()
) : ViewModel() {
    var state: MutableState<DetailsState> = mutableStateOf(DetailsState())
        private set


    /* funcion que llama a la funcion del modelo que trae los datos de los
        servicios y maneja sus diferentes estados como su estado cuando esta
        cargando cuando completo la carga o si tiene algun error.
     */
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

    /*  En esta clase se definen los diferentes estados de la interfaz o pantalla
        que el viewmodel se encargara de modificar para que la pantalla lo pueda
        mostrar.
    */
data class DetailsState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val service: List<Service> = emptyList(),
    val success: Boolean = false,
    val displayProgressBar: Boolean = false,
    val email: String = ""
)