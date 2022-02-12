package com.example.myapplication.ui.fragments

import android.content.ComponentName
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.models.Music
import com.example.myapplication.services.MusicService
import com.example.myapplication.ui.adapters.MusicAdapter
import com.example.myapplication.ui.viewmodels.MusicViewModel
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.now_playing_fragment.*


class MusicFragment:Fragment(R.layout.fragment_list),MusicAdapter.MusicItemClick ,
    ServiceConnection {
    lateinit var viewModel: MusicViewModel
    lateinit var adapter : MusicAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val intentService = Intent(requireContext(), MusicService::class.java)
        activity?.bindService(intentService, this, BIND_AUTO_CREATE)
        activity?.startService(intentService)
        viewModel = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
        viewModel.getSongs(this.requireContext())
        setUpRecycler()
        viewModel.musicList.observe(viewLifecycleOwner,{
            adapter.list = it as List<Music>
        })
    }

    private fun setUpRecycler() = recyclerView.apply {
        this@MusicFragment.adapter = MusicAdapter(this@MusicFragment)
        adapter = this@MusicFragment.adapter
        layoutManager = LinearLayoutManager(this@MusicFragment.requireContext())
    }

    override fun onClick(index: Int) {
        viewModel.setIndex(index)
        this@MusicFragment.activity?.supportFragmentManager?.beginTransaction()?.apply {
            add(R.id.mainFragment, NowPlayingFragment())
            addToBackStack("NowPlaying")
            commit()
        }
//        val intent = Intent(this@MusicFragment.activity,PlayerActivity::class.java)
//        intent.putExtra("music",music)
//        this.startActivity(intent)
    }

    override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
        val binder = p1 as MusicService.MyBinder
        viewModel.setService(binder.currentService())
    }

    override fun onServiceDisconnected(p0: ComponentName?) {
        viewModel.setService(null)
    }
}