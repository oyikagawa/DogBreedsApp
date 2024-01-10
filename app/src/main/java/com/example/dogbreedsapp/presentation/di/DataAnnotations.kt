package com.example.dogbreedsapp.presentation.di

import javax.inject.Qualifier

@Qualifier
annotation class ApiDataProviderAnnotation

@Qualifier
annotation class DatabaseProviderAnnotation

@Qualifier
annotation class DatabaseUpdaterAnnotation

@Qualifier
annotation class ApiBreedDataRepository

@Qualifier
annotation class DatabaseBreedDataRepository