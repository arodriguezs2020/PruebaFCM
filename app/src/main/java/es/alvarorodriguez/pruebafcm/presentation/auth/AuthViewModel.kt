package es.alvarorodriguez.pruebafcm.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import es.alvarorodriguez.pruebafcm.domain.auth.AuthRepo
import kotlinx.coroutines.Dispatchers
import es.alvarorodriguez.pruebafcm.core.Result

class AuthViewModel(private val repo: AuthRepo): ViewModel() {
    fun signIn(email: String, password: String) = liveData(Dispatchers.IO){
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.signIn(email, password)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(private val repo: AuthRepo): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AuthViewModel(repo) as T
    }
}