package com.sumit.movieapp.domain

data class Movie(
    val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String
)