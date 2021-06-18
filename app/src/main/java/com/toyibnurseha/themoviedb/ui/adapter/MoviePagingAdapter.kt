package com.toyibnurseha.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.databinding.ItemMovieBinding
import com.toyibnurseha.themoviedb.utils.Constant

class MoviePagingAdapter : PagedListAdapter<MovieEntity, MoviePagingAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MovieEntity>() {
            override fun areItemsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MovieEntity, newItem: MovieEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MoviePagingAdapter.ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviePagingAdapter.ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: MovieEntity?) {
            with(binding) {
                tvTitle.text = movie?.title
                tvReleaseDate.text = movie?.releaseDate
                tvRate.text = movie?.voteAverage.toString()
                val thumbnail = Constant.IMAGE_PATH + movie?.backdropPath
                Glide.with(itemView.context).load(thumbnail).into(ivThumbnail)
                itemView.setOnClickListener {
                    listener?.let {
                        it(movie)
                    }
                }
            }
        }
    }

    private var listener: ((MovieEntity?) -> Unit)? = null

    fun setOnItemClickListener(clickListener: ((MovieEntity?) -> Unit)?) {
        listener = clickListener
    }

}