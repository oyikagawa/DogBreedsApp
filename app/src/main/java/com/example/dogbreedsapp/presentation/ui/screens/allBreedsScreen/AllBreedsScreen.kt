package com.example.dogbreedsapp.presentation.ui.screens.allBreedsScreen

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dogbreedsapp.R
import com.example.dogbreedsapp.domain.models.BreedData
import com.example.dogbreedsapp.domain.models.BreedType
import com.example.dogbreedsapp.presentation.ui.screens.EmptyDataLabel
import com.example.dogbreedsapp.presentation.ui.screens.TopBarWContent
import com.example.dogbreedsapp.presentation.ui.screens.setupBreedLabel

@Composable
fun BreedsScreen(
    onBtnClick: (breedName: String, subBreedName: String) -> Unit,
    viewModel: AllBreedsViewModel = hiltViewModel()
) {
    val dataFlow = viewModel.breedsDataFlow.collectAsState().value
    if (dataFlow?.isFetchDataInProgress != false)
        EmptyDataLabel(labelText = stringResource(id = R.string.data_loading))
    else if (dataFlow.breedsData != null) {
        var sortVal by remember { mutableStateOf(true) }
        var searchVal by remember { mutableStateOf("") }

        TopBarWContent(
            sortValue = sortVal,
            searchValue = searchVal,
            onSortValueChanged = { sortV ->
                sortVal = sortV
                viewModel.send(AllBreedsIntention.FetchDataByOrder(sortV))
            },
            onSearchValueChanged = { searchV ->
                searchVal = searchV
                viewModel.send(AllBreedsIntention.FetchDataByQuery(searchV))
            }
        ) { topBarHeight ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = topBarHeight),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(count = dataFlow.breedsData.size, key = { it }) {
                    BreedBtn(
                        breedData = dataFlow.breedsData[it],
                        onBtnClick = { breedType: BreedType ->
                            onBtnClick.invoke(breedType.breedName, breedType.subBreedName)
                        })
                }
            }

        }
    }
}

@Composable
fun BreedBtn(breedData: BreedData, onBtnClick: (BreedType) -> Unit) {
    Button(
        onClick = { onBtnClick(breedData.breedType) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(
            text = setupBreedLabel(breedData),
            fontSize = 20.sp
        )
    }
}