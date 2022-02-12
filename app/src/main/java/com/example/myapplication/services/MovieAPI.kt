package com.example.myapplication.services

import com.example.myapplication.data.models.MovieDetail
import com.example.myapplication.data.models.MovieResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface MovieAPI {

    @GET("{url}?api_key=eb7786e5fa9f76e923010eb722ee0173")
    fun getMovieList(@Path("url", encoded = true) url:String,@Query("page") page: Int?): Call<MovieResponse>

    @GET("/3/movie/{movie_id}?api_key=eb7786e5fa9f76e923010eb722ee0173")
    fun getMovieFromID(@Path("movie_id") id: Int): Call<MovieDetail>
}