package com.example.mymoviesapp.ui.home.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymoviesapp.databinding.ItemMostMoviesBinding
import com.example.mymoviesapp.retrofit.model.Movie

class MostPopularAdapter(var itemClick: (item: Movie) -> Unit) :
    RecyclerView.Adapter<MostPopularAdapter.PopularMoviesViewHolder>() {

    private val diffUtilCallBack = object : DiffUtil.ItemCallback<Movie>() {
        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this, diffUtilCallBack)

    fun submitList(movieList: List<Movie>) {
        differ.submitList(movieList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMoviesViewHolder {
        val binding = ItemMostMoviesBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PopularMoviesViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: PopularMoviesViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class PopularMoviesViewHolder(private val binding: ItemMostMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movies: Movie) {
            val baseUrl = "https://image.tmdb.org/t/p/w500"
            Glide.with(binding.imgMostPopular.context)
                .load("$baseUrl${movies.poster_path}")
                .into(binding.imgMostPopular)

            itemView.setOnClickListener {
                itemClick(movies)
            }
        }

    }
}