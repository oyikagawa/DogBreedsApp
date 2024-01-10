package com.example.dogbreedsapp.data.repository

import com.example.dogbreedsapp.data.models.BreedDataModel
import com.example.dogbreedsapp.data.models.BreedTypeDataModel

interface DataProvider {

    suspend fun fetchAllData(): List<BreedDataModel>?

    suspend fun fetchDataByType(breedTypeDataModel: BreedTypeDataModel): BreedDataModel?
}