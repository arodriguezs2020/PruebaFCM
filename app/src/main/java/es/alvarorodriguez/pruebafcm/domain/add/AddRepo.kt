package es.alvarorodriguez.pruebafcm.domain.add

interface AddRepo {
    suspend fun addCar(marca: String, description: String)
}