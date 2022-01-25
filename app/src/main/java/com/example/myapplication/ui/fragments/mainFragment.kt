package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.R
import com.example.myapplication.constants.CAT_POPULAR
import com.example.myapplication.constants.CAT_UPCOMING
import com.example.myapplication.ui.adapters.viewPagerAdapter
import com.example.myapplication.ui.viewmodels.ViewModelFactory
import com.example.myapplication.ui.viewmodels.mainViewModel
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch

class mainFragment : Fragment(R.layout.main_fragment) {
    lateinit var viewModel: mainViewModel
    val cat: List<String> = listOf(CAT_POPULAR, CAT_UPCOMING)
    private lateinit var viewPagerAdapter: viewPagerAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpViewPager()
        TabLayoutMediator(
            tabLayout,
            viewPager
        ) { tab, position -> tab.text = cat.get(position) }.attach()



        viewModel = ViewModelProvider(this, ViewModelFactory()).get(mainViewModel::class.java)
        viewModel.catList = cat;

        viewModel.loading.observe(viewLifecycleOwner, {
            if (it)
                progressBar.visibility = View.VISIBLE
            else
                progressBar.visibility = View.GONE
        })
    }

    private fun onCategorySel(selCategory: String, initial: Boolean? = false) {
        lifecycleScope.launch {
            viewModel.fetchData(selCategory, initial)
        }
    }

    private fun setUpViewPager() = viewPager.apply {
        viewPagerAdapter = viewPagerAdapter(cat,this@mainFragment.getActivity() as FragmentActivity)
        this.adapter = viewPagerAdapter
    }
}

