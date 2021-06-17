package com.toyibnurseha.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.data.movie.MovieEntity
import com.toyibnurseha.themoviedb.databinding.ItemMovieBinding
import com.toyibnurseha.themoviedb.utils.Constant

class FavoriteShowAdapter : RecyclerView.Adapter<FavoriteShowAdapter.ViewHolder>() {

    private val movies = ArrayList<DetailShowEntity>()

    fun setData(items: List<DetailShowEntity>) {
        movies.clear()
        movies.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteShowAdapter.ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteShowAdapter.ViewHolder, position: Int) {
        holder.bind(movies[position])
    }

    override fun getItemCount(): Int = movies.size

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: DetailShowEntity) {
            binding.tvRate.text = show.voteAverage.toString()
            binding.tvTitle.text = show.name
            binding.tvReleaseDate.text = show.firstAirDate
            val backdrop = Constant.IMAGE_PATH + show.backdropPath
            Glide.with(itemView.context).load(backdrop).into(binding.ivThumbnail)
            itemView.setOnClickListener {
                listener?.let {
                    it(show)
                }
            }
        }
    }

    private var listener: ((DetailShowEntity) -> Unit)? = null

    fun setOnItemClickListener(clickListener: ((DetailShowEntity) -> Unit)?) {
        listener = clickListener
    }

}