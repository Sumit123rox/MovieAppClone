package com.sumit.movieapp.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sumit.movieapp.data.repository.MovieRepository
import com.sumit.movieapp.data.repository.MovieResult
import com.sumit.movieapp.domain.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieDetailState>(MovieDetailState.Loading)
    val uiState: StateFlow<MovieDetailState> = _uiState.asStateFlow()

    private val movieId: Int = savedStateHandle["movieId"]
        ?: throw IllegalArgumentException("Missing movieId")

    init {
        viewModelScope.launch {
            repository.getMovieById(movieId).collect { result ->
                _uiState.value = when (result) {
                    is MovieResult.Success -> MovieDetailState.Success(result.data.first())
                    is MovieResult.Error -> MovieDetailState.Error(result.message)
                    else -> MovieDetailState.Loading
                }
            }
        }
    }
}

sealed interface MovieDetailState {
    object Loading : MovieDetailState
    data class Success(val movie: Movie) : MovieDetailState
    data class Error(val message: String) : MovieDetailState
}
