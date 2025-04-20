package com.sumit.movieapp.data.remote.api

import com.sumit.movieapp.data.remote.dto.MovieDto
import com.sumit.movieapp.data.remote.dto.MovieResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.URLProtocol
import io.ktor.http.encodedPath

class TmdbApi(private val client: HttpClient) {
    companion object {
        private const val API_KEY = "API_KEY_HERE"
    }

    suspend fun getTrendingMovies(): MovieResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                encodedPath = "/3/trending/movie/week"
                parameters.append("api_key", API_KEY)
            }
        }.body()
    }

    suspend fun searchMovies(query: String): MovieResponse {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                encodedPath = "/3/search/movie"
                parameters.append("api_key", API_KEY)
                parameters.append("query", query)
            }
        }.body()
    }

    suspend fun getMovieDetails(id: Int): MovieDto {
        return client.get {
            url {
                protocol = URLProtocol.HTTPS
                host = "api.themoviedb.org"
                encodedPath = "/3/movie/$id"
                parameters.append("api_key", API_KEY)
            }
        }.body()
    }
}