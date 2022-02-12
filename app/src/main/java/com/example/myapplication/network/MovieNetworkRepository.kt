package com.example.myapplication.network

import android.util.Log
import com.example.myapplication.data.models.MovieDetail
import retrofit2.awaitResponse

object MovieNetworkRepository {
    suspend fun getMovieFromID(id: Int): MovieDetail? {
        var response = RetrofitInstance.getRetroInstance().getMovieFromID(id).awaitResponse()
        Log.d("TAG", "getMovieFromID: "+ response.raw().request.url)
        return response.body()
    }
}