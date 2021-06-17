package com.toyibnurseha.themoviedb.ui.movie

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyibnurseha.themoviedb.databinding.FragmentMovieBinding
import com.toyibnurseha.themoviedb.ui.adapter.MovieAdapter
import com.toyibnurseha.themoviedb.ui.detail.MovieDetailActivity
import com.toyibnurseha.themoviedb.ui.detail.MovieDetailActivity.Companion.MOVIE_EXTRA_ID
import com.toyibnurseha.themoviedb.viewmodel.ViewModelProviderFactory

class MovieFragment : Fragment() {

    private lateinit var binding: FragmentMovieBinding
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieAdapter = MovieAdapter()
        showLoading(true)
        setupData()
        clickHandler()
    }

    private fun clickHandler() {
        movieAdapter.setOnItemClickListener { movie ->
            startActivity(Intent(requireContext(), MovieDetailActivity::class.java).apply {
                putExtra(MOVIE_EXTRA_ID, movie.id)
                putExtra(MovieDetailActivity.MOVIE_EXTRA_TYPE, MovieDetailActivity.MOVIE_TYPE_MOVIE)
            })
        }
    }

    private fun setupData() {
        val factory = ViewModelProviderFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[MovieViewModel::class.java]
        viewModel.getPopularMovies().observe(viewLifecycleOwner, { movies ->
            showLoading(false)
            movieAdapter.setData(movies)
            binding.rvMovies.apply {
                adapter = movieAdapter
                layoutManager = LinearLayoutManager(requireContext())
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