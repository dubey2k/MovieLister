package com.example.myapplication.network

import android.util.Log
import com.example.myapplication.services.MovieAPI
import okhttp3.Interceptor
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {

    companion object {
        val baseURL = "https://api.themoviedb.org"

        fun getRetroInstance(): MovieAPI {
            return Retrofit.Builder()
                .baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieAPI::class.java)

        }
    }
}