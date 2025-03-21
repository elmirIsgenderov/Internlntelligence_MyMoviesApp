package com.example.mymoviesapp.retrofit

import com.example.mymoviesapp.retrofit.model.GenreResponse
import com.example.mymoviesapp.retrofit.model.MoviesResponse
import com.example.mymoviesapp.retrofit.model.VideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApiService {

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>

    @GET("discover/movie")
    suspend fun getCategoryMovies(
        @Query("api_key") apiKey: String,
        @Query("with_genres") genreId: Int
    ): Response<MoviesResponse>

    @GET("movie/upcoming")
    suspend fun getMovies(
        @Query("api_key") apiKey: String
    ): Response<MoviesResponse>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String
    ): Response<GenreResponse>

    @GET("search/movie")
    suspend fun getSearchMovies(
        @Query("api_key") apiKey: String,
        @Query("query") title: String
    ): Response<MoviesResponse>

    @GET("movie/{movie_id}/videos")
    suspend fun getMovieVideos(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String
    ): Response<VideoResponse>

}