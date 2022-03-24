package es.alvarorodriguez.pruebafcm.domain.auth

import com.google.firebase.auth.FirebaseUser
import es.alvarorodriguez.pruebafcm.data.remote.auth.AuthDataSource

class AuthRepoImpl(private val dataSource: AuthDataSource): AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        dataSource.signIn(email, password)
}