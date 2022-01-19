package com.example.myapplication.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.ui.adapters.MainAdapter
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.data.models.Movie
import com.example.myapplication.network.RetrofitInstance
import com.example.myapplication.ui.fragments.listFragment
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch
import retrofit2.*

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val listFrag = listFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.listFragment,listFrag)
            commit()
        }
    }


}