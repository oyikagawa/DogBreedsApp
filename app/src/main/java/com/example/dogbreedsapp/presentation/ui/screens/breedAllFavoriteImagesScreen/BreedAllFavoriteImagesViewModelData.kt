package com.example.dogbreedsapp.presentation.ui.screens.breedAllFavoriteImagesScreen

import com.example.dogbreedsapp.domain.models.BreedData

data class BreedAllFavoriteImagesViewModelData(
    val breedFavoriteImagesData: BreedData?,
    val isFetchDataInProgress: Boolean = true
)