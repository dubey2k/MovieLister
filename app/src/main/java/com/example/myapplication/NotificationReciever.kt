package com.example.myapplication

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.myapplication.ui.fragments.NowPlayingFragment

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            ApplicationClass.PREVIOUS -> NowPlayingFragment.viewModel.skipPrevious()
            ApplicationClass.PLAY -> NowPlayingFragment.viewModel.playNPause()
            ApplicationClass.NEXT -> NowPlayingFragment.viewModel.skipNext()
//            ApplicationClass.EXIT ->{
//                exitApplication()
//            }
        }
    }
}