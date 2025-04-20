package com.sumit.movieapp.presentation.screens

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.sumit.movieapp.presentation.components.MovieItem
import com.sumit.movieapp.presentation.components.SearchBar
import com.sumit.movieapp.viewmodel.MovieViewModel
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieListScreen(
    navController: NavController,
    viewModel: MovieViewModel = koinViewModel()
) {

    val state = viewModel.state.value
    val configuration = LocalConfiguration.current
    val columns = if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) 4 else 2
    var isSearchActive by remember { mutableStateOf(false) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.sdp),
                onSearch = { query ->
                    isSearchActive = query.isNotEmpty()
                    viewModel.search(query)
                },
                onClear = {
                    isSearchActive = false
                    viewModel.loadTrendingMovies()
                }
            )

            when {
                state.isLoading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                state.error != null -> Text(state.error)
                state.movies.isEmpty() -> Text("No movies found")
                else -> {
                    LazyVerticalStaggeredGrid(columns = StaggeredGridCells.Fixed(columns)) {
                        items(state.movies) { movie ->
                            MovieItem(movie) {
                                navController.navigate("movie/${movie.id}")
                            }
                        }
                    }
                }
            }
        }
    }
}
