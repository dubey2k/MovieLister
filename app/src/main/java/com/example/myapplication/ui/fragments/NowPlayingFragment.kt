package com.example.myapplication.ui.fragments

import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.myapplication.PlayerState
import com.example.myapplication.R
import com.example.myapplication.getImgFromUri
import com.example.myapplication.getTimmedString
import com.example.myapplication.ui.viewmodels.MusicViewModel
import kotlinx.android.synthetic.main.now_playing_fragment.*

class NowPlayingFragment : Fragment(R.layout.now_playing_fragment) {
    companion object {
        lateinit var viewModel: MusicViewModel
        lateinit var bitmap: Bitmap
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[MusicViewModel::class.java]
        setViews()
        viewModel.playNPause()
        viewModel.showNotification(bitmap)
    }

    private fun setViews() {
        setLayouts()
        playPauseBtn.setOnClickListener {
            viewModel.playNPause()
        }
        skipNextBtn.setOnClickListener {
            viewModel.skipNext()
        }
        skipPreviousBtn.setOnClickListener {
            viewModel.skipPrevious()
        }
        viewModel.curPos.observe(viewLifecycleOwner, {
            val music = viewModel.getCurMusic()
            currentTime.text = getTimmedString((it ?: 0) / 1000)
            seekBarNowPlaying.progress = ((it * 100) / music.duration).toInt()
        })
        viewModel.playerState.observe(viewLifecycleOwner, {
            setLayouts()
            if (it == PlayerState.PAUSED) {
                playPauseBtn.setImageResource(R.drawable.ic_play)
            } else {
                playPauseBtn.setImageResource(R.drawable.ic_pause)
            }
        })
        seekBarNowPlaying.max = 100
        seekBarNowPlaying.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                    val music = viewModel.getCurMusic()
                    if (p0 != null && p2) {
                        viewModel.setMediaPlayerPosition(((music.duration * p1) / 100).toInt())
                    }
                }

                override fun onStartTrackingTouch(p0: SeekBar?) {
                }

                override fun onStopTrackingTouch(p0: SeekBar?) {
                }

            }
        )

    }

    private fun setLayouts() {
        val music = viewModel.getCurMusic()
        setBgImage(Uri.parse(music.data))
        musicTitle.text = music.title
        musicDuration.text = getTimmedString((music.duration / 1000).toInt())
        authorText.text = music.artistName
    }

    private fun setBgImage(uri: Uri) {
        bitmap = getImgFromUri(uri, requireContext())
        Glide.with(this).load(bitmap).into(musicCoverImg)
    }
}