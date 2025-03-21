package com.example.mymoviesapp.ui.detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.mymoviesapp.R
import com.example.mymoviesapp.databinding.FragmentDetailBinding
import com.example.mymoviesapp.retrofit.model.Movie
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    private val args: DetailFragmentArgs by navArgs()
    private val viewModel: DetailViewModel by viewModels()
    private var videoUrl: String? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val apiKey = "246f88b8ef73253891506078dbacbca4"

        viewModel.getSearchMovieByTitle(apiKey, args.moviesTitle)

        observeMovieDetails()
        observeTrailerUrl()
        save()
    }

    private fun observeMovieDetails() {
        viewModel.movieDetails.observe(viewLifecycleOwner) { movie ->
            if (movie != null) {
                populateMovieDetails(movie)
            } else {
                Toast.makeText(requireContext(), "Movies empty", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun observeTrailerUrl() {
        viewModel.trailerUrl.observe(viewLifecycleOwner) { trailerUrl ->
            if (trailerUrl != null) {
                binding.btnPlay.setOnClickListener {
                    videoUrl = trailerUrl
                    playTrailer(trailerUrl)
                }
            } else {
                Toast.makeText(requireContext(), "No trailer found", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun populateMovieDetails(movie: Movie) {
        binding.txtTitle.text = movie.title
        binding.txtDescription.text = movie.overview
        binding.txtTrailerDescrpt.text = movie.overview
        binding.txtYear.text = movie.release_date

        if (!movie.poster_path.isNullOrEmpty()) {
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(binding.imgMoviePoster)
            Glide.with(requireContext())
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(binding.imgTrailerPoster)
        } else {
            Log.e("DetailFragment", "Poster path is null or empty")
        }
    }

    private fun playTrailer(url: String) {
        val webView: WebView = binding.webViewTrailer
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        webView.visibility = View.VISIBLE
    }

    private fun save() {

        val image = binding.imgSave

        image.setOnClickListener {
            image.setImageResource(
                if (image.tag == "on") {
                    image.tag = "off"
                    R.drawable.ic_save_empty
                } else {
                    image.tag = "on"
                    R.drawable.ic_save
                }
            )
        }
        binding.imgBack.setOnClickListener {
            findNavController().navigate(R.id.action_moviesDetailFragment_to_homeFragment)
        }
    }

}