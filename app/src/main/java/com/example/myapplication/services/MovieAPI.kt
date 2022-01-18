package com.example.myapplication.services

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET

interface MovieAPI {
    @GET("/3/movie/popular?api_key=eb7786e5fa9f76e923010eb722ee0173")
    fun getMovieList(): Call<JsonObject>
}