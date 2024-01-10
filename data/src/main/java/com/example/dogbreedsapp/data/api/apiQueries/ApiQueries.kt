package com.example.dogbreedsapp.data.api.apiQueries

import com.example.dogbreedsapp.data.api.apiProviderData.ApiProviderAllBreedsData
import com.example.dogbreedsapp.data.api.apiProviderData.ApiProviderRandomImageData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiQueries {

    @GET("breeds/list/all")
    suspend fun fetchAllBreedsData(): Response<ApiProviderAllBreedsData>

    @GET("breed/{breedType}/images/random")
    suspend fun fetchBreedDataByType(
        @Path(
            "breedType",
            encoded = true
        ) breedType: String
    ): Response<ApiProviderRandomImageData>
}