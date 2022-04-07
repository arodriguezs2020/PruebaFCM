package es.alvarorodriguez.pruebafcm.data.remote.auth

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import es.alvarorodriguez.pruebafcm.data.model.User
import kotlinx.coroutines.tasks.await

class AuthDataSource {

    // Login
    suspend fun signIn(email: String, password: String) : FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).await()
        return authResult.user
    }

    // Register
    suspend fun signUp(name: String, email: String, password: String, tel: String) : FirebaseUser? {
        val authResult = FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).await()

        authResult?.let {
            FirebaseFirestore.getInstance().collection("users").add(
                User(name = name, email = email, password = password, tel = tel)
            )
        }

        return authResult.user
    }

}