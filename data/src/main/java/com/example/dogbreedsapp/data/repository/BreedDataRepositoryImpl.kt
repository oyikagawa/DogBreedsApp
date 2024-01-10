package com.example.dogbreedsapp.data.repository

import com.example.dogbreedsapp.data.models.BreedTypeDataModel
import com.example.dogbreedsapp.data.models.ChangeableBreedDataModel
import com.example.dogbreedsapp.domain.models.BreedData
import com.example.dogbreedsapp.domain.models.BreedType
import com.example.dogbreedsapp.domain.models.ChangeableBreedData
import com.example.dogbreedsapp.domain.repository.BreedDataRepository

class BreedDataRepositoryImpl(
    private val dataProvider: DataProvider?,
    private val dataUpdater: DataUpdater?
) : BreedDataRepository {

    override suspend fun fetchAllData(): List<BreedData>? {
        val temp = dataProvider?.fetchAllData() ?: return null
        val breedsData = arrayListOf<BreedData>()

        temp.forEach {
            breedsData.add(
                BreedData(
                    breedType = BreedType(
                        breedName = it.breedTypeDataModel.breedName,
                        subBreedName = it.breedTypeDataModel.subBreedName
                    ), breedImageSources = it.imageSources
                )
            )
        }

        return breedsData
    }

    override suspend fun fetchDataByType(breedType: BreedType): BreedData? {
        val temp = dataProvider?.fetchDataByType(
            BreedTypeDataModel(
                breedName = breedType.breedName,
                subBreedName = breedType.subBreedName
            )
        ) ?: return null

        return BreedData(
            breedType = BreedType(
                breedName = temp.breedTypeDataModel.breedName,
                subBreedName = temp.breedTypeDataModel.subBreedName
            ),
            breedImageSources = temp.imageSources
        )
    }

    override suspend fun insertData(changeableBreedData: ChangeableBreedData) {
        dataUpdater?.insertData(
            ChangeableBreedDataModel(
                breedTypeDataModel = BreedTypeDataModel(
                    breedName = changeableBreedData.breedType.breedName,
                    subBreedName = changeableBreedData.breedType.subBreedName
                ),
                changeableImageSource = changeableBreedData.changeableImageSource
            )
        )
    }

    override suspend fun deleteData(changeableBreedData: ChangeableBreedData) {
        dataUpdater?.deleteData(
            ChangeableBreedDataModel(
                breedTypeDataModel = BreedTypeDataModel(
                    breedName = changeableBreedData.breedType.breedName,
                    subBreedName = changeableBreedData.breedType.subBreedName
                ),
                changeableImageSource = changeableBreedData.changeableImageSource
            )
        )
    }
}