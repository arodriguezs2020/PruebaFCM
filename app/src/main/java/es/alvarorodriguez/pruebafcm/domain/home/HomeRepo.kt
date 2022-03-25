package es.alvarorodriguez.pruebafcm.domain.home

import es.alvarorodriguez.pruebafcm.core.Result
import es.alvarorodriguez.pruebafcm.data.model.Cars

interface HomeRepo {
    suspend fun getCars(): Result<List<Cars>>
}