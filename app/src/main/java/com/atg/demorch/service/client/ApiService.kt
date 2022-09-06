package com.atg.demorch.service.client

import com.atg.demorch.model.Beer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("beers?")
    fun getAllBears(
        @Query("brewed_before") brewedAfter: String?,
        @Query("brewed_after") brewedBefore: String?,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Call<ArrayList<Beer?>>

    @GET("beers/{id}")
    fun getBear(
        @Path("id") id: Int,
    ): Call<List<Beer>>
}