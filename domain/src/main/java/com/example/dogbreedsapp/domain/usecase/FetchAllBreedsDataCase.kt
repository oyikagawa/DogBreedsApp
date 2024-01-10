package com.example.dogbreedsapp.domain.usecase

import com.example.dogbreedsapp.domain.repository.BreedDataRepository

class FetchAllBreedsDataCase(private val breedDataRepository: BreedDataRepository) {

    suspend fun execute() = breedDataRepository.fetchAllData()
}