package com.example.mymoviesapp.retrofit.model

data class MoviesResponse(
    //val page: Int,
    val results: List<Movie>
    //val total_pages: Int,
    //val total_results: Int
)

data class Movie(
    //val adult: Boolean,
    //val backdrop_path: String,
    //val genre_ids: List<Int>,
    //val original_language: String,
    //val original_title: String,
    //val popularity: Double,
    //val video: Boolean,
    //val vote_average: Double,
    //val vote_count: Int,
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

