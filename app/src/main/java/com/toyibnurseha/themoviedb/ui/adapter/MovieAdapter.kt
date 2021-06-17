package com.toyibnurseha.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.databinding.ItemMovieBinding
import com.toyibnurseha.themoviedb.utils.Constant.IMAGE_PATH

class MovieAdapter : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private val movies = ArrayList<MovieEntity>()

    fun setData(items: List<MovieEntity>) {
        movies.clear()
        movies.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity) {
            with(binding) {
                tvTitle.text = movie.title
                tvReleaseDate.text = movie.releaseDate
                tvRate.text = movie.voteAverage.toString()
                val thumbnail = IMAGE_PATH + movie.backdropPath
                Glide.with(itemView.context).load(thumbnail).into(ivThumbnail)
                itemView.setOnClickListener {
                    listener?.let {
                        it(movie)
                    }
                }
            }
        }
    }

    private var listener: ((MovieEntity) -> Unit)? = null

    fun setOnItemClickListener(clickListener: ((MovieEntity) -> Unit)?) {
        listener = clickListener
    }
}