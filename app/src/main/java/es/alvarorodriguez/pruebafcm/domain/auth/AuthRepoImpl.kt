package es.alvarorodriguez.pruebafcm.domain.auth

import com.google.firebase.auth.FirebaseUser
import es.alvarorodriguez.pruebafcm.data.remote.auth.AuthDataSource

class AuthRepoImpl(private val dataSource: AuthDataSource): AuthRepo {
    override suspend fun signIn(email: String, password: String): FirebaseUser? =
        dataSource.signIn(email, password)

    override suspend fun signUp(name: String, email: String, password: String, tel: String): FirebaseUser? =
        dataSource.signUp(name, email, password, tel)
}