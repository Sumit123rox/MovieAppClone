package com.sumit.movieapp.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.sumit.movieapp.R
import com.sumit.movieapp.domain.Movie
import com.sumit.movieapp.viewmodel.MovieDetailState
import com.sumit.movieapp.viewmodel.MovieDetailViewModel
import ir.kaaveh.sdpcompose.sdp
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController
) {
    val viewModel: MovieDetailViewModel = koinViewModel(
        parameters = { parametersOf(movieId) }
    )
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { Text("") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (state) {
                is MovieDetailState.Loading -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is MovieDetailState.Error -> Text((state as MovieDetailState.Error).message)
                is MovieDetailState.Success -> {
                    val movie = (state as MovieDetailState.Success).movie
                    MovieDetailContent(movie)
                }
            }
        }
    }
}

@Composable
private fun MovieDetailContent(movie: Movie) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.sdp, end = 16.sdp, bottom = 16.sdp, top = 8.sdp)
            .verticalScroll(rememberScrollState())
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500${movie.posterPath}",
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(300.sdp)
                .clip(RoundedCornerShape(8.sdp))
                .fillMaxWidth(),
            placeholder = painterResource(id = R.drawable.dummy_movie),
            error = painterResource(id = R.drawable.dummy_movie),
        )
        Spacer(modifier = Modifier.height(16.sdp))
        Text(movie.title, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.sdp))
        Text(movie.overview)
    }
}