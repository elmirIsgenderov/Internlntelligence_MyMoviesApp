package com.example.mymoviesapp.ui.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymoviesapp.databinding.FragmentHomeBinding
import com.example.mymoviesapp.ui.home.adapter.MostPopularAdapter
import com.example.mymoviesapp.ui.home.adapter.TopRatedAdapter
import com.example.mymoviesapp.ui.home.adapter.ImageAdapter
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private lateinit var mostPopularAdapter: MostPopularAdapter
    private lateinit var topRatedAdapter: TopRatedAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiKey = "246f88b8ef73253891506078dbacbca4"
        setupRecyclerViewAdapters()
        fetchMovies(apiKey)


        viewModel.nowPlayingMoviesResult.observe(viewLifecycleOwner) { movieResponse ->
            movieResponse?.results?.let { movies ->
                val limitedMovies = movies.take(6)
                val adapter = ImageAdapter(
                    limitedMovies.mapNotNull { it.poster_path }
                ) {}
                binding.viewPager.adapter = adapter
                val dotsIndicator = binding.dotsIndicator
                dotsIndicator.setViewPager2(binding.viewPager)
            } ?: run {
                Log.e("HomeFragment", "Failed to fetch movies or no data available.")
            }
        }

        viewModel.popularMovieResult.observe(viewLifecycleOwner) { movieResponse ->
            movieResponse?.results?.let { movies ->
                mostPopularAdapter.submitList(movies)
            } ?: run {
                Log.e("HomeFragment", "Failed to fetch movies or no data available.")
            }
        }
        viewModel.topRatedMovieResult.observe(viewLifecycleOwner) { movieResponse ->
            movieResponse?.results?.let { movies ->
                topRatedAdapter.submitList(movies)
            } ?: run {
                Log.e("HomeFragment", "Failed to fetch movies or no data available.")
            }
        }

        viewModel.filteredMovies.observe(viewLifecycleOwner) { movies ->
            mostPopularAdapter.submitList(movies)
        }

        viewModel.movieGenres.observe(viewLifecycleOwner) { genres ->
            genres?.let {
                val chipGroup = binding.chipGroup
                chipGroup.removeAllViews()

                genres.genres?.forEach { genre ->
                    val chip = Chip(requireContext())
                    chip.text = genre.name
                    chip.isCheckable = true
                    chip.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            viewModel.getGenreMovies(apiKey, genre.id)
                        } else {
                            viewModel.getPopularMovies(apiKey)
                        }
                    }
                    chipGroup.addView(chip)
                }
            }
        }
    }

    private fun fetchMovies(apiKey: String) {
        viewModel.getPopularMovies(apiKey)
        viewModel.getTopRatedMovies(apiKey)
        viewModel.getNowPlayingMovies(apiKey)
        viewModel.getGenres(apiKey)
    }

    private fun setupRecyclerViewAdapters() {
        mostPopularAdapter = MostPopularAdapter { movie -> movieDetail(movie.title) }
        binding.rvMostPopular.adapter = mostPopularAdapter
        binding.rvMostPopular.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        topRatedAdapter = TopRatedAdapter { movie -> movieDetail(movie.title) }
        binding.rvTopRated.adapter = topRatedAdapter
        binding.rvTopRated.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun movieDetail(moviesTitle: String?) {
        if (moviesTitle != null) {
            val action =
                HomeFragmentDirections.actionHomeFragmentToMoviesDetailFragment(moviesTitle)
            findNavController().navigate(action)
        } else {
            Log.e("HomeFragment", "Movie title is null")
        }
    }
}

