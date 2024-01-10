package com.example.dogbreedsapp.presentation.ui.screens.breedAllFavoriteImagesScreen

import com.example.dogbreedsapp.domain.models.ChangeableBreedData

sealed interface BreedAllFavoriteImagesIntention {

    data class DeleteBreedImageFromFavorite(val changeableBreedData: ChangeableBreedData) :
        BreedAllFavoriteImagesIntention
}