package com.example.myapplication.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.models.Movie
import com.example.myapplication.data.models.MovieResponse
import com.example.myapplication.network.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.JsonArray
import retrofit2.awaitResponse

class movieRepository private constructor(){

    companion object {
        private var INSTANCE: movieRepository? = null
        fun getInstance(): movieRepository {
            if (INSTANCE == null) {
                INSTANCE = movieRepository()
            }
            return INSTANCE!!
        }
    }

    suspend fun getPopularMovies(page: Int): LiveData<MovieResponse>{
        val data: MutableLiveData<MovieResponse> = MutableLiveData()
        val response = RetrofitInstance.api.getPopularList(page).awaitResponse().body()
        data.value =  response
        return data
    }

    suspend fun getUpcomingMovies(page:Int): LiveData<MovieResponse>{
        val data: MutableLiveData<MovieResponse> = MutableLiveData()
        val response = RetrofitInstance.api.getUpcomingList(page).awaitResponse().body()
        data.value = response
        return data
    }
}