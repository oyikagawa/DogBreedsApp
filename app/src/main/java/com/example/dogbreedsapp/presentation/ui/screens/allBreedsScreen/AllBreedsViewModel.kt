package com.example.dogbreedsapp.presentation.ui.screens.allBreedsScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dogbreedsapp.domain.models.BreedData
import com.example.dogbreedsapp.domain.usecase.FetchAllBreedsDataCase
import com.example.dogbreedsapp.presentation.ui.screens.findByQuery
import com.example.dogbreedsapp.presentation.ui.screens.sortByOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllBreedsViewModel @Inject constructor(
    private val fetchAllBreedsDataCase: FetchAllBreedsDataCase
) : ViewModel() {

    private var _breedsDataFlow = MutableStateFlow<AllBreedsViewModelData?>(null)
    val breedsDataFlow = _breedsDataFlow.asStateFlow()

    private var _breedsData: List<BreedData>? = null
    private var _straightOrder: Boolean = true
    private var _searchQuery: String = ""

    init {
        fetchAllBreedsData()
    }

    fun send(intention: AllBreedsIntention) {
        when (intention) {
            is AllBreedsIntention.FetchDataByQuery -> fetchBreedDataByQuery(intention.searchQuery)
            is AllBreedsIntention.FetchDataByOrder -> fetchBreedDataByOrder(intention.straightOrder)
        }
    }

    private fun fetchAllBreedsData() = viewModelScope.launch {
        _breedsDataFlow.emit(
            AllBreedsViewModelData(
                breedsData = fetchAllBreedsDataCase.execute(),
                isFetchDataInProgress = false
            )
        )
        _breedsData = _breedsDataFlow.value?.breedsData
    }

    private fun fetchBreedDataByQuery(searchQuery: String) = viewModelScope.launch {
        _breedsDataFlow.emit(
            AllBreedsViewModelData(
                breedsData = _breedsData.sortByOrder(_straightOrder).findByQuery(searchQuery),
                isFetchDataInProgress = false
            )
        )
        _searchQuery = searchQuery
    }

    private fun fetchBreedDataByOrder(straightOrder: Boolean) = viewModelScope.launch {
        _breedsDataFlow.emit(
            AllBreedsViewModelData(
                breedsData = _breedsData.sortByOrder(straightOrder).findByQuery(_searchQuery),
                isFetchDataInProgress = false
            )
        )
        _straightOrder = straightOrder
    }
}
