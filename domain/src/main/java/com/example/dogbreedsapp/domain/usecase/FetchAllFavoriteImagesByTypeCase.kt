package com.example.dogbreedsapp.domain.usecase

import com.example.dogbreedsapp.domain.models.BreedType
import com.example.dogbreedsapp.domain.repository.BreedDataRepository

class FetchAllFavoriteImagesByTypeCase(private val breedDataRepository: BreedDataRepository) {

    suspend fun execute(breedType: BreedType) =
        breedDataRepository.fetchDataByType(breedType)
}