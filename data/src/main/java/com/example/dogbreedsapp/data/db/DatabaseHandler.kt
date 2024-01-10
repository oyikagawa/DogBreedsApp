package com.example.dogbreedsapp.data.db

import android.content.Context
import android.database.sqlite.SQLiteException
import android.util.Log
import androidx.room.Room
import com.example.dogbreedsapp.data.models.BreedDataModel
import com.example.dogbreedsapp.data.models.BreedTypeDataModel
import com.example.dogbreedsapp.data.models.ChangeableBreedDataModel
import com.example.dogbreedsapp.data.repository.DataProvider
import com.example.dogbreedsapp.data.repository.DataUpdater
import com.example.dogbreedsapp.data.db.entities.FavoriteBreedImageEntity
import com.example.dogbreedsapp.data.db.entities.databaseName

class DatabaseHandler(context: Context) : DataProvider, DataUpdater {

    private val database = Room.databaseBuilder(
        context = context.applicationContext,
        klass = FavoriteBreedsImagesDatabase::class.java,
        name = databaseName
    ).build()
    private val databaseDao = database.fetchFavoriteBreedsImagesDatabaseDao()

    override suspend fun fetchAllData(): List<BreedDataModel>? {
        val temp = tryApplyDBQuery { databaseDao.fetchAllFavoriteBreedsImages() }
        if (temp.isNullOrEmpty()) return null
        val tempBreedDataModels = arrayListOf<BreedDataModel>()

        temp.forEach {
            val type = BreedTypeDataModel(it.breedName, it.subBreedName)
            val existingBDM =
                tempBreedDataModels.find { bdm: BreedDataModel -> bdm.breedTypeDataModel == type }
            if (existingBDM == null)
                tempBreedDataModels.add(
                    BreedDataModel(
                        breedTypeDataModel = type,
                        imageSources = arrayListOf(it.imageSource)
                    )
                )
            else
                existingBDM.imageSources?.add(it.imageSource)
        }

        return tempBreedDataModels
    }

    override suspend fun fetchDataByType(breedTypeDataModel: BreedTypeDataModel): BreedDataModel? {
        val temp = tryApplyDBQuery {
            databaseDao.fetchFavoriteBreedImagesByType(
                breedName = breedTypeDataModel.breedName,
                subBreedName = breedTypeDataModel.subBreedName
            )
        }
        if (temp.isNullOrEmpty()) return null
        val imagesList = arrayListOf<String>()

        temp.forEach {
            imagesList.add(it.imageSource)
        }

        return BreedDataModel(
            breedTypeDataModel = BreedTypeDataModel(
                breedName = temp[0].breedName,
                subBreedName = temp[0].subBreedName
            ),
            imageSources = imagesList
        )
    }

    override suspend fun insertData(changeableBreedDataModel: ChangeableBreedDataModel) {
        tryApplyDBQuery {
            databaseDao.insertFavoriteBreedImage(
                FavoriteBreedImageEntity(
                    id = 0,
                    breedName = changeableBreedDataModel.breedTypeDataModel.breedName,
                    subBreedName = changeableBreedDataModel.breedTypeDataModel.subBreedName,
                    imageSource = changeableBreedDataModel.changeableImageSource
                )
            )
        }
    }

    override suspend fun deleteData(changeableBreedDataModel: ChangeableBreedDataModel) {
        tryApplyDBQuery {
            databaseDao.deleteFavoriteBreedImage(
                breedName = changeableBreedDataModel.breedTypeDataModel.breedName,
                subBreedName = changeableBreedDataModel.breedTypeDataModel.subBreedName,
                imageSource = changeableBreedDataModel.changeableImageSource
            )
        }
    }

    private suspend fun <T> tryApplyDBQuery(dbQuery: suspend () -> T): T? {
        return try {
            dbQuery()
        } catch (e: SQLiteException) {
            Log.e("Database", e.toString())
            null
        }
    }
}