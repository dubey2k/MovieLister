package com.example.myapplication.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.constants.CAT_POPULAR
import com.example.myapplication.ui.adapters.MainAdapter
import com.example.myapplication.ui.viewmodels.ViewModelFactory
import com.example.myapplication.ui.viewmodels.mainViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch

class listFragment(val type: String) : Fragment(R.layout.fragment_list), MainAdapter.IPagingCallback {
    lateinit var listAdapter:MainAdapter
    lateinit var viewModel: mainViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupViewModel()
        lifecycleScope.launch {
            viewModel.fetchData(type)
        }
    }

    override fun startPaging() {
        lifecycleScope.launch {
            viewModel.fetchData(viewModel.selCategory)
        }
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this,ViewModelFactory()).get(mainViewModel::class.java)

        val b = if(type.equals(CAT_POPULAR)) viewModel.movieListPopular else viewModel.movieListUpcoming
        b.observe(viewLifecycleOwner, {
            val pos:Int = listAdapter.itemCount
            listAdapter.items = it
            listAdapter.notifyItemRangeChanged(pos, it.size - pos)
        })
        viewModel.pageLoading.observe(viewLifecycleOwner,{
            if(it)
                pagingProgress.visibility = View.VISIBLE
            else
                pagingProgress.visibility = View.GONE
        })
    }

    private fun setupRecycler() = recyclerView.apply{
        listAdapter = MainAdapter(this@listFragment)
        this.adapter = listAdapter
        this.layoutManager = LinearLayoutManager(this@listFragment.requireContext())
    }
}