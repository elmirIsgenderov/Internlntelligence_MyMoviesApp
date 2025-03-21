package com.example.mymoviesapp.ui.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mymoviesapp.retrofit.Repository
import com.example.mymoviesapp.retrofit.model.MoviesResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _movieResult = MutableLiveData<MoviesResponse?>()
    val movieResult: LiveData<MoviesResponse?> = _movieResult

    private val _searchResult = MutableLiveData<MoviesResponse?>()
    val searchResult: LiveData<MoviesResponse?> = _searchResult


    fun getMovies(apiKey: String) {
        viewModelScope.launch {
            try {
                val response = repository.getMovies(apiKey)
                if (response.isSuccessful) {
                    _movieResult.postValue(response.body())
                } else {
                    Log.e("HomeViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _movieResult.postValue(null)
            }
        }
    }

    fun getSearch(apiKey: String, query: String) {
        viewModelScope.launch {
            try {
                val response = repository.getSearchMovies(apiKey, query)
                if (response.isSuccessful) {
                    _searchResult.postValue(response.body())
                } else {
                    Log.e("SearchViewModel", "Error: ${response.code()}")
                }
            } catch (e: Exception) {
                _searchResult.postValue(null)
            }
        }
    }
}