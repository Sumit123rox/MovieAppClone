package com.sumit.movieapp.data.repository

import com.sumit.movieapp.domain.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrendingMovies(): Flow<MovieResult>
    suspend fun searchMovies(query: String): Flow<MovieResult>
    suspend fun getMovieById(id: Int): Flow<MovieResult>
}

sealed class MovieResult {
    object Loading : MovieResult()
    data class Success(val data: List<Movie>) : MovieResult()
    data class Error(val message: String) : MovieResult()
}