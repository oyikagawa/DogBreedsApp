package com.example.dogbreedsapp.presentation.ui.screens.breedRandomImageScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dogbreedsapp.R
import com.example.dogbreedsapp.domain.models.BreedData
import com.example.dogbreedsapp.domain.models.ChangeableBreedData
import com.example.dogbreedsapp.presentation.ui.screens.EmptyDataLabel

@Composable
fun BreedRandomImageScreen(
    viewModel: BreedRandomImageViewModel = hiltViewModel()
) {
    val breedsFlow = viewModel.breedsDataFlow.collectAsState().value
    val chosenBreed = breedsFlow?.breedRandomImageData
    if (chosenBreed == null)
        EmptyDataLabel(labelText = stringResource(id = R.string.data_loading))
    else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 100.dp)
        ) {
            Box(modifier = Modifier.weight(4f)) {
                chosenBreed.breedImageSources?.forEach {
                    AsyncImage(
                        model = it,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
            Box(modifier = Modifier.weight(1f)) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        LikeImageBtn(
                            viewModel = viewModel,
                            breedsVMData = breedsFlow,
                            chosenBreed = chosenBreed
                        )
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        NextImageBtn(onBtnClick = {
                            viewModel.send(
                                BreedRandomImageIntention.FetchBreedDataByType(
                                    chosenBreed.breedType
                                )
                            )
                        })
                    }
                }
            }
        }
    }
}

@Composable
fun NextImageBtn(onBtnClick: () -> Unit) {
    IconButton(onClick = onBtnClick) {
        Icon(
            imageVector = Icons.Rounded.KeyboardArrowRight,
            contentDescription = "Get next breed image"
        )
    }
}

@Composable
fun LikeImageBtn(
    viewModel: BreedRandomImageViewModel,
    breedsVMData: BreedRandomImageViewModelData?,
    chosenBreed: BreedData
) {
    val favBreedsImages = breedsVMData?.breedsFavoriteImagesData
    val isLiked =
        !favBreedsImages.isNullOrEmpty()
                && !chosenBreed.breedImageSources.isNullOrEmpty()
                && !favBreedsImages.find {
            it.breedType == chosenBreed.breedType
        }?.breedImageSources?.find {
            it == chosenBreed.breedImageSources!![0]
        }.isNullOrEmpty()
    IconButton(onClick = {
        if (!chosenBreed.breedImageSources.isNullOrEmpty()) {
            val changeableBreedData = ChangeableBreedData(
                breedType = chosenBreed.breedType,
                changeableImageSource = chosenBreed.breedImageSources!![0]
            )
            if (!isLiked)
                viewModel.send(
                    BreedRandomImageIntention.InsertFavoriteBreedImage(
                        changeableBreedData
                    )
                )
            else
                viewModel.send(
                    BreedRandomImageIntention.DeleteBreedImageFromFavorite(
                        changeableBreedData
                    )
                )
        }
    }) {
        Icon(
            imageVector = if (isLiked) Icons.Rounded.Favorite else Icons.Rounded.FavoriteBorder,
            contentDescription = "Breed image like button"
        )
    }
}