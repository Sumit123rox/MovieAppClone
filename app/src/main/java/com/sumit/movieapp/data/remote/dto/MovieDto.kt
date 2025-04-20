package com.sumit.movieapp.data.remote.dto

import com.sumit.movieapp.data.local.database.MovieEntity
import kotlinx.serialization.Serializable

@Serializable
data class MovieDto(
    val id: Int,
    val title: String,
    val poster_path: String?,
    val overview: String
) {
    fun toEntity(): MovieEntity = MovieEntity(
        id = id,
        title = title,
        posterPath = poster_path,
        overview = overview
    )
}