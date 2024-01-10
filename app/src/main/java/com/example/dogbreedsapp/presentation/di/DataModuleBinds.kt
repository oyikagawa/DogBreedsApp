package com.example.dogbreedsapp.presentation.di

import com.example.dogbreedsapp.data.db.DatabaseHandler
import com.example.dogbreedsapp.data.repository.DataProvider
import com.example.dogbreedsapp.data.repository.DataUpdater
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModuleBinds {

    @DatabaseProviderAnnotation
    @Binds
    abstract fun bindDataProvider(databaseHandler: DatabaseHandler): DataProvider

    @DatabaseUpdaterAnnotation
    @Binds
    abstract fun bindDataUpdater(databaseHandler: DatabaseHandler): DataUpdater
}