package com.example.myapplication.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.fragments.ListFragment

class ViewPagerAdapter(val list:List<String>, fragActivity : FragmentActivity): FragmentStateAdapter(fragActivity) {
    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return ListFragment(position)
    }
}
