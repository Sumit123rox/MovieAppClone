package com.sumit.movieapp.data.repository

import com.sumit.movieapp.data.local.dao.MovieDao
import com.sumit.movieapp.data.remote.api.TmdbApi
import com.sumit.movieapp.util.NetworkConnectivity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val api: TmdbApi,
    private val dao: MovieDao,
    private val network: NetworkConnectivity
) : MovieRepository {

    override suspend fun getTrendingMovies(): Flow<MovieResult> = flow {
        emit(MovieResult.Loading)
        try {
            val movies = if (network.isConnected()) {
                val response = api.getTrendingMovies()
                val entities = response.results.map { it.toEntity() }
                dao.insertAll(entities)
                entities.map { it.toDomain() }
            } else {
                dao.getAllMovies().first().map { it.toDomain() }
            }
            emit(MovieResult.Success(movies))
        } catch (e: Exception) {
            emit(MovieResult.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun searchMovies(query: String): Flow<MovieResult> = flow {
        emit(MovieResult.Loading)
        try {
            val movies = if (network.isConnected()) {
                val response = api.searchMovies(query)
                val entities = response.results.map { it.toEntity() }
                dao.insertAll(entities)
                entities.map { it.toDomain() }
            } else {
                dao.searchMovies("%$query%").first().map { it.toDomain() }
            }
            emit(MovieResult.Success(movies))
        } catch (e: Exception) {
            emit(MovieResult.Error(e.message ?: "Unknown error"))
        }
    }

    override suspend fun getMovieById(id: Int): Flow<MovieResult> = flow {
        emit(MovieResult.Loading)
        try {
            val movie = if (network.isConnected()) {
                val dto = api.getMovieDetails(id)
                val entity = dto.toEntity()
                dao.insertAll(listOf(entity))
                entity.toDomain()
            } else {
                dao.getMovieById(id).first()?.toDomain()
            }
            emit(MovieResult.Success(listOf(movie ?: throw Exception())))
        } catch (e: Exception) {
            emit(MovieResult.Error(e.message ?: "Unknown error"))
        }
    }
}