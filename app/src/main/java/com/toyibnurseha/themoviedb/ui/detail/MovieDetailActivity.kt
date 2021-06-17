package com.toyibnurseha.themoviedb.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.toyibnurseha.themoviedb.R
import com.toyibnurseha.themoviedb.data.detailmovie.DetailMovieEntity
import com.toyibnurseha.themoviedb.data.detailshow.DetailShowEntity
import com.toyibnurseha.themoviedb.databinding.ActivityMovieDetailBinding
import com.toyibnurseha.themoviedb.utils.Constant.IMAGE_PATH
import com.toyibnurseha.themoviedb.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.item_movie.*

class MovieDetailActivity : AppCompatActivity() {

    companion object {
        const val MOVIE_EXTRA_ID = "movie_id"
        const val MOVIE_EXTRA_TYPE = "movie_type"
        const val MOVIE_DATA = "movie_data"
        const val MOVIE_TYPE_SHOW = "show"
        const val MOVIE_TYPE_MOVIE = "movie"
        const val MOVIE_TYPE_DETAIL_SHOW = "detail_show"
        const val MOVIE_TYPE_DETAIL_MOVIE = "detail_movie"
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
            MOVIE_TYPE_MOVIE -> setupMovieData()
            MOVIE_TYPE_SHOW -> setupShowData()
            MOVIE_TYPE_DETAIL_MOVIE -> setupDetailMovieData()
            MOVIE_TYPE_DETAIL_SHOW -> setupDetailShowData()
        }

    }

    private fun setupDetailShowData() {
        val showData = intent.getParcelableExtra<DetailShowEntity>(MOVIE_DATA)
        binding.apply {
            tvTitleDetail.text = showData?.name
            tvDescriptionDetail.text = showData?.overview
            tvRateDetail.text = showData?.voteAverage.toString()
            tvReleaseDateDetail.text = showData?.firstAirDate
            val backdrop = IMAGE_PATH + showData?.backdropPath
            Glide.with(this@MovieDetailActivity).load(backdrop)
                .into(binding.ivThumbnail)
        }

        binding.btnWatchTrailer.setOnClickListener {
            if (showData!!.video) {
                Toast.makeText(
                    this,
                    "Video not available from our database",
                    Toast.LENGTH_SHORT
                ).show()
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
                            showData?.name,
                            showData?.overview,
                            showData?.firstAirDate
                        )
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(shareData, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun setupDetailMovieData() {
        val movieData = intent.getParcelableExtra<DetailMovieEntity>(MOVIE_DATA)
        binding.apply {
            tvTitleDetail.text = movieData?.title
            tvDescriptionDetail.text = movieData?.overview
            tvRateDetail.text = movieData?.voteAverage.toString()
            tvReleaseDateDetail.text = movieData?.releaseDate
            val backdrop = IMAGE_PATH + movieData?.backdropPath
            Glide.with(this@MovieDetailActivity).load(backdrop)
                .into(binding.ivThumbnail)
        }

        binding.btnWatchTrailer.setOnClickListener {
            if (movieData!!.video) {
                Toast.makeText(
                    this,
                    "Video not available from our database",
                    Toast.LENGTH_SHORT
                ).show()
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
                            movieData?.title,
                            movieData?.overview,
                            movieData?.releaseDate
                        )
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(shareData, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun setupShowData() {
        movieId?.let {
            viewModel.getDetailShow(it).observe(this, { res ->
                showLoading(false)
                initShowDetail(res)
                binding.btnFavorite.setOnClickListener {
                    viewModel.insertFavoriteShow(res)
                    Snackbar.make(binding.root, "inserted", Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initShowDetail(detailShowEntity: DetailShowEntity) {
        binding.tvTitleDetail.text = detailShowEntity.name
        binding.tvDescriptionDetail.text = detailShowEntity.overview
        binding.tvRateDetail.text = detailShowEntity.voteAverage.toString()
        binding.tvReleaseDateDetail.text = detailShowEntity.firstAirDate
        val backdrop = IMAGE_PATH + detailShowEntity.backdropPath
        Glide.with(this@MovieDetailActivity).load(backdrop)
            .into(binding.ivThumbnail)

        binding.btnWatchTrailer.setOnClickListener {
            if (!detailShowEntity.video) {
                Toast.makeText(
                    this,
                    "Video not available from our database",
                    Toast.LENGTH_SHORT
                ).show()
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
                            detailShowEntity.name,
                            detailShowEntity.overview,
                            detailShowEntity.firstAirDate
                        )
                    )
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(shareData, null)
                startActivity(shareIntent)
            }
        }
    }

    private fun setupMovieData() {
        movieId?.let {
            viewModel.getDetailMovie(it).observe(this, { res ->
                showLoading(false)
                initMovieDetail(res)
                binding.btnFavorite.setOnClickListener {
                    viewModel.insertFavoriteMovie(res)
                    Snackbar.make(binding.root, "inserted", Snackbar.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun initMovieDetail(movieEntity: DetailMovieEntity) {
        binding.tvTitleDetail.text = movieEntity.title
        binding.tvDescriptionDetail.text = movieEntity.overview
        binding.tvRateDetail.text = movieEntity.voteAverage.toString()
        binding.tvReleaseDateDetail.text = movieEntity.releaseDate.toString()
        val backdrop = IMAGE_PATH + movieEntity.backdropPath
        Glide.with(this@MovieDetailActivity).load(backdrop)
            .into(binding.ivThumbnail)

        binding.btnWatchTrailer.setOnClickListener {
            if (!movieEntity.video) {
                Toast.makeText(
                    this,
                    "Video not available from our database",
                    Toast.LENGTH_SHORT
                ).show()
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