package es.alvarorodriguez.pruebafcm.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.domain.home.HomeRepo
import kotlinx.coroutines.Dispatchers

class HomeViewModel(private val repo: HomeRepo): ViewModel() {

    fun getCars() = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        kotlin.runCatching {
            repo.getCars()
        }.onSuccess { listCars ->
            emit(listCars)
        }.onFailure { error ->
            emit(Result.Failure(Exception(error.message)))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class HomeViewModelFactory(private val repo: HomeRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomeViewModel(repo) as T
    }
}