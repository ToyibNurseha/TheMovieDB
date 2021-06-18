package com.toyibnurseha.themoviedb.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.databinding.ItemMovieBinding
import com.toyibnurseha.themoviedb.utils.Constant

class ShowPagingAdapter : PagedListAdapter<TVShowEntity, ShowPagingAdapter.ViewHolder>(DIFF_CALLBACK) {
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TVShowEntity>() {
            override fun areItemsTheSame(oldItem: TVShowEntity, newItem: TVShowEntity): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TVShowEntity, newItem: TVShowEntity): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ShowPagingAdapter.ViewHolder {
        val view = ItemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShowPagingAdapter.ViewHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class ViewHolder(private val binding: ItemMovieBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(show: TVShowEntity?) {
            with(binding) {
                tvTitle.text = show?.name
                tvReleaseDate.text = show?.firstAirDate
                tvRate.text = show?.voteAverage.toString()
                val thumbnail = Constant.IMAGE_PATH + show?.backdrop_path
                Glide.with(itemView.context).load(thumbnail).into(ivThumbnail)
                itemView.setOnClickListener {
                    listener?.let {
                        it(show)
                    }
                }
            }
        }
    }

    private var listener: ((TVShowEntity?) -> Unit)? = null

    fun setOnItemClickListener(clickListener: ((TVShowEntity?) -> Unit)?) {
        listener = clickListener
    }


}