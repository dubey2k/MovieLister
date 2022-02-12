package com.example.myapplication.ui.fragments

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.myapplication.R
import com.example.myapplication.constants.IMAGE_BASE
import com.example.myapplication.data.models.Movie
import com.example.myapplication.ui.viewmodels.MovieDetailViewModel
import kotlinx.android.synthetic.main.movie_detail_fragment.*

class MovieDetailFragment(val movie: Movie) : Fragment(R.layout.movie_detail_fragment) {
    lateinit var viewmodel: MovieDetailViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewmodel = ViewModelProvider(this).get(MovieDetailViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewmodel.getMovie(movie.id)
        }
        viewmodel.movie.observe(viewLifecycleOwner, {
            if (it != null) {
                Glide.with(this).load(IMAGE_BASE + movie.backdrop_path).into(movieDetailBackImg)
                movieDetailTitle.text = it.title
                movieDetailReleaseText.text = "Release Date : "+it.release_date
                movieDetailOverview.text = it.overview
                movieDetailVote.text = it.vote_average.toString()
                movieDetailBackBtn.setOnClickListener {
                    this.activity?.onBackPressed()
                }
            }else if(viewmodel.loading.value!!){
                Toast.makeText(this@MovieDetailFragment.requireContext(),"Error while fetching Data!!",Toast.LENGTH_LONG).show()
            }
        })
        viewmodel.loading.observe(viewLifecycleOwner, {
            if (it) {
                movieDetailProgress.visibility = View.VISIBLE
                mainConstraintMovieDetail.visibility = View.GONE
            } else {
                movieDetailProgress.visibility = View.GONE
                mainConstraintMovieDetail.visibility = View.VISIBLE
            }
        })
    }
}

