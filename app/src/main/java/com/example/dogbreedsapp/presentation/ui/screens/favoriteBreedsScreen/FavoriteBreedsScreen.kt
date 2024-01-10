package com.example.dogbreedsapp.presentation.ui.screens.favoriteBreedsScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.dogbreedsapp.R
import com.example.dogbreedsapp.presentation.ui.screens.EmptyDataLabel
import com.example.dogbreedsapp.presentation.ui.screens.TopBarWContent
import com.example.dogbreedsapp.presentation.ui.screens.setupBreedLabel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteBreedsScreen(
    onBreedCardClick: (breedName: String, subBreedName: String) -> Unit,
    viewModel: FavoriteBreedsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.send(FavoriteBreedsIntention.FetchAllFavoriteBreedsImages)
    }

    val favoriteBreedsImages =
        viewModel.breedsDataFlow.collectAsState().value?.breedsFavoriteImagesData
    if (favoriteBreedsImages == null) EmptyDataLabel(labelText = stringResource(id = R.string.no_data))
    else {
        var sortVal by remember { mutableStateOf(true) }
        var searchVal by remember { mutableStateOf("") }

        TopBarWContent(
            sortValue = sortVal,
            searchValue = searchVal,
            onSortValueChanged = { sortV ->
                sortVal = sortV
                viewModel.send(FavoriteBreedsIntention.FetchDataByOrder(sortV))
            },
            onSearchValueChanged = { searchV ->
                searchVal = searchV
                viewModel.send(FavoriteBreedsIntention.FetchDataByQuery(searchV))
            }
        ) { topBarHeight ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = topBarHeight)
                    .padding(vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(count = favoriteBreedsImages.size, key = { it }) {
                    val bd = favoriteBreedsImages[it]
                    ElevatedCard(
                        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
                        modifier = Modifier.fillMaxWidth()
                            .height(300.dp)
                            .padding(horizontal = 16.dp)
                        ,
                        onClick = {
                            onBreedCardClick.invoke(
                                bd.breedType.breedName,
                                bd.breedType.subBreedName
                            )
                        }
                    ) {
                        Text(
                            text = setupBreedLabel(bd),
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(MaterialTheme.colorScheme.primary)
                                .padding(vertical = 10.dp),
                            fontSize = 25.sp,
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        AsyncImage(
                            model = bd.breedImageSources!![bd.breedImageSources!!.size - 1],
                            contentDescription = null,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}