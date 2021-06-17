package com.toyibnurseha.themoviedb.ui.show

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.toyibnurseha.themoviedb.databinding.FragmentTvShowBinding
import com.toyibnurseha.themoviedb.ui.adapter.ShowAdapter
import com.toyibnurseha.themoviedb.ui.detail.MovieDetailActivity
import com.toyibnurseha.themoviedb.viewmodel.ViewModelProviderFactory

class TvShowFragment : Fragment() {

    private lateinit var binding: FragmentTvShowBinding
    private lateinit var adapterShow: ShowAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTvShowBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoading(true)
        adapterShow = ShowAdapter()
        setupData()
        onClickHandler()
    }

    private fun onClickHandler() {
        adapterShow.setOnItemClickListener { show ->
            startActivity(Intent(requireContext(), MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailActivity.MOVIE_EXTRA_ID, show.id)
                putExtra(MovieDetailActivity.MOVIE_EXTRA_TYPE, MovieDetailActivity.MOVIE_TYPE_SHOW)
            })
        }
    }

    private fun setupData() {
        val factory = ViewModelProviderFactory.getInstance(requireContext())
        val viewModel = ViewModelProvider(this, factory)[ShowViewModel::class.java]

        viewModel.getPopularShow().observe(viewLifecycleOwner, {tvShows ->
            showLoading(false)
            adapterShow.setData(tvShows)
            binding.rvShow.apply {
                adapter = adapterShow
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