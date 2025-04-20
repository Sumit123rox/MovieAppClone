package com.sumit.movieapp.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sumit.movieapp.domain.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String?,
    val overview: String
) {
    fun toDomain(): Movie = Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview
    )
}