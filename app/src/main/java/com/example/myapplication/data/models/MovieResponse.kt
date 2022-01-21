package com.example.myapplication.data.models

data class MovieResponse(
    val results : List<Movie>,
    val page : Int,
    val total_pages : Int
)
