package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.models.MovieDetail
import com.example.myapplication.network.MovieNetworkRepository.getMovieFromID
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.services.MovieAPI

class MovieDetailViewModel : ViewModel() {
    var movie: MutableLiveData<MovieDetail> = MutableLiveData()
        private set
    var loading: MutableLiveData<Boolean> = MutableLiveData(false)
        private set

    suspend fun getMovie(id: Int) {
        loading.value = true
        movie.value = getMovieFromID(id)
        if (movie.value != null)
            loading.value = false
    }
}