package com.example.rickandmortytestapp.features.search.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.rickandmortytestapp.features.search.presentation.screen.DetailScreen
import com.example.rickandmortytestapp.features.search.presentation.screen.SearchScreen

@Composable
fun SearchNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Search,
    ) {
        composable<Search> {
            SearchScreen(navController)
        }
        composable<Detail> { backStackEntry ->
            val detail: Detail = backStackEntry.toRoute()
            DetailScreen(navController, detail.id)
        }
    }
}