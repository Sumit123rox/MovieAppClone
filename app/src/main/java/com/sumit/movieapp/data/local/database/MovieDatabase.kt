package com.sumit.movieapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sumit.movieapp.data.local.dao.MovieDao

@Database(entities = [MovieEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
}