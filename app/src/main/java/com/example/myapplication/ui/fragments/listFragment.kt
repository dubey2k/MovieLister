package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.constants
import com.example.myapplication.ui.adapters.MainAdapter
import com.example.myapplication.ui.viewmodels.ViewModelFactory
import com.example.myapplication.ui.viewmodels.mainViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.launch

class listFragment: Fragment(R.layout.list_fragment) {
    lateinit var viewModel: mainViewModel
    private lateinit var mainAdapter:MainAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        categoryGrp.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.popularItem -> onCategorySel(constants.MOVIES_CATEGORY.POPULAR)
                R.id.upcomingItem -> onCategorySel(constants.MOVIES_CATEGORY.UPCOMING)
            }
        }

        viewModel = ViewModelProvider(this,ViewModelFactory()).get(mainViewModel::class.java)

        viewModel.movieList.observe(viewLifecycleOwner, Observer {
            mainAdapter.items = it
            mainAdapter.notifyDataSetChanged()
        })
        viewModel.loading.observe(viewLifecycleOwner,{
            if(it)
                progressBar.visibility = View.VISIBLE;
            else
                progressBar.visibility = View.GONE;
        })

        onCategorySel(constants.MOVIES_CATEGORY.POPULAR)
    }
    private fun onCategorySel(selCategory: constants.MOVIES_CATEGORY){
        lifecycleScope.launch {
            when(selCategory){
                constants.MOVIES_CATEGORY.UPCOMING-> viewModel.getUpcomingMovies()
                constants.MOVIES_CATEGORY.POPULAR->viewModel.getPopularMovies()
            }
        }
    }
    private fun setupRecycler() = recyclerView.apply{
        mainAdapter = MainAdapter()
        this.adapter = mainAdapter
        this.layoutManager = LinearLayoutManager(this@listFragment.requireContext())
    }
}