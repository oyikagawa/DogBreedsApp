package com.example.dogbreedsapp.presentation.ui.screens.favoriteBreedsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsapp.domain.models.BreedData
import com.example.dogbreedsapp.domain.usecase.FetchAllFavoriteBreedsImagesCase
import com.example.dogbreedsapp.presentation.ui.screens.findByQuery
import com.example.dogbreedsapp.presentation.ui.screens.sortByOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteBreedsViewModel @Inject constructor(
    private val fetchAllFavoriteBreedsImagesCase: FetchAllFavoriteBreedsImagesCase
) :
    ViewModel() {

    private var _breedsDataFlow = MutableStateFlow<FavoriteBreedsViewModelData?>(null)
    val breedsDataFlow = _breedsDataFlow.asStateFlow()

    private var _favoriteImagesData: List<BreedData>? = null
    private var _straightOrder: Boolean = true
    private var _searchQuery: String = ""

    fun send(intention: FavoriteBreedsIntention) {
        when (intention) {
            is FavoriteBreedsIntention.FetchAllFavoriteBreedsImages -> fetchAllFavoriteBreedsImages()
            is FavoriteBreedsIntention.FetchDataByQuery -> fetchBreedDataByQuery(intention.searchQuery)
            is FavoriteBreedsIntention.FetchDataByOrder -> fetchBreedDataByOrder(intention.straightOrder)
        }
    }

    private fun fetchAllFavoriteBreedsImages() = viewModelScope.launch {
        _breedsDataFlow.emit(
            FavoriteBreedsViewModelData(breedsFavoriteImagesData = fetchAllFavoriteBreedsImagesCase.execute())
        )
        _favoriteImagesData = _breedsDataFlow.value?.breedsFavoriteImagesData
    }

    private fun fetchBreedDataByQuery(searchQuery: String) = viewModelScope.launch {
        _breedsDataFlow.emit(
            FavoriteBreedsViewModelData(
                breedsFavoriteImagesData = _favoriteImagesData.sortByOrder(_straightOrder).findByQuery(searchQuery)
            )
        )
        _searchQuery = searchQuery
    }

    private fun fetchBreedDataByOrder(straightOrder: Boolean) = viewModelScope.launch {
        _breedsDataFlow.emit(
            FavoriteBreedsViewModelData(
                breedsFavoriteImagesData = _favoriteImagesData.sortByOrder(straightOrder).findByQuery(_searchQuery)
            )
        )
        _straightOrder = straightOrder
    }

}