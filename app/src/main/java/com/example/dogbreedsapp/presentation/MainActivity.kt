package com.example.dogbreedsapp.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.dogbreedsapp.presentation.ui.DogBreedsApp
import com.example.dogbreedsapp.presentation.ui.theme.DogBreedsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DogBreedsAppTheme {
                DogBreedsApp()
            }
        }
    }
}