package com.example.dogbreedsapp.data.repository

import com.example.dogbreedsapp.data.models.ChangeableBreedDataModel

interface DataUpdater {

    suspend fun insertData(changeableBreedDataModel: ChangeableBreedDataModel)

    suspend fun deleteData(changeableBreedDataModel: ChangeableBreedDataModel)
}