package com.example.dogbreedsapp.domain.usecase

import com.example.dogbreedsapp.domain.models.ChangeableBreedData
import com.example.dogbreedsapp.domain.repository.BreedDataRepository

class InsertFavoriteBreedImageCase(private val breedDataRepository: BreedDataRepository) {

    suspend fun execute(changeableBreedData: ChangeableBreedData) =
        breedDataRepository.insertData(changeableBreedData)
}