package com.example.myapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.myapplication.MoviePagingSource
import com.example.myapplication.data.models.Movie
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.services.MovieAPI
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
class ItemListViewModel(private val index: Int) : ViewModel() {
    private val repository: MovieAPI = RetrofitInstance.getRetroInstance()

    val list = mutableListOf(
        "/3/movie/popular",
        "/3/movie/upcoming"
    )

    //loading values
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var pageLoading: MutableLiveData<Boolean> = MutableLiveData()

    fun getList(): Flow<PagingData<Movie>> {

        return Pager(config = PagingConfig(pageSize = 20,enablePlaceholders = true),pagingSourceFactory = {
            MoviePagingSource(
                repository,
                list[index]
            )
        }).flow.cachedIn(viewModelScope)
    }
}