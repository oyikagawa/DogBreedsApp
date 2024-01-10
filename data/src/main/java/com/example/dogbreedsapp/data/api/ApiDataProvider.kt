package com.example.dogbreedsapp.data.api

import android.util.Log
import com.example.dogbreedsapp.data.models.BreedDataModel
import com.example.dogbreedsapp.data.models.BreedTypeDataModel
import com.example.dogbreedsapp.data.repository.DataProvider
import com.example.dogbreedsapp.data.api.apiQueries.ApiQueries
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val url = "https://dog.ceo/api/"

class ApiDataProvider : DataProvider {

    private val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val apiQueries = retrofit.create(ApiQueries::class.java)

    override suspend fun fetchAllData(): List<BreedDataModel>? {
        val temp = tryFetchResponse { apiQueries.fetchAllBreedsData() } ?: return null
        val breedDataModels = arrayListOf<BreedDataModel>()
        val emptySubBreed = arrayOf("")

        temp.message.forEach { (k, v) ->
            val vTemp = if (v.isEmpty()) emptySubBreed else v
            vTemp.forEach {
                breedDataModels.add(
                    BreedDataModel(
                        breedTypeDataModel = BreedTypeDataModel(
                            breedName = k,
                            subBreedName = it
                        ),
                        imageSources = null
                    )
                )
            }
        }
        return breedDataModels
    }

    override suspend fun fetchDataByType(breedTypeDataModel: BreedTypeDataModel): BreedDataModel? {
        var queryBreedType = breedTypeDataModel.breedName
        if (breedTypeDataModel.subBreedName.isNotEmpty())
            queryBreedType += "/${breedTypeDataModel.subBreedName}"

        val temp = tryFetchResponse { apiQueries.fetchBreedDataByType(queryBreedType) } ?: return null
        return BreedDataModel(
            breedTypeDataModel = breedTypeDataModel,
            imageSources = arrayListOf(temp.message)
        )
    }

    private suspend fun <T> tryFetchResponse(apiQuery: suspend () -> Response<T>): T? {
        fun sendErrorLog() = Log.e("Api", "Api Query Error!")

        return try {
            val response = apiQuery()
            if (!response.isSuccessful) {
                sendErrorLog()
                return null
            }
            return response.body()
        } catch (e: Exception) {
            sendErrorLog()
            null
        }
    }
}