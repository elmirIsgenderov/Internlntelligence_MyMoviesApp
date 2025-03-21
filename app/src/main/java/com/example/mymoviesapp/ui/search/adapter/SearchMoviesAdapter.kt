package com.example.mymoviesapp.ui.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymoviesapp.databinding.ItemSearchMoviesBinding
import com.example.mymoviesapp.retrofit.model.Movie

class SearchMoviesAdapter(
    var itemClick: (item: Movie) -> Unit
) : RecyclerView.Adapter<SearchMoviesAdapter.ViewHolder>() {

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
            ItemSearchMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class ViewHolder(val binding: ItemSearchMoviesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(movies: Movie) {
            val baseUrl = "https://image.tmdb.org/t/p/w500"
            Glide.with(binding.imgSearch.context)
                .load("$baseUrl${movies.poster_path}")
                .into(binding.imgSearch)
            itemView.setOnClickListener {
                itemClick(movies)
            }
            binding.txtMovieName.text = movies.title
        }
    }

}