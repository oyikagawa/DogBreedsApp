package com.example.dogbreedsapp.domain.models

data class BreedData(
    val breedType: BreedType,
    val breedImageSources: List<String>?
)