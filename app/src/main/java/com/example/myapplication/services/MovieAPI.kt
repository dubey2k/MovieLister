package com.example.myapplication.services

import com.example.myapplication.data.models.MovieResponse
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryName

interface MovieAPI {
    @GET("/3/movie/popular?api_key=eb7786e5fa9f76e923010eb722ee0173")
    fun getPopularList(@Query("page") page: Int?): Call<MovieResponse>

    @GET("/3/movie/upcoming?api_key=eb7786e5fa9f76e923010eb722ee0173")
    fun getUpcomingList(@Query("page") page:Int?): Call<MovieResponse>

    @GET("/3/search/movie?api_key=eb7786e5fa9f76e923010eb722ee0173")
    fun searchMovies(@Query("query") query:String?,@Query("page") page:Int?): Call<MovieResponse>
}