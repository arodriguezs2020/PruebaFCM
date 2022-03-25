package es.alvarorodriguez.pruebafcm.domain.home

import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.data.model.Cars
import es.alvarorodriguez.pruebafcm.data.remote.home.HomeDataSource

class HomeRepoImpl(private val dataSource: HomeDataSource) : HomeRepo{
    override suspend fun getCars(): Result<List<Cars>> = dataSource.getCars()
}