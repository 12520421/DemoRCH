package com.atg.demorch.service.repository

import com.atg.demorch.model.Beer
import com.atg.demorch.model.Result
import com.atg.demorch.service.client.ApiService
import com.atg.demorch.utils.Utils

interface BeerRepository {
    suspend fun getBeers(
        brewedBefore: String?,
        brewedAfter: String?,
        pageSize: Int,
        pageNumber: Int
    ): Result<ArrayList<Beer?>>

    suspend fun getBeer(idBeer: Int): Result<List<Beer>>
}

class BeerRepositoryImpl(
    private val apiService: ApiService
) : BeerRepository {
    override suspend fun getBeers(
        brewedBefore: String?,
        brewedAfter: String?,
        pageSize: Int,
        pageNumber: Int
    ): Result<ArrayList<Beer?>> {
        val response =
            apiService.getAllBears(brewedBefore, brewedAfter, pageNumber, pageSize).execute()
        return Utils.toResponseResult(response)
    }

    override suspend fun getBeer(idBeer: Int): Result<List<Beer>> {
        val response =
            apiService.getBear(idBeer).execute()
        return Utils.toResponseResult(response)
    }

}