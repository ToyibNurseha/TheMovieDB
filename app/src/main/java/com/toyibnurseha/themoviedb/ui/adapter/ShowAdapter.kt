package com.toyibnurseha.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toyibnurseha.themoviedb.data.show.TVShowEntity
import com.toyibnurseha.themoviedb.databinding.ItemMovieBinding
import com.toyibnurseha.themoviedb.utils.Constant

class ShowAdapter : RecyclerView.Adapter<ShowAdapter.ViewHolder>(){

    private val shows = ArrayList<TVShowEntity>()

    fun setData(items: List<TVShowEntity>) {
        shows.clear()
        shows.addAll(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShowAdapter.ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowAdapter.ViewHolder, position: Int) {
        holder.bind(shows[position])
    }

    override fun getItemCount(): Int = shows.size

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(movie: TVShowEntity) {
            with(binding) {
                tvTitle.text = movie.originalName
                tvReleaseDate.text = movie.firstAirDate
                tvRate.text = movie.voteAverage.toString()
                val thumbnail = Constant.IMAGE_PATH + movie.backdrop_path
                Glide.with(itemView.context).load(thumbnail).into(ivThumbnail)
                itemView.setOnClickListener {
                    listener?.let {
                        it(movie)
                    }
                }
            }
        }
    }

    private var listener: ((TVShowEntity) -> Unit)? = null

    fun setOnItemClickListener(clickListener: ((TVShowEntity) -> Unit)?) {
        listener = clickListener
    }
}