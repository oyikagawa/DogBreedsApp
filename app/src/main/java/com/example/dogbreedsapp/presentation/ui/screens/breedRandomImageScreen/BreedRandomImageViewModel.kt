package com.example.dogbreedsapp.presentation.ui.screens.breedRandomImageScreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsapp.domain.models.BreedType
import com.example.dogbreedsapp.domain.models.ChangeableBreedData
import com.example.dogbreedsapp.domain.usecase.DeleteBreedImageFromFavoriteCase
import com.example.dogbreedsapp.domain.usecase.FetchAllFavoriteBreedsImagesCase
import com.example.dogbreedsapp.domain.usecase.FetchBreedDataByTypeCase
import com.example.dogbreedsapp.domain.usecase.InsertFavoriteBreedImageCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BreedRandomImageViewModel @Inject constructor(
    private val fetchBreedDataByTypeCase: FetchBreedDataByTypeCase,
    private val fetchAllFavoriteBreedsImagesCase: FetchAllFavoriteBreedsImagesCase,
    private val insertFavoriteBreedImageCase: InsertFavoriteBreedImageCase,
    private val deleteBreedImageFromFavoriteCase: DeleteBreedImageFromFavoriteCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private var _breedsDataFlow = MutableStateFlow<BreedRandomImageViewModelData?>(null)
    val breedsDataFlow = _breedsDataFlow.asStateFlow()

    init {
        val breedName: String? = savedStateHandle["breed"]
        val subBreedName: String? = savedStateHandle["subBreed"]
        if (breedName != null)
            fetchBreedDataByType(
                BreedType(
                    breedName = breedName,
                    subBreedName = subBreedName ?: ""
                )
            )
    }

    fun send(intention: BreedRandomImageIntention) {
        when (intention) {
            is BreedRandomImageIntention.FetchBreedDataByType -> fetchBreedDataByType(intention.breedType)
            is BreedRandomImageIntention.InsertFavoriteBreedImage -> insertFavoriteBreedImage(
                intention.changeableBreedData
            )
            is BreedRandomImageIntention.DeleteBreedImageFromFavorite -> deleteBreedImageFromFavorite(
                intention.changeableBreedData
            )
        }
    }

    private fun fetchBreedDataByType(breedType: BreedType) = viewModelScope.launch {
        _breedsDataFlow.emit(
            BreedRandomImageViewModelData(
                breedRandomImageData = fetchBreedDataByTypeCase.execute(breedType),
                breedsFavoriteImagesData = fetchAllFavoriteBreedsImagesCase.execute()
            )
        )
    }

    private fun insertFavoriteBreedImage(changeableBreedData: ChangeableBreedData) =
        viewModelScope.launch {
            insertFavoriteBreedImageCase.execute(changeableBreedData)
            emitViewModelBreedsImages()
        }

    private fun deleteBreedImageFromFavorite(changeableBreedData: ChangeableBreedData) =
        viewModelScope.launch {
            deleteBreedImageFromFavoriteCase.execute(changeableBreedData)
            emitViewModelBreedsImages()
        }

    private suspend fun emitViewModelBreedsImages() = _breedsDataFlow.emit(
        BreedRandomImageViewModelData(
            breedRandomImageData = _breedsDataFlow.value?.breedRandomImageData,
            breedsFavoriteImagesData = fetchAllFavoriteBreedsImagesCase.execute()
        )
    )
}