package com.sumit.movieapp.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class MovieResponse(
    val results: List<MovieDto>
)