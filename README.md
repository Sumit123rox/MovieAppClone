# MovieAppClone

---

# 🎬 Movie App (Jetpack Compose + Koin + Ktor + Room)

A modern Android app built with **Jetpack Compose**, **Koin**, **Ktor**, and **Room**. It fetches trending movies from [TMDB API](https://www.themoviedb.org/), allows searching, and displays detailed movie information.

---

## ✨ Features

- **Trending Movies**: Display trending movies fetched from TMDB.
- **Search Movies**: Search for movies by title.
- **Detailed View**: Tap on a movie to view its poster, title, and overview.
- **Offline Support**: Cache movies locally using **Room** for offline access.
- **Error Handling**: Handle network errors and empty states gracefully.

---

## 🛠️ Tech Stack

- **Jetpack Compose**: Declarative UI framework.
- **Koin**: Dependency injection.
- **Ktor**: Networking library for API calls.
- **Room**: Local database for caching.
- **Coroutines & Flow**: Asynchronous programming and reactive data streams.
- **TMDB API**: External API for movie data.

---

## 🚀 Setup Instructions

### 1. **Clone the Repository**

```bash
git remote add origin https://github.com/Sumit123rox/MovieAppClone.git
cd MovieAppClone
```

### 2. **Set Up TMDB API Key**

- Create an account at [TMDB](https://www.themoviedb.org/) and generate an API key.
- Replace `YOUR_API_KEY` in `TmdbApi.kt` with your actual API key:

```kotlin
private const val API_KEY = "YOUR_API_KEY"
```

### 3. **Install Dependencies**

Open the project in **Android Studio** and sync Gradle to install all dependencies.

### 4. **Run the App**

- Connect your Android device or emulator.
- Click **Run** in Android Studio to install and launch the app.

---

## 🏗️ Project Structure

This project follows the **MVVM architecture** with clean separation of concerns:

### 1. **Domain Layer**
- Contains the business logic and models:
  - `Movie`: Represents a movie entity.

### 2. **Data Layer**
- Handles data operations:
  - **Remote**: Fetches data from TMDB API (`TmdbApi`, `MovieDto`).
  - **Local**: Caches data in Room database (`MovieDao`, `MovieEntity`).
  - **Repository**: Manages data flow between remote and local sources (`MovieRepositoryImpl`).

### 3. **Presentation Layer**
- Displays data using **Jetpack Compose**:
  - **ViewModels**: Manage UI-related data (`MovieViewModel`, `MovieDetailViewModel`).
  - **Composables**: Build the UI (`MovieListScreen`, `MovieDetailScreen`).

### 4. **Dependency Injection**
- Uses **Koin** for managing dependencies like repositories, APIs, and ViewModels.

---

## 🎮 Usage

### 1. **Home Screen**
- Displays a grid of trending movies fetched from TMDB.

### 2. **Search Movies**
- Use the search bar to find movies by title.

### 3. **Movie Details**
- Tap on any movie to view its details, including:
  - Poster image
  - Title
  - Overview

### 4. **Offline Mode**
- Movies are cached locally using **Room** and displayed when offline.
