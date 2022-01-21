package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.MOVIES_CATEGORY
import com.example.myapplication.R
import com.example.myapplication.ui.adapters.MainAdapter
import com.example.myapplication.ui.viewmodels.ViewModelFactory
import com.example.myapplication.ui.viewmodels.mainViewModel
import kotlinx.android.synthetic.main.list_fragment.*
import kotlinx.coroutines.launch

class listFragment: Fragment(R.layout.list_fragment), MainAdapter.IPagingCallback {
    lateinit var viewModel: mainViewModel
    private lateinit var mainAdapter:MainAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        categoryGrp.setOnCheckedChangeListener { radioGroup, i ->
            when(i){
                R.id.popularItem -> onCategorySel(MOVIES_CATEGORY.POPULAR)
                R.id.upcomingItem -> onCategorySel(MOVIES_CATEGORY.UPCOMING)
            }
        }

        viewModel = ViewModelProvider(this,ViewModelFactory()).get(mainViewModel::class.java)

        viewModel.curList.observe(viewLifecycleOwner, {
            val pos:Int = mainAdapter.itemCount
            mainAdapter.items = it
            mainAdapter.notifyItemRangeChanged(pos, it.size - pos)
        })
        viewModel.loading.observe(viewLifecycleOwner,{
            if(it)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })
        viewModel.pageLoading.observe(viewLifecycleOwner,{
            if(it)
                pagingProgress.visibility = View.VISIBLE
            else
                pagingProgress.visibility = View.GONE
        })

        onCategorySel(MOVIES_CATEGORY.POPULAR,true)
    }
    private fun onCategorySel(selCategory: MOVIES_CATEGORY,initial:Boolean?=false){
        lifecycleScope.launch {
            viewModel.fetchData(selCategory,initial)
        }
    }

    private fun setupRecycler() = recyclerView.apply{
        mainAdapter = MainAdapter(this@listFragment)
        this.adapter = mainAdapter
        this.layoutManager = LinearLayoutManager(this@listFragment.requireContext())
    }

    override fun startPaging() {
        lifecycleScope.launch {
            viewModel.fetchData(viewModel.selCategory)
        }
    }
}

