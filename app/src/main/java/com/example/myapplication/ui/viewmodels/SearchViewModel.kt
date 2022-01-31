package com.example.myapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.models.Movie
import com.example.myapplication.data.repositories.movieRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    private var searchJob: Job? = null
    val repository: movieRepository = movieRepository.getInstance()
    var searchList: MutableLiveData<MutableList<Movie>> = MutableLiveData<MutableList<Movie>>(
        mutableListOf()
    )
        private set

    //paging variables for popular movies
    var CUR_PAGE: Int = 1
    var TOTAL_PAGES: Int? = null
    var isLast: Boolean = false


    suspend fun searchMovie(text: String) {
        searchJob?.cancel()
        searchJob =
            viewModelScope.launch {
                delay(500)
                val res = repository.searchMovies(text,CUR_PAGE).value!!
                if (TOTAL_PAGES != null && res.page >= (TOTAL_PAGES!!)) {
                    isLast = true
                }
                if (!isLast)
                    CUR_PAGE++
                TOTAL_PAGES = res.total_pages
                searchList.value = (res.results?: mutableListOf<Movie>()) as MutableList<Movie>
            }
        Log.d("TAG", "searchMovie: scope:" + searchList.value)
    }
}