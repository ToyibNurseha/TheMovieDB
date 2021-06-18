package com.toyibnurseha.themoviedb.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.toyibnurseha.themoviedb.R
import com.toyibnurseha.themoviedb.data.response.movie.MovieEntity
import com.toyibnurseha.themoviedb.data.response.show.TVShowEntity
import com.toyibnurseha.themoviedb.databinding.ActivityMovieDetailBinding
import com.toyibnurseha.themoviedb.utils.Constant.IMAGE_PATH
import com.toyibnurseha.themoviedb.utils.Status
import com.toyibnurseha.themoviedb.viewmodel.ViewModelProviderFactory

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_EXTRA_ID = "movie_id"
        const val MOVIE_EXTRA_TYPE = "movie_type"
        const val MOVIE_TYPE_SHOW = "show"
        const val MOVIE_TYPE_MOVIE = "movie"
    }

    private lateinit var binding: ActivityMovieDetailBinding
    private lateinit var factory: ViewModelProviderFactory
    private lateinit var viewModel: MovieDetailViewModel

    private var movieId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieDetailBinding.inflate(layoutInflater)
        factory = ViewModelProviderFactory.getInstance(this)
        viewModel = ViewModelProvider(this, factory)[MovieDetailViewModel::class.java]
        showLoading(true)
        setContentView(binding.root)
        setupData()
    }

    private fun setupData() {
        movieId = intent.getIntExtra(MOVIE_EXTRA_ID, 0)

        when (intent.getStringExtra(MOVIE_EXTRA_TYPE)) {
            MOVIE_TYPE_MOVIE -> getMoviesData(movieId!!)
            MOVIE_TYPE_SHOW -> getShowData(movieId!!)
        }

    }

    private fun getShowData(movieId: Int) {
        viewModel.setShowData(movieId).observe(this, { movie ->
            when (movie.status) {
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.SUCCESS -> {
                    if (movie.data != null) {
                        showLoading(false)
                        initShowDetail(movie.data)
                    }
                }
                Status.ERROR -> {
                    showLoading(false)
                    Toast.makeText(applicationContext, "Gagal memuat data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun initShowDetail(data: TVShowEntity) {
        binding.tvTitleDetail.text = data.name
        binding.tvDescriptionDetail.text = data.overview
        binding.tvRateDetail.text = data.voteAverage.toString()
        binding.tvReleaseDateDetail.text = data.firstAirDate.toString()
        val backdrop = IMAGE_PATH + data.backdrop_path
        Glide.with(this@MovieDetailActivity).load(backdrop)
            .into(binding.ivThumbnail)

        if (data.addWatchlist) {
            binding.btnAddWatchList.text = resources.getString(R.string.remove_from_watch_list)
        } else {
            binding.btnAddWatchList.text = resources.getString(R.string.add_to_watch_list)
        }

        binding.btnAddWatchList.setOnClickListener {
            viewModel.insertFavoriteShow(data)
            if (data.addWatchlist) {
                Toast.makeText(this, resources.getString(R.string.deleted_from_watch_list), Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, resources.getString(R.string.added_to_watch_list), Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnShare.setOnClickListener {
            movieId?.let {
                val shareData = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        resources.getString(
                            R.string.share_text,
                            data.name,
                            data.overview,
                            data.firstAirDate
                        )
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(shareData, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun getMoviesData(id: Int) {
        viewModel.setMoviesData(id).observe(this, { movie ->
            when (movie.status) {
                Status.LOADING -> {
                    showLoading(true)
                }
                Status.SUCCESS -> {
                    if (movie.data != null) {
                        showLoading(false)
                        initMovieDetail(movie.data)
                    }
                }
                Status.ERROR -> {
                    showLoading(false)
                    Toast.makeText(applicationContext, "Gagal memuat data", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })
    }

    private fun initMovieDetail(movieEntity: MovieEntity) {
        binding.tvTitleDetail.text = movieEntity.title
        binding.tvDescriptionDetail.text = movieEntity.overview
        binding.tvRateDetail.text = movieEntity.voteAverage.toString()
        binding.tvReleaseDateDetail.text = movieEntity.releaseDate.toString()
        val backdrop = IMAGE_PATH + movieEntity.backdropPath
        Glide.with(this@MovieDetailActivity).load(backdrop)
            .into(binding.ivThumbnail)

        if (movieEntity.addWatchlist) {
            binding.btnAddWatchList.text = resources.getString(R.string.remove_from_watch_list)
        } else {
            binding.btnAddWatchList.text = resources.getString(R.string.add_to_watch_list)
        }

        binding.btnAddWatchList.setOnClickListener {
            viewModel.insertFavoriteMovie(movieEntity)
            if (movieEntity.addWatchlist) {
                binding.btnAddWatchList.text = resources.getString(R.string.remove_from_watch_list)
            } else {
                binding.btnAddWatchList.text = resources.getString(R.string.add_to_watch_list)
            }
        }

        binding.btnShare.setOnClickListener {
            movieId?.let {
                val shareData = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(
                        Intent.EXTRA_TEXT,
                        resources.getString(
                            R.string.share_text,
                            movieEntity.title,
                            movieEntity.overview,
                            movieEntity.releaseDate
                        )
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(shareData, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}