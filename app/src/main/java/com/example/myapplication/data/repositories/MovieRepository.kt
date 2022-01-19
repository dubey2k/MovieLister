package com.example.myapplication.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.data.models.Movie
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

    suspend fun getPopularMovies(): LiveData<List<Movie>>{
        var data: MutableLiveData<List<Movie>> = MutableLiveData()
            var response = RetrofitInstance.api.getPopularList().awaitResponse().body()
            val gson = Gson()
            var movies : MutableList<Movie> = mutableListOf()
            (response?.get("results") as JsonArray).forEach {
                var testModel:Movie = gson.fromJson(it, Movie::class.java)
                movies.add(testModel)
            }
        data.value = movies
        return data
    }

    suspend fun getUpcomingMovies(): LiveData<List<Movie>>{
        var data: MutableLiveData<List<Movie>> = MutableLiveData()
        var response = RetrofitInstance.api.getUpcomingList().awaitResponse().body()
        val gson = Gson()
        var movies : MutableList<Movie> = mutableListOf()
        (response?.get("results") as JsonArray).forEach {
            var testModel:Movie = gson.fromJson(it, Movie::class.java)
            movies.add(testModel)
        }
        data.value = movies
        return data
    }
}