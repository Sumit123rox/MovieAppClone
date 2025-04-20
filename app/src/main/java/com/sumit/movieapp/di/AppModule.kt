package com.sumit.movieapp.di

import android.app.Application
import androidx.room.Room
import com.sumit.movieapp.data.local.dao.MovieDao
import com.sumit.movieapp.data.local.database.MovieDatabase
import com.sumit.movieapp.data.remote.api.TmdbApi
import com.sumit.movieapp.data.repository.MovieRepository
import com.sumit.movieapp.data.repository.MovieRepositoryImpl
import com.sumit.movieapp.util.NetworkConnectivity
import com.sumit.movieapp.viewmodel.MovieDetailViewModel
import com.sumit.movieapp.viewmodel.MovieViewModel
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single { NetworkConnectivity(androidContext()) }
    single { createHttpClient() }
    single { TmdbApi(get()) }
    single<MovieRepository> { MovieRepositoryImpl(get(), get(), get()) }
    single<MovieDatabase> { createRoomDatabase(androidApplication()) }
    single<MovieDao> { get<MovieDatabase>().movieDao() }
    viewModelOf(::MovieViewModel)
    viewModelOf(::MovieDetailViewModel)
}

fun createRoomDatabase(androidApplication: Application): MovieDatabase = Room.databaseBuilder(
    androidApplication,
    MovieDatabase::class.java,
    "movie_database"
).build()

fun createHttpClient(): HttpClient = HttpClient(Android) {
    install(Logging) {
        level = LogLevel.ALL
        logger = object : Logger {
            override fun log(message: String) {
                println("API_CALL: $message")
            }
        }
    }
    install(HttpTimeout) {
        connectTimeoutMillis = 15000
        requestTimeoutMillis = 30000
    }
    install(ContentNegotiation) {
        json(Json {
            ignoreUnknownKeys = true
            prettyPrint = true
            isLenient = true
        })
    }
}
