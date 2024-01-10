package com.example.dogbreedsapp.presentation.ui.screens.allBreedsScreen

sealed interface AllBreedsIntention {

    data class FetchDataByQuery(val searchQuery: String) : AllBreedsIntention

    data class FetchDataByOrder(val straightOrder: Boolean) : AllBreedsIntention
}