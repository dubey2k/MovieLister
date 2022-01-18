package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.adapters.MainAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.models.Movie
import com.example.myapplication.network.RetrofitInstance
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter:MainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupRecycler()
        lifecycleScope.launch {
            binding.progressBar.visibility = View.VISIBLE;
            var list = RetrofitInstance.api.getMovieList().awaitResponse().body()
            val gson = Gson()
            var movies : MutableList<Movie> = mutableListOf()
            Log.d("TAG", "onCreate: "+ list?.get("results"))
            (list?.get("results") as JsonArray).forEach {
                var testModel:Movie = gson.fromJson(it, Movie::class.java)
                movies.add(testModel)
            }
            mainAdapter.items = movies
            mainAdapter.notifyDataSetChanged()
            binding.progressBar.visibility = View.GONE;
        }
    }

    private fun setupRecycler() = binding.recyclerView.apply{
        mainAdapter = MainAdapter()
        this.adapter = mainAdapter
        this.layoutManager = LinearLayoutManager(this@MainActivity)
    }
}