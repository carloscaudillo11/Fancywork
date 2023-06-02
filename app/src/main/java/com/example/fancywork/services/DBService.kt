package com.example.fancywork.services

import com.example.fancywork.model.Result
import com.example.fancywork.model.Service
import com.example.fancywork.model.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
class DBService {
    private val db = Firebase.firestore

    /* Funcion que trabaja en segundo plano para guardar en la base de datos la
       información de cada usuario al momento de registrarse.
    */
    suspend fun registerUserDB(
        email: String?,
        map: Map<String, Any?>,
        onComplete: (Boolean) -> Unit
    ): Unit = withContext(Dispatchers.IO) {
        if (email != null) {
            db.collection("Users")
                .document(email)
                .set(map)
                .addOnCompleteListener {
                    onComplete.invoke(it.isSuccessful)
                }.await()
        }
    }
    /* Funcion que trabaja en segundo plano para guardar en la base de datos la
       información de cada servicio publicado.
    */
    suspend fun registerServiceDB(
        map: Map<String, Any?>,
        onComplete: (Boolean) -> Unit
    ): Unit = withContext(Dispatchers.IO) {
        db.collection("Services")
            .document()
            .set(map)
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }.await()
    }

    /* Funcion que trabaja en segundo plano para obtener la información de los
       usuarios registrados.
   */
    fun getUser(email: String): Flow<Result<User>> = flow {
        try {
            emit(Result.Loading())
            val user = db.collection("Users")
                .document(email)
                .get()
                .await()
            val data = user.toObject(User::class.java)
            emit(Result.Success(data = data))
        } catch (ex: Exception) {
            emit(Result.Error(message = ex.localizedMessage ?: "Error"))
        }
    }
    /* Funcion que trabaja en segundo plano para obtener la información de todos
       los servicios registrados.
   */
    fun getServices(): Flow<Result<List<Service>>> = flow {
        try {
            emit(Result.Loading())
            val service = db.collection("Services")
                .get()
                .await()
                .map { queryDocumentSnapshot ->
                    queryDocumentSnapshot.toObject(Service::class.java)
                }
            emit(Result.Success(data = service))
        } catch (ex: Exception) {
            emit(Result.Error(message = ex.localizedMessage ?: "Error"))
        }
    }
    /* Funcion que trabaja en segundo plano para obtener la información de
       los servicios registrados por email para la pantalla de detalles.
   */
    fun getService(email: String): Flow<Result<List<Service>>> = flow {
        try {
            emit(Result.Loading())
            val service = db.collection("Services")
                .whereEqualTo("email", email)
                .get()
                .await()
            val data = service.toObjects(Service::class.java)
            emit(Result.Success(data = data))
        } catch (ex: Exception) {
            emit(Result.Error(message = ex.localizedMessage ?: "Error"))
        }
    }
}