package com.example.dogbreedsapp.presentation.ui.screens.breedAllFavoriteImagesScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dogbreedsapp.R
import com.example.dogbreedsapp.domain.models.ChangeableBreedData
import com.example.dogbreedsapp.presentation.ui.screens.EmptyDataLabel

@Composable
fun BreedAllFavoriteImagesScreen(
    onBreedImagesEmptyDialogClick: () -> Unit,
    viewModel: BreedAllFavoriteImagesViewModel = hiltViewModel()
) {
    val dataFlow = viewModel.breedsDataFlow.collectAsState().value
    if (dataFlow?.isFetchDataInProgress != false)
        EmptyDataLabel(labelText = stringResource(id = R.string.data_loading))
    else if (dataFlow.breedFavoriteImagesData?.breedImageSources.isNullOrEmpty())
        AlertImagesEmptyDialog(onBreedImagesEmptyDialogClick)
    else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(vertical = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val imgSources = dataFlow.breedFavoriteImagesData?.breedImageSources ?: return@LazyColumn
            items(count = imgSources.size, key = { it }) {
                AsyncImage(
                    model = imgSources[it],
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize()
                )
                Button(onClick = {
                    viewModel.send(
                        BreedAllFavoriteImagesIntention.DeleteBreedImageFromFavorite(
                            ChangeableBreedData(
                                breedType = dataFlow.breedFavoriteImagesData.breedType,
                                changeableImageSource = imgSources[it]
                            )
                        )
                    )
                }) {
                    Text(text = stringResource(id = R.string.delete))
                }
            }
        }
    }
}

@Composable
fun AlertImagesEmptyDialog(onConfirmBtnClick: () -> Unit) {
    AlertDialog(
        title = {
            Text(text = "Images are ended")
        },
        text = {
            Text(text = "You've deleted all images for that breed! Now you will be navigated to the previous screen.")
        },
        onDismissRequest = {},
        confirmButton = {
            TextButton(onClick = onConfirmBtnClick) {
                Text(text = stringResource(id = R.string.confirm))
            }
        })
}