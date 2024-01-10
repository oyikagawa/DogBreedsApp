package com.example.dogbreedsapp.presentation.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.dogbreedsapp.presentation.ui.screens.allBreedsScreen.BreedsScreen
import com.example.dogbreedsapp.presentation.ui.screens.breedAllFavoriteImagesScreen.BreedAllFavoriteImagesScreen
import com.example.dogbreedsapp.presentation.ui.screens.breedRandomImageScreen.BreedRandomImageScreen
import com.example.dogbreedsapp.presentation.ui.screens.favoriteBreedsScreen.FavoriteBreedsScreen

private enum class DogBreedsAppScreens {
    Main, Favorites, BreedRandomImage, BreedAllFavoriteImages
}

@Composable
fun DogBreedsApp(
    navController: NavHostController = rememberNavController()
) {
    Scaffold(
        bottomBar = {
            BottomAppBar {
                BottomBar(
                    onMainScreenBtnClick = {
                        navController.navigate(DogBreedsAppScreens.Main.name) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    },
                    onFavoriteScreenBtnClick = {
                        navController.navigate(DogBreedsAppScreens.Favorites.name) {
                            popUpTo(route = DogBreedsAppScreens.Favorites.name) {
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    })
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = DogBreedsAppScreens.Main.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = DogBreedsAppScreens.Main.name) {
                BreedsScreen(
                    onBtnClick = { breedName: String, subBreedName: String ->
                        navController.navigate(
                            "${DogBreedsAppScreens.BreedRandomImage.name}/${breedName}?subBreed=${subBreedName}"
                        )
                    })
            }
            composable(route = DogBreedsAppScreens.Favorites.name) {
                FavoriteBreedsScreen(
                    onBreedCardClick = { breedName: String, subBreedName: String ->
                        navController.navigate(
                            "${DogBreedsAppScreens.BreedAllFavoriteImages.name}/${breedName}?subBreed=${subBreedName}"
                        )
                    })
            }
            composable(
                route = "${DogBreedsAppScreens.BreedRandomImage.name}/{breed}?subBreed={subBreed}",
                arguments = listOf(
                    navArgument("breed") { type = NavType.StringType },
                    navArgument("subBreed") { type = NavType.StringType },
                )
            ) {
                BreedRandomImageScreen()
            }
            composable(
                route = "${DogBreedsAppScreens.BreedAllFavoriteImages.name}/{breed}?subBreed={subBreed}",
                arguments = listOf(
                    navArgument("breed") { type = NavType.StringType },
                    navArgument("subBreed") { type = NavType.StringType },
                )
            ) {
                BreedAllFavoriteImagesScreen(onBreedImagesEmptyDialogClick = { navController.popBackStack() })
            }
        }
    }
}

@Composable
fun BottomBar(onMainScreenBtnClick: () -> Unit, onFavoriteScreenBtnClick: () -> Unit) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            IconButton(onClick = onMainScreenBtnClick) {
                Icon(imageVector = Icons.Rounded.Home, contentDescription = "Main screen button")
            }
        }
        Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
            IconButton(onClick = onFavoriteScreenBtnClick) {
                Icon(
                    imageVector = Icons.Rounded.Favorite,
                    contentDescription = "Favorites breeds screen button"
                )
            }
        }
    }
}

@Composable
fun TrailingIcon(showIcon: Boolean) {
    if (showIcon) Icon(
        imageVector = Icons.Rounded.Check,
        contentDescription = "Chosen Dropdown Item"
    )
}