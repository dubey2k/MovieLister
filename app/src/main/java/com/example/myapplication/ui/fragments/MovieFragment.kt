package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.myapplication.R
import com.example.myapplication.constants.CAT_POPULAR
import com.example.myapplication.constants.CAT_UPCOMING
import com.example.myapplication.ui.adapters.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.movie_fragment.*

class MovieFragment : Fragment(R.layout.movie_fragment) {
    val cat: List<String> = listOf(CAT_POPULAR, CAT_UPCOMING)
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpViewPager()
        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab, position -> tab.text = cat[position] }.attach()
    }

    private fun setUpViewPager() = viewPager.apply {
        viewPagerAdapter = ViewPagerAdapter(cat,this@MovieFragment.getActivity() as FragmentActivity)
        this.adapter = viewPagerAdapter
    }
}

