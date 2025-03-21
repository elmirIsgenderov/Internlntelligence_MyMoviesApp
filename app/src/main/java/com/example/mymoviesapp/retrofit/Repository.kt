package com.example.mymoviesapp.retrofit

import com.example.mymoviesapp.retrofit.model.GenreResponse
import com.example.mymoviesapp.retrofit.model.MoviesResponse
import com.example.mymoviesapp.retrofit.model.VideoResponse
import retrofit2.Response

class Repository(private val moviesApi: MoviesApiService) {

    suspend fun getPopularMovies(apiKey: String): Response<MoviesResponse> {
        return moviesApi.getPopularMovies(apiKey)
    }

    suspend fun getTopRatedMovies(apiKey: String): Response<MoviesResponse> {
        return moviesApi.getTopRatedMovies(apiKey)
    }

    suspend fun getNowPlayingMovies(apiKey: String): Response<MoviesResponse> {
        return moviesApi.getNowPlayingMovies(apiKey)
    }

    suspend fun getGenres(apiKey: String): Response<GenreResponse> {
        return moviesApi.getGenres(apiKey)
    }

    suspend fun getCategoryMovies(apiKey: String, genreId: Int): Response<MoviesResponse> {
        return moviesApi.getCategoryMovies(apiKey, genreId)
    }

    suspend fun getMovies(apiKey: String): Response<MoviesResponse> {
        return moviesApi.getMovies(apiKey)
    }

    suspend fun getSearchMovies(apiKey: String, title: String): Response<MoviesResponse> {
        return moviesApi.getSearchMovies(apiKey, title)
    }

    suspend fun getMovieVideos(movieId: Int, apiKey: String): Response<VideoResponse> {
        return moviesApi.getMovieVideos(movieId, apiKey)
    }


}