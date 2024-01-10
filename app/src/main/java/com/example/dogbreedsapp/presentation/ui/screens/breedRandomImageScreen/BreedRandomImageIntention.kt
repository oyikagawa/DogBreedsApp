package com.example.dogbreedsapp.presentation.ui.screens.breedRandomImageScreen

import com.example.dogbreedsapp.domain.models.BreedType
import com.example.dogbreedsapp.domain.models.ChangeableBreedData

sealed interface BreedRandomImageIntention {

    data class FetchBreedDataByType(val breedType: BreedType) : BreedRandomImageIntention

    data class InsertFavoriteBreedImage(val changeableBreedData: ChangeableBreedData) :
        BreedRandomImageIntention

    data class DeleteBreedImageFromFavorite(val changeableBreedData: ChangeableBreedData) :
        BreedRandomImageIntention
}