package com.example.dogbreedsapp.presentation.ui.screens

import com.example.dogbreedsapp.domain.models.BreedData

fun List<BreedData>?.findByQuery(searchQuery: String): List<BreedData>? =
    this?.filter {
        it.breedType.breedName.contains(
            other = searchQuery,
            ignoreCase = true
        ) || it.breedType.subBreedName.contains(
            other = searchQuery,
            ignoreCase = true
        )
    }

fun List<BreedData>?.sortByOrder(straightOrder: Boolean): List<BreedData>? =
    if (straightOrder) this?.sortedWith(
        compareBy(
            { it.breedType.breedName },
            { it.breedType.subBreedName })
    )
    else this?.sortedWith(
        compareByDescending<BreedData>
        { it.breedType.breedName }
            .thenByDescending { it.breedType.subBreedName }
    )