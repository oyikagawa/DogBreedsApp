package com.example.dogbreedsapp.presentation.di

import android.content.Context
import com.example.dogbreedsapp.data.api.ApiDataProvider
import com.example.dogbreedsapp.data.db.DatabaseHandler
import com.example.dogbreedsapp.data.repository.BreedDataRepositoryImpl
import com.example.dogbreedsapp.data.repository.DataProvider
import com.example.dogbreedsapp.data.repository.DataUpdater
import com.example.dogbreedsapp.domain.repository.BreedDataRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @ApiBreedDataRepository
    @Provides
    @Singleton
    fun provideApiBreedDataRepository(
        @ApiDataProviderAnnotation dataProvider: DataProvider
    ): BreedDataRepository =
        BreedDataRepositoryImpl(dataProvider, null)

    @DatabaseBreedDataRepository
    @Provides
    @Singleton
    fun provideDatabaseBreedDataRepository(
        @DatabaseProviderAnnotation dataProvider: DataProvider,
        @DatabaseUpdaterAnnotation dataUpdater: DataUpdater?
    ): BreedDataRepository =
        BreedDataRepositoryImpl(dataProvider, dataUpdater)

    @ApiDataProviderAnnotation
    @Provides
    @Singleton
    fun provideApiDataProvider(): DataProvider =
        ApiDataProvider()

    @Provides
    @Singleton
    fun provideDatabaseProvider(@ApplicationContext context: Context) =
        DatabaseHandler(context)
}