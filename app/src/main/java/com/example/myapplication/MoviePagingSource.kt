package com.example.myapplication

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.myapplication.data.models.Movie
import com.example.myapplication.data.models.MovieResponse
import com.example.myapplication.services.MovieAPI
import retrofit2.awaitResponse

class MoviePagingSource(val movieAPI: MovieAPI,val url: String) : PagingSource<Int, Movie>() {
    private val FIRST_PAGING_INDEX = 1
    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try{
            val page = params.key ?: FIRST_PAGING_INDEX
            val data: MovieResponse
            val response = movieAPI.getMovieList(url, page).awaitResponse().body()
            data = response!!
            return LoadResult.Page(data = data.results,prevKey = null,nextKey = page + 1)
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}