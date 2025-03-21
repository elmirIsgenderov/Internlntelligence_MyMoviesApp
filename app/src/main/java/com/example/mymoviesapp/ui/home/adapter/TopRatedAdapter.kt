package com.example.mymoviesapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymoviesapp.databinding.ItemTopMoviesBinding
import com.example.mymoviesapp.retrofit.model.Movie

class TopRatedAdapter(
    private val itemClick: (item: Movie) -> Unit
) :
    RecyclerView.Adapter<TopRatedAdapter.ViewHolder>() {


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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemTopMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder(val binding: ItemTopMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            Glide.with(binding.root)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .into(binding.imgTopRated)

            binding.imgTopRated.setOnClickListener {
                itemClick(movie)
            }
        }
    }
}