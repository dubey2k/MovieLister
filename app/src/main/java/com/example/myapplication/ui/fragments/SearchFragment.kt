package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.constants.CAT_POPULAR
import com.example.myapplication.constants.CAT_UPCOMING
import com.example.myapplication.ui.adapters.SearchViewAdapter
import com.example.myapplication.ui.adapters.viewPagerAdapter
import com.example.myapplication.ui.viewmodels.SearchViewModel
import com.example.myapplication.ui.viewmodels.ViewModelFactory
import com.example.myapplication.ui.viewmodels.mainViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.search_fragment.*
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.search_fragment) {
    lateinit var adapter: SearchViewAdapter
    lateinit var viewModel : SearchViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        setUpSearchView()
        setUpRecyclerView()

        viewModel.searchList.observe(viewLifecycleOwner,{
            adapter.items = it
            adapter.notifyDataSetChanged()
        })

    }

    private fun setUpRecyclerView() {
        searchRecycler.apply {
            this@SearchFragment.adapter = SearchViewAdapter(mutableListOf())
            adapter = this@SearchFragment.adapter
            layoutManager = LinearLayoutManager(this@SearchFragment.requireContext())
        }
    }

    private fun setUpSearchView() {
        val searchView =  searchViewMovies
        searchView.queryHint = "Search"
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                lifecycleScope.launch {
                    if(query != null && query.isNotEmpty())
                    viewModel.searchMovie(query)
                }
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                lifecycleScope.launch {
                    if(newText != null && newText.isNotEmpty())
                    viewModel.searchMovie(newText)
                }
                return true
            }
        })
    }

}

