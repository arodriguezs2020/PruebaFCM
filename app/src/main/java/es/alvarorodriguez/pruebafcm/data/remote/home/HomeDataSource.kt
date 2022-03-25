package es.alvarorodriguez.pruebafcm.data.remote.home

import com.google.firebase.firestore.FirebaseFirestore
import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.data.model.Cars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class HomeDataSource {
    suspend fun getCars(): Result<List<Cars>> {
        val carsList = mutableListOf<Cars>()

        withContext(Dispatchers.IO) {
            val query = FirebaseFirestore.getInstance().collection("cars").get().await()

            for (car in query.documents){
                car.toObject(Cars::class.java)?.let { fbCar ->
                    carsList.add(fbCar)
                }
            }
        }

        return Result.Success(carsList)
    }
}