package com.example.mymoviesapp.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.retrofit.Repository
import com.example.mymoviesapp.retrofit.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _movieDetails = MutableLiveData<Movie?>()
    val movieDetails: LiveData<Movie?> = _movieDetails

    private val _trailerUrl = MutableLiveData<String?>()
    val trailerUrl: LiveData<String?> = _trailerUrl


    fun getSearchMovieByTitle(apiKey: String, title: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSearchMovies(apiKey, title)
                if (response.isSuccessful) {
                    val movies = response.body()?.results
                    if (!movies.isNullOrEmpty()) {
                        _movieDetails.postValue(movies.first())
                        val movieId = movies.first().id
                        getMovieTrailer(apiKey, movieId)
                    } else {
                        Log.e("DetailFragment", "No movies found for title: $title")
                    }
                } else {
                    Log.e("DetailFragment", "API response error: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("DetailFragment", "Error searching movie by title: $e")
            }
        }
    }

    private suspend fun getMovieTrailer(apiKey: String, movieId: Int) {
        try {
            val response = repository.getMovieVideos(movieId, apiKey)
            if (response.isSuccessful) {
                val video = response.body()?.results
                if (!video.isNullOrEmpty()) {
                    // Trailer tipində olan videonu tap.
                    val trailer = video.firstOrNull { it.type == "Trailer" }
                    if (trailer != null) {
                        val videoUrl = "https://www.youtube.com/embed/${trailer.key}"
                        _trailerUrl.postValue(videoUrl)
                    } else {
                        Log.e("Trailer", "No trailer found for this movie")
                    }
                } else {
                    Log.e("Trailer", "Videos list is empty")
                }
            } else {
                Log.e("Trailer", "API response error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e("Trailer", "Error getting trailer: $e")
        }
    }

}