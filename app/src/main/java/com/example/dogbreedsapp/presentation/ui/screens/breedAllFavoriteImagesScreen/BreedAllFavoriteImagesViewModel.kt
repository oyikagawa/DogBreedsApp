package com.example.dogbreedsapp.presentation.ui.screens.breedAllFavoriteImagesScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsapp.domain.models.BreedType
import com.example.dogbreedsapp.domain.models.ChangeableBreedData
import com.example.dogbreedsapp.domain.usecase.DeleteBreedImageFromFavoriteCase
import com.example.dogbreedsapp.domain.usecase.FetchAllFavoriteImagesByTypeCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedAllFavoriteImagesViewModel @Inject constructor(
    private val fetchAllFavoriteImagesByTypeCase: FetchAllFavoriteImagesByTypeCase,
    private val deleteBreedImageFromFavoriteCase: DeleteBreedImageFromFavoriteCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _breedsDataFlow = MutableStateFlow<BreedAllFavoriteImagesViewModelData?>(null)
    val breedsDataFlow = _breedsDataFlow.asStateFlow()

    init {
        val breedName: String? = savedStateHandle["breed"]
        val subBreedName: String? = savedStateHandle["subBreed"]
        if (breedName != null)
            fetchAllFavoriteImagesByType(
                BreedType(
                    breedName = breedName,
                    subBreedName = subBreedName ?: ""
                )
            )
    }

    fun send(intention: BreedAllFavoriteImagesIntention) {
        when (intention) {
            is BreedAllFavoriteImagesIntention.DeleteBreedImageFromFavorite -> deleteBreedImageFromFavorite(
                intention.changeableBreedData
            )
        }
    }

    private fun fetchAllFavoriteImagesByType(breedType: BreedType) = viewModelScope.launch {
        emitViewModelData(breedType)
    }

    private fun deleteBreedImageFromFavorite(changeableBreedData: ChangeableBreedData) =
        viewModelScope.launch {
            deleteBreedImageFromFavoriteCase.execute(changeableBreedData)
            emitViewModelData(changeableBreedData.breedType)
        }

    private suspend fun emitViewModelData(breedType: BreedType) = _breedsDataFlow.emit(
        BreedAllFavoriteImagesViewModelData(
            breedFavoriteImagesData = fetchAllFavoriteImagesByTypeCase.execute(
                breedType
            ),
            isFetchDataInProgress = false
        )
    )
}