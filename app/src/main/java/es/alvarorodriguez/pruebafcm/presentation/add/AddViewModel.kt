package es.alvarorodriguez.pruebafcm.presentation.add

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.domain.add.AddRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class AddViewModel(private val repo: AddRepo) : ViewModel() {

    fun addCar(marca: String, description: String, imgCar: Bitmap) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.addCar(marca, description, imgCar)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

@Suppress("UNCHECKED_CAST")
class AddViewModelFactory(private val repo: AddRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddViewModel(repo) as T
    }
}