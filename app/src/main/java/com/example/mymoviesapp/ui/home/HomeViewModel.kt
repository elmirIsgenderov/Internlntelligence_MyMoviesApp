package com.example.mymoviesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.retrofit.Repository
import com.example.mymoviesapp.retrofit.model.GenreResponse
import com.example.mymoviesapp.retrofit.model.Movie
import com.example.mymoviesapp.retrofit.model.MoviesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {


    private val _popularMovieResult = MutableLiveData<MoviesResponse?>()
    val popularMovieResult: LiveData<MoviesResponse?> = _popularMovieResult

    private val _topRatedMovieResult = MutableLiveData<MoviesResponse?>()
    val topRatedMovieResult: LiveData<MoviesResponse?> = _topRatedMovieResult

 /* val topRatedMovieResult = MutableLiveData<MoviesResponse?>() -- Eger pramoy bele etseydik Fragment və Activity bu dəyişəni dəyişdirə bilərdi.
    Buda, arxitektura üçün təhlükəlidir! View yalnız məlumatı göstərməli, onu dəyişməməlidir.
    Bu yanaşma Encapsulation (Qablaşdırma) prinsipi ilə daha təhlükəsiz və strukturlaşdırılmış kod yazmağa kömək edir.

    🔹 ViewModel məlumatı dəyişir.
    🔹 View yalnız oxuyur, dəyişdirə bilmir.
    🔹 Kod təmiz və asan idarə olunan olur 🚀*/

    private val _nowPlayingMoviesResult = MutableLiveData<MoviesResponse?>()
    val nowPlayingMoviesResult: LiveData<MoviesResponse?> = _nowPlayingMoviesResult

    private val _movieGenres = MutableLiveData<GenreResponse?>()
    val movieGenres: LiveData<GenreResponse?> = _movieGenres

    private val _filteredMovies = MutableLiveData<List<Movie>>()
    val filteredMovies: LiveData<List<Movie>> = _filteredMovies

    fun getPopularMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getPopularMovies(apiKey)
                if (response.isSuccessful) {
                    _filteredMovies.postValue(response.body()?.results ?: emptyList())
                }
            } catch (e: Exception) {
                _filteredMovies.postValue(emptyList())
            }
        }
    }

    fun getTopRatedMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getTopRatedMovies(apiKey)
                if (response.isSuccessful) {
                    _topRatedMovieResult.postValue(response.body())
                }
            } catch (e: Exception) {
                _topRatedMovieResult.postValue(null)
            }
        }
    }

    fun getNowPlayingMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getNowPlayingMovies(apiKey)
                if (response.isSuccessful) {
                    _nowPlayingMoviesResult.postValue(response.body())
                }
            } catch (e: Exception) {
                _nowPlayingMoviesResult.postValue(null)
            }
        }
    }

    fun getGenres(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getGenres(apiKey)
                if (response.isSuccessful) {
                    _movieGenres.postValue(response.body())
                }
            } catch (e: Exception) {
                _movieGenres.postValue(null)
            }
        }
    }

    fun getGenreMovies(apiKey: String, genreId: Int) {
        viewModelScope.launch {
            try {
                val response = repository.getCategoryMovies(apiKey, genreId)
                if (response.isSuccessful) {
                    _filteredMovies.postValue(response.body()?.results ?: emptyList())
                }
            } catch (e: Exception) {
                _filteredMovies.postValue(emptyList())
            }
        }
    }
}