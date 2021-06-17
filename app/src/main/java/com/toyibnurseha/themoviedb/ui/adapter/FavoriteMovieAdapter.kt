package com.toyibnurseha.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.databinding.ItemMovieBinding
import com.toyibnurseha.themoviedb.utils.Constant

class FavoriteMovieAdapter : RecyclerView.Adapter<FavoriteMovieAdapter.ViewHolder>() {

    private val movies = ArrayList<DetailMovieEntity>()

    fun setData(items: List<DetailMovieEntity>) {
        movies.clear()
        movies.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteMovieAdapter.ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteMovieAdapter.ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: DetailMovieEntity) {
            binding.tvRate.text = movie.voteAverage.toString()
            binding.tvTitle.text = movie.title
            binding.tvReleaseDate.text = movie.releaseDate
            val backdrop = Constant.IMAGE_PATH + movie.backdropPath
            Glide.with(itemView.context).load(backdrop).into(binding.ivThumbnail)
        }
    }

}