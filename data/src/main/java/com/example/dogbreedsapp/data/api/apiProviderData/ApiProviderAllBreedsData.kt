package com.example.dogbreedsapp.data.api.apiProviderData

import kotlinx.serialization.Serializable

@Serializable
data class ApiProviderAllBreedsData(
    val message: Map<String, Array<String>>
)
