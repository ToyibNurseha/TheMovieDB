package com.toyibnurseha.themoviedb.ui.favorite

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import com.toyibnurseha.themoviedb.R
import com.toyibnurseha.themoviedb.databinding.FragmentFavoriteBinding
import com.toyibnurseha.themoviedb.ui.adapter.WatchListMovieAdapter
import com.toyibnurseha.themoviedb.ui.adapter.WatchListShowAdapter
import com.toyibnurseha.themoviedb.ui.detail.MovieDetailActivity
import com.toyibnurseha.themoviedb.viewmodel.ViewModelProviderFactory

class FavoriteFragment : Fragment() {

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var watchListShowAdapter: WatchListShowAdapter
    private lateinit var watchListMovieAdapter: WatchListMovieAdapter
    private lateinit var viewModel: FavoriteViewModel
    private lateinit var factory: ViewModelProviderFactory
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoriteBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        watchListShowAdapter = WatchListShowAdapter()
        watchListMovieAdapter = WatchListMovieAdapter()
        factory = ViewModelProviderFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        showLoading(true)
        setupFilter()
        setupMovieData()
        setupShowData()
        onClickHandler()
    }

    private fun setupFilter() {
        binding.moviesChip.checkedIcon?.let {
            val wrappedDrawable = DrawableCompat.wrap(it)
            DrawableCompat.setTint(wrappedDrawable, Color.WHITE)
            binding.moviesChip.checkedIcon = wrappedDrawable
        }

        binding.showsChip.checkedIcon?.let {
            val wrappedDrawable = DrawableCompat.wrap(it)
            DrawableCompat.setTint(wrappedDrawable, Color.WHITE)
            binding.showsChip.checkedIcon = wrappedDrawable
        }

        binding.chipGroup.forEach {
            (it as Chip).setOnCheckedChangeListener { _, isChecked ->
                handleFilterSelection(isChecked)
            }
        }
    }

    private fun handleFilterSelection(isChecked: Boolean) {
        if (isChecked) {
            binding.chipGroup.checkedChipIds.forEach {
                val chip = view?.findViewById<Chip>(it)
                when (chip?.text) {
                    resources.getString(R.string.movies) -> binding.rvFavoriteShow.visibility =
                        View.GONE
                    resources.getString(R.string.tv_show) -> binding.rvFavoriteMovie.visibility =
                        View.GONE
                }
            }
        } else {
            binding.rvFavoriteShow.visibility = View.VISIBLE
            binding.rvFavoriteMovie.visibility = View.VISIBLE
        }
    }

    private fun onClickHandler() {
        watchListMovieAdapter.setOnItemClickListener { movie ->
            startActivity(Intent(requireContext(), MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailActivity.MOVIE_EXTRA_ID, movie?.id)
                putExtra(MovieDetailActivity.MOVIE_EXTRA_TYPE, MovieDetailActivity.MOVIE_TYPE_MOVIE)
            })
        }
        watchListShowAdapter.setOnItemClickListener { show ->
            startActivity(Intent(requireContext(), MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailActivity.MOVIE_EXTRA_ID, show?.id)
                putExtra(MovieDetailActivity.MOVIE_EXTRA_TYPE, MovieDetailActivity.MOVIE_TYPE_SHOW)
            })
        }
    }

    private fun setupMovieData() {
        viewModel.getMoviesWatchlist().observe(viewLifecycleOwner, { movies ->
            showLoading(false)
            watchListMovieAdapter.submitList(movies)
            binding.rvFavoriteMovie.apply {
                adapter = watchListMovieAdapter
                layoutManager = LinearLayoutManager(requireContext())
                isNestedScrollingEnabled = true
            }
        })
    }

    private fun setupShowData() {
        viewModel.getTvShowsWatchlist().observe(viewLifecycleOwner, { shows ->
            showLoading(false)
            watchListShowAdapter.submitList(shows)
            binding.rvFavoriteShow.apply {
                adapter = watchListShowAdapter
                layoutManager = LinearLayoutManager(requireContext())
                isNestedScrollingEnabled = true
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}