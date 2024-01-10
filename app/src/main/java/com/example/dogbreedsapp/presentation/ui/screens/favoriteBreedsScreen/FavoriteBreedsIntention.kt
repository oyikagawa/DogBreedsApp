package com.example.dogbreedsapp.presentation.ui.screens.favoriteBreedsScreen

sealed interface FavoriteBreedsIntention {

    data object FetchAllFavoriteBreedsImages : FavoriteBreedsIntention

    data class FetchDataByQuery(val searchQuery: String) : FavoriteBreedsIntention

    data class FetchDataByOrder(val straightOrder: Boolean) : FavoriteBreedsIntention
}