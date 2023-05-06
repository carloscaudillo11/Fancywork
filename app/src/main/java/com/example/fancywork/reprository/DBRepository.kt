package com.example.fancywork.reprository

import com.example.fancywork.objects.User
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class DBRepository {
    private val db = Firebase.firestore

    suspend fun registerDB(
        email: String?,
        map: Map<String, String?>,
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

    fun geUser(email: String): Flow<Result<User>> = flow {
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
}