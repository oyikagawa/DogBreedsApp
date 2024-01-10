package com.example.dogbreedsapp.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val databaseName = "favorite_breeds_images"

@Entity(tableName = databaseName)
data class FavoriteBreedImageEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    @ColumnInfo(name = "breed_name") val breedName: String,
    @ColumnInfo(name = "sub_breed_name") val subBreedName: String,
    @ColumnInfo(name = "image_source") val imageSource: String
)
