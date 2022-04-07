package es.alvarorodriguez.pruebafcm.domain.add

import android.graphics.Bitmap

interface AddRepo {
    suspend fun addCar(marca: String, description: String, imgCar: Bitmap)
}