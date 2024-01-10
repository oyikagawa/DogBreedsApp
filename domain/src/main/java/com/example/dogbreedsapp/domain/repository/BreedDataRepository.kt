package com.example.dogbreedsapp.domain.repository

import com.example.dogbreedsapp.domain.models.BreedData
import com.example.dogbreedsapp.domain.models.BreedType
import com.example.dogbreedsapp.domain.models.ChangeableBreedData

interface BreedDataRepository {

    suspend fun fetchAllData(): List<BreedData>?

    suspend fun fetchDataByType(breedType: BreedType): BreedData?

    suspend fun insertData(changeableBreedData: ChangeableBreedData)

    suspend fun deleteData(changeableBreedData: ChangeableBreedData)
}