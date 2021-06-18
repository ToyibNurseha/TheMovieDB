package com.toyibnurseha.themoviedb.ui.movie

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyibnurseha.themoviedb.databinding.FragmentMovieBinding
import com.toyibnurseha.themoviedb.ui.adapter.MoviePagingAdapter
import com.toyibnurseha.themoviedb.ui.detail.MovieDetailActivity
import com.toyibnurseha.themoviedb.ui.detail.MovieDetailActivity.Companion.MOVIE_EXTRA_ID
import com.toyibnurseha.themoviedb.utils.Status
import com.toyibnurseha.themoviedb.viewmodel.ViewModelProviderFactory

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieAdapterPaging: MoviePagingAdapter

    companion object {
        const val TAG = "MovieFragment"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapterPaging = MoviePagingAdapter()
        showLoading(true)
        setupData()
        clickHandler()
    }

    private fun clickHandler() {
        movieAdapterPaging.setOnItemClickListener { movie ->
            startActivity(Intent(requireContext(), MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_EXTRA_ID, movie?.id)
                putExtra(MovieDetailActivity.MOVIE_EXTRA_TYPE, MovieDetailActivity.MOVIE_TYPE_MOVIE)
            })
        }
    }

    private fun setupData() {
        val factory = ViewModelProviderFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        viewModel.getMovies().observe(viewLifecycleOwner, { movies ->
            if (movies != null) {
                when(movies.status) {
                    Status.LOADING -> {
                        showLoading(true)
                    }

                    Status.SUCCESS -> {
                        showLoading(false)
                        Log.d(TAG, "setupData: ${movies.data}")
                        movieAdapterPaging.submitList(movies.data)
                        binding.rvMovies.apply {
                            adapter = movieAdapterPaging
                            layoutManager = LinearLayoutManager(requireContext())
                        }
                    }

                    Status.ERROR -> {
                        Log.d(TAG, "setupData: failed")
                    }
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        }else {
            binding.progressBar.visibility = View.GONE
        }
    }
}