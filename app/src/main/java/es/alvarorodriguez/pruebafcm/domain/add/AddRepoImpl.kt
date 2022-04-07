package es.alvarorodriguez.pruebafcm.domain.add

import android.graphics.Bitmap
import es.alvarorodriguez.pruebafcm.data.remote.add.AddDataSource

class AddRepoImpl(private val dataSource: AddDataSource) : AddRepo {
    override suspend fun addCar(marca: String, description: String, imgCar: Bitmap) =
        dataSource.addCar(marca, description, imgCar)
}