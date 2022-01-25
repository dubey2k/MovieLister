package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.models.Movie

class ViewModelFactory(): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(mainViewModel::class.java)){
            return mainViewModel() as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}