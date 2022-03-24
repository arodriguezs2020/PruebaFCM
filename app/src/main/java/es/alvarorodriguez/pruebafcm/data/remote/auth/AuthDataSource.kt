package es.alvarorodriguez.pruebafcm.data.remote.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthDataSource {

    // Login
    suspend fun signIn(email: String, password: String) : FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        FirebaseAuth.getInstance()
        return authResult.user
    }

}