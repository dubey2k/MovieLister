package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.constants
import com.example.myapplication.data.models.Movie
import com.example.myapplication.data.repositories.movieRepository

class mainViewModel : ViewModel() {
    val repository: movieRepository = movieRepository.getInstance()
    var movies : MutableList<Movie> = mutableListOf()
        private set
    var movieList : MutableLiveData<List<Movie>> = MutableLiveData<List<Movie>>()
        private set

    var loading: MutableLiveData<Boolean> = MutableLiveData()

    var selCategory: constants.MOVIES_CATEGORY = constants.MOVIES_CATEGORY.POPULAR

    suspend fun getPopularMovies(){
        selCategory = constants.MOVIES_CATEGORY.POPULAR
        loading.value = true
        movies = repository.getPopularMovies().value as MutableList<Movie>
        movieList.value = movies
        loading.value = false
    }
    suspend fun getUpcomingMovies(){
        selCategory = constants.MOVIES_CATEGORY.UPCOMING
        loading.value = true
        movies = repository.getUpcomingMovies().value as MutableList<Movie>
        movieList.value = movies
        loading.value = false
    }
}