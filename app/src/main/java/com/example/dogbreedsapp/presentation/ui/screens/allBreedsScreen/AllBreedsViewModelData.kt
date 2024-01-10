package com.example.dogbreedsapp.presentation.ui.screens.allBreedsScreen

import com.example.dogbreedsapp.domain.models.BreedData

data class AllBreedsViewModelData(
    val breedsData: List<BreedData>?,
    val isFetchDataInProgress: Boolean = true
)