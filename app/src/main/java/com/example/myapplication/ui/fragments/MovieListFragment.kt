package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.models.Movie
import com.example.myapplication.ui.adapters.MainAdapter
import com.example.myapplication.ui.viewmodels.ItemListViewModel
import com.example.myapplication.ui.viewmodels.factories.MovieViewModelFactory
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.coroutines.flow.collectLatest

class MovieListFragment(val index : Int) : Fragment(R.layout.fragment_list) , MainAdapter.MovieItemClick{
    lateinit var listAdapter:MainAdapter
    private lateinit var viewModel: ItemListViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupViewModel()
    }

    private fun setupViewModel(){
        viewModel = ViewModelProvider(this, MovieViewModelFactory(index))[ItemListViewModel::class.java]
        lifecycleScope.launchWhenCreated {
            viewModel.getList().collectLatest {
                listAdapter.submitData(it)
            }
        }
        listAdapter.addLoadStateListener { loadState->
            if (loadState.refresh is LoadState.Loading){
                progressBar.visibility = View.VISIBLE
            }
            else{
                progressBar.visibility = View.GONE

                // getting the error
                val error = when {
                    loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                    loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                    loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                    else -> null
                }
                error?.let {
                    Toast.makeText(this@MovieListFragment.requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }

//        viewModel.loading.observe(viewLifecycleOwner, {
//            if (it)
//                progressBar.visibility = View.VISIBLE
//            else
//                progressBar.visibility = View.GONE
//        })
//        viewModel.pageLoading.observe(viewLifecycleOwner,{
//            if(it)
//                pagingProgress.visibility = View.VISIBLE
//            else
//                pagingProgress.visibility = View.GONE
//        })
    }

    private fun setupRecycler() = recyclerView.apply{
        listAdapter = MainAdapter(this@MovieListFragment)
        this.adapter = listAdapter
        this.layoutManager = LinearLayoutManager(this@MovieListFragment.requireContext())
    }

    override fun onClick( movie: Movie) {
        this.activity?.supportFragmentManager?.beginTransaction()?.apply {
            add(R.id.bottomContainerFragment,MovieDetailFragment(movie))
            addToBackStack("Detail")
            commit()
        }
    }
}