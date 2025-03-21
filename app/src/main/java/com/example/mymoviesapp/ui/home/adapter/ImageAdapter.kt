package com.example.mymoviesapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mymoviesapp.databinding.ItemImageMoviesBinding
import com.example.mymoviesapp.retrofit.model.Movie

class ImageAdapter(
    private val imageUrls: List<String>,
    private val onImageClick: () -> Unit
) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemImageMoviesBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemImageMoviesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val baseUrl = "https://image.tmdb.org/t/p/w500"
        Glide.with(holder.binding.root.context)
            .load("$baseUrl${imageUrls[position]}")
            .centerCrop()
            .into(holder.binding.imageView)

        holder.binding.imageView.setOnClickListener {
            onImageClick()
        }
    }

    override fun getItemCount(): Int = imageUrls.size

}