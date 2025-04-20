package com.sumit.movieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.sumit.movieapp.presentation.screens.MovieDetailScreen
import com.sumit.movieapp.presentation.screens.MovieListScreen

@Composable
fun MovieNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "movies") {
        composable("movies") {
            MovieListScreen(navController = navController)
        }
        composable(
            route = "movie/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1
            MovieDetailScreen(movieId = movieId, navController = navController)
        }
    }
}