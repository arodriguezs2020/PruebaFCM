package es.alvarorodriguez.pruebafcm.domain.add

import es.alvarorodriguez.pruebafcm.data.remote.add.AddDataSource

class AddRepoImpl(private val dataSource: AddDataSource) : AddRepo {
    override suspend fun addCar(marca: String, description: String) =
        dataSource.addCar(marca, description)
}