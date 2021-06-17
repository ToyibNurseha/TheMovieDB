package com.toyibnurseha.themoviedb.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyibnurseha.themoviedb.databinding.FragmentFavoriteBinding
import com.toyibnurseha.themoviedb.ui.adapter.FavoriteMovieAdapter
import com.toyibnurseha.themoviedb.ui.adapter.FavoriteShowAdapter
import com.toyibnurseha.themoviedb.viewmodel.ViewModelProviderFactory

class FavoriteFragment : Fragment() {

    private lateinit var binding : FragmentFavoriteBinding
    private lateinit var favMovieAdapter : FavoriteMovieAdapter
    private lateinit var favShowAdapter : FavoriteShowAdapter
    private lateinit var viewModel : FavoriteViewModel
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
        favMovieAdapter = FavoriteMovieAdapter()
        favShowAdapter = FavoriteShowAdapter()
        factory = ViewModelProviderFactory.getInstance(requireContext())
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        showLoading(true)
        setupMovieData()
        setupShowData()
        onClickHandler()
    }

    private fun onClickHandler() {

    }

    private fun setupMovieData() {
        viewModel.getFavoriteMovies().observe(viewLifecycleOwner, {movies ->
            showLoading(false)
            favMovieAdapter.setData(movies)
            binding.rvFavoriteMovie.apply {
                adapter = favMovieAdapter
                layoutManager = LinearLayoutManager(requireContext())
            }
        })
    }

    private fun setupShowData() {
        viewModel.getFavoriteShows().observe(viewLifecycleOwner, {movies ->
            showLoading(false)
            favShowAdapter.setData(movies)
            binding.rvFavoriteShow.apply {
                adapter = favShowAdapter
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