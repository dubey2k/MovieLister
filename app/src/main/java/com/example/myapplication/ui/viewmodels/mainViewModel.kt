package com.example.myapplication.ui.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.MOVIES_CATEGORY
import com.example.myapplication.data.models.Movie
import com.example.myapplication.data.models.MovieResponse
import com.example.myapplication.data.repositories.movieRepository

class mainViewModel : ViewModel() {
    val repository: movieRepository = movieRepository.getInstance()
    var movieListPopular : MutableLiveData<MutableList<Movie>> = MutableLiveData<MutableList<Movie>>(
        mutableListOf<Movie>())
        private set
    var movieListUpcoming : MutableLiveData<MutableList<Movie>> = MutableLiveData<MutableList<Movie>>(mutableListOf<Movie>())
        private set
    var curList : MutableLiveData<MutableList<Movie>> = MutableLiveData<MutableList<Movie>>(mutableListOf<Movie>())
        private set

    //loading values
    var loading: MutableLiveData<Boolean> = MutableLiveData()
    var pageLoading: MutableLiveData<Boolean> = MutableLiveData()

    //selected category
    var selCategory: MOVIES_CATEGORY = MOVIES_CATEGORY.POPULAR

    //paging variables for popular movies
    var CUR_PAGE_POP : Int = 1
    var TOTAL_PAGES_POP : Int ? = null
    var isLast_POP : Boolean = false

    //paging variables for upcoming movies
    var CUR_PAGE_UP : Int = 1
    var TOTAL_PAGES_UP : Int ? = null
    var isLast_UP : Boolean = false


    suspend fun fetchData(sel:MOVIES_CATEGORY,initial: Boolean? = false) {
        selCategory = sel
        if (initial != null && initial)
            loading.value = true
        else
            pageLoading.value = true

        if(selCategory == MOVIES_CATEGORY.POPULAR && !isLast_POP){
            val res = repository.getPopularMovies(CUR_PAGE_POP).value!!
            if(TOTAL_PAGES_POP != null && res.page >= (TOTAL_PAGES_POP!!)) {
                isLast_POP = true
            }
            if(!isLast_POP)
                CUR_PAGE_POP++
            TOTAL_PAGES_POP = res.total_pages
            val oldList = movieListPopular.value
            oldList?.addAll(res.results)
            movieListPopular.value = oldList
            curList.value = movieListPopular.value
        }else if(selCategory == MOVIES_CATEGORY.UPCOMING && !isLast_UP){
            val res = repository.getUpcomingMovies(CUR_PAGE_UP).value!!
            if(TOTAL_PAGES_UP != null && res.page >= (TOTAL_PAGES_UP!!))
                isLast_UP = true
            if(!isLast_UP)
                CUR_PAGE_UP++
            TOTAL_PAGES_UP = res.total_pages
            val oldList = movieListUpcoming.value
            oldList?.addAll(res.results)
            movieListUpcoming.value = oldList
            curList.value = movieListUpcoming.value
        }
        loading.value = false
        pageLoading.value = false
    }
}