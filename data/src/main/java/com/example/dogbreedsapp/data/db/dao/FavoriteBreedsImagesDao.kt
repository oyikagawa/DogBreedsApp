package com.example.dogbreedsapp.data.db.dao

import android.database.sqlite.SQLiteException
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dogbreedsapp.data.db.entities.FavoriteBreedImageEntity
import kotlin.jvm.Throws

@Dao
interface FavoriteBreedsImagesDao {

    @Insert
    @Throws(SQLiteException::class)
    suspend fun insertFavoriteBreedImage(favoriteBreedImageEntity: FavoriteBreedImageEntity)

    @Query("SELECT * FROM favorite_breeds_images")
    @Throws(SQLiteException::class)
    suspend fun fetchAllFavoriteBreedsImages(): List<FavoriteBreedImageEntity>

    @Query("SELECT * FROM favorite_breeds_images WHERE breed_name = :breedName AND sub_breed_name = :subBreedName")
    @Throws(SQLiteException::class)
    suspend fun fetchFavoriteBreedImagesByType(
        breedName: String,
        subBreedName: String
    ): List<FavoriteBreedImageEntity>

    @Query("DELETE FROM favorite_breeds_images WHERE breed_name = :breedName AND sub_breed_name = :subBreedName AND image_source = :imageSource")
    @Throws(SQLiteException::class)
    suspend fun deleteFavoriteBreedImage(
        breedName: String,
        subBreedName: String,
        imageSource: String
    )

    @Query("DELETE FROM favorite_breeds_images")
    suspend fun deleteAll()
}