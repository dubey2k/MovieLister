package com.example.myapplication.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.data.models.Movie

class ViewModelFactory(val index:Int): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ItemListViewModel::class.java)){
            return ItemListViewModel(index) as T
        }
        throw IllegalArgumentException ("UnknownViewModel")
    }

}