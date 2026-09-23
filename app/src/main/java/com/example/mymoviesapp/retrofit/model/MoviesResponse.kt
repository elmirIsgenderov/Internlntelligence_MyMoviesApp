package com.example.mymoviesapp.retrofit.model

data class MoviesResponse(
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val overview: String,
    val poster_path: String?,
    val release_date: String,
    val title: String,
)

data class GenreResponse(
    val genres: List<Genre>
)

data class Genre(
    val id: Int,
    val name: String
)

