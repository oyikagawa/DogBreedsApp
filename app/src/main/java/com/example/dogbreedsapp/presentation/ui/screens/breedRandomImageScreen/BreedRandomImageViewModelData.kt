package com.example.dogbreedsapp.presentation.ui.screens.breedRandomImageScreen

import com.example.dogbreedsapp.domain.models.BreedData

data class BreedRandomImageViewModelData(
    val breedRandomImageData: BreedData?,
    val breedsFavoriteImagesData: List<BreedData>?
)