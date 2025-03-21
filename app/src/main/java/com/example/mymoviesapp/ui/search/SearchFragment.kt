package com.example.mymoviesapp.ui.search

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mymoviesapp.databinding.FragmentSearchBinding
import com.example.mymoviesapp.ui.search.adapter.SearchMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var searchMoviesAdapter: SearchMoviesAdapter
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiKey = "246f88b8ef73253891506078dbacbca4"

        searchMoviesAdapter = SearchMoviesAdapter { movie ->
            movieDetail(movie.title)
        }
        binding.rvGenresMovies.adapter = searchMoviesAdapter
        binding.rvGenresMovies.layoutManager = GridLayoutManager(context, 2)
        setupSearchListener()
        viewModel.getMovies(apiKey)
        viewModel.movieResult.observe(viewLifecycleOwner) { movieResponse ->
            movieResponse?.results?.let { movies ->
                searchMoviesAdapter.submitList(movies)
            } ?: run {
                Log.e("HomeFragment", "Failed to fetch movies or no data available.")
            }
        }
    }

    private fun setupSearchListener() {
        binding.edtSearch.addTextChangedListener { editable ->
            val query = editable.toString().trim()
            if (query.isNotEmpty()) {
                searchMovies(query)
            } else {
                Log.e("Search", "$query empty")
            }
        }
    }

    private fun searchMovies(query: String) {
        val apiKey = "827c2738d945feb56a52ad0fc38dc665"
        viewModel.getSearch(apiKey, query)
        viewModel.searchResult.observe(viewLifecycleOwner) { searchResponse ->
            searchResponse?.results?.let { movies ->
                searchMoviesAdapter.submitList(movies)
            } ?: run {
                Log.e("SearchFragment", "No results found for query: $query")
            }
        }
    }

    private fun movieDetail(movieTitle: String?) {
        if (movieTitle != null) {
            val action =
                SearchFragmentDirections.actionSearchFragmentToMoviesDetailFragment(movieTitle)
            findNavController().navigate(action)
        } else {
            Log.e("HomeFragment", "Movie title is null")
        }
    }


}