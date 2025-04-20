package com.sumit.movieapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumit.movieapp.data.repository.MovieRepository
import com.sumit.movieapp.data.repository.MovieResult
import com.sumit.movieapp.domain.Movie
import kotlinx.coroutines.launch

class MovieViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    var state = mutableStateOf(MovieState())
        private set

    init {
        loadTrendingMovies()
    }

    fun loadTrendingMovies() {
        viewModelScope.launch {
            repository.getTrendingMovies().collect { result ->
                state.value = when (result) {
                    is MovieResult.Loading -> state.value.copy(isLoading = true)
                    is MovieResult.Success -> state.value.copy(
                        isLoading = false,
                        movies = result.data,
                        error = null
                    )

                    is MovieResult.Error -> state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }

    fun search(query: String) {
        viewModelScope.launch {
            repository.searchMovies(query).collect { result ->
                state.value = when (result) {
                    is MovieResult.Loading -> state.value.copy(isLoading = true)
                    is MovieResult.Success -> state.value.copy(
                        isLoading = false,
                        movies = result.data,
                        error = null
                    )

                    is MovieResult.Error -> state.value.copy(
                        isLoading = false,
                        error = result.message
                    )
                }
            }
        }
    }
}

data class MovieState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String? = null
)