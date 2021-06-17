package com.toyibnurseha.themoviedb.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.toyibnurseha.themoviedb.R
import com.toyibnurseha.themoviedb.TabPagerAdapter
import com.toyibnurseha.themoviedb.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val titles = intArrayOf(
        R.string.movies,
        R.string.tv_show,
        R.string.watch_list
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupTab()
    }

    private fun setupTab() {
        val pagerAdapter = TabPagerAdapter(this)
        binding.viewPagerDetail.adapter = pagerAdapter
        TabLayoutMediator(binding.tabDetail, binding.viewPagerDetail) { tab, position ->
            tab.text = resources.getString(titles[position])
        }.attach()
    }

}