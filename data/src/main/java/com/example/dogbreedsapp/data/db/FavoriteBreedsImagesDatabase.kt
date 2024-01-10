package com.example.dogbreedsapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.dogbreedsapp.data.db.dao.FavoriteBreedsImagesDao
import com.example.dogbreedsapp.data.db.entities.FavoriteBreedImageEntity

@Database(
    version = 1,
    entities = [
        FavoriteBreedImageEntity::class
    ]
)
abstract class FavoriteBreedsImagesDatabase : RoomDatabase() {

    abstract fun fetchFavoriteBreedsImagesDatabaseDao() : FavoriteBreedsImagesDao
}