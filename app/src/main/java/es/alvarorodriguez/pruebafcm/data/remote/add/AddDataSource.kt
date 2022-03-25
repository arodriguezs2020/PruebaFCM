package es.alvarorodriguez.pruebafcm.data.remote.add

import com.google.firebase.firestore.FirebaseFirestore
import es.alvarorodriguez.pruebafcm.data.model.Cars
import kotlinx.coroutines.tasks.await

class AddDataSource {

    suspend fun addCar(marca: String, description: String) {
        FirebaseFirestore.getInstance().collection("cars").add(
            Cars(brand = marca, description = description)).await()
    }
}