package com.example.dogbreedsapp.presentation.di

import com.example.dogbreedsapp.domain.repository.BreedDataRepository
import com.example.dogbreedsapp.domain.usecase.DeleteBreedImageFromFavoriteCase
import com.example.dogbreedsapp.domain.usecase.FetchAllBreedsDataCase
import com.example.dogbreedsapp.domain.usecase.FetchAllFavoriteBreedsImagesCase
import com.example.dogbreedsapp.domain.usecase.FetchAllFavoriteImagesByTypeCase
import com.example.dogbreedsapp.domain.usecase.FetchBreedDataByTypeCase
import com.example.dogbreedsapp.domain.usecase.InsertFavoriteBreedImageCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun provideFetchAllBreedsDataCase(@ApiBreedDataRepository breedDataRepository: BreedDataRepository) =
        FetchAllBreedsDataCase(breedDataRepository)

    @Provides
    fun provideFetchBreedDataByTypeCase(@ApiBreedDataRepository breedDataRepository: BreedDataRepository) =
        FetchBreedDataByTypeCase(breedDataRepository)

    @Provides
    fun provideFetchAllFavoriteBreedsImagesCase(@DatabaseBreedDataRepository breedDataRepository: BreedDataRepository) =
        FetchAllFavoriteBreedsImagesCase(breedDataRepository)

    @Provides
    fun provideFetchAllFavoriteImagesForTypeCase(@DatabaseBreedDataRepository breedDataRepository: BreedDataRepository) =
        FetchAllFavoriteImagesByTypeCase(breedDataRepository)

    @Provides
    fun provideInsertFavoriteBreedImageCase(@DatabaseBreedDataRepository breedDataRepository: BreedDataRepository) =
        InsertFavoriteBreedImageCase(breedDataRepository)

    @Provides
    fun provideDeleteBreedImageFromFavoriteCase(@DatabaseBreedDataRepository breedDataRepository: BreedDataRepository) =
        DeleteBreedImageFromFavoriteCase(breedDataRepository)
}