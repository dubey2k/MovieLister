package com.example.myapplication.ui.viewmodels

import android.content.Context
import android.database.Cursor
import android.graphics.Bitmap
import android.provider.BaseColumns
import android.provider.MediaStore
import android.provider.MediaStore.Audio.AudioColumns
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.PlayerState
import com.example.myapplication.data.models.Music
import com.example.myapplication.services.MusicService
import java.util.*
import kotlin.collections.ArrayList


class MusicViewModel : ViewModel() {
    private var musicService: MusicService? = null

    var musicList: MutableLiveData<MutableList<Music>?> = MutableLiveData(null)
        private set
    var curIndex: MutableLiveData<Int> = MutableLiveData(0)
        private set
    var playerState: MutableLiveData<PlayerState> = MutableLiveData(PlayerState.INIT)
        private set
    var curPos: MutableLiveData<Int> = MutableLiveData(0)

    var timer: Timer = Timer()

    fun setIndex(index: Int){
        curIndex.value = index
        playerState.value = PlayerState.INIT
        playNPause()
    }
    fun getCurMusic() = musicList.value!![curIndex.value!!]

    fun playNPause() {
        when (playerState.value) {
            PlayerState.PLAYING -> {
                playerState.value = PlayerState.PAUSED
                timer.cancel()
                musicService!!.mediaPlayer!!.pause()
            }
            PlayerState.PAUSED -> {
                playerState.value = PlayerState.PLAYING
                musicService!!.mediaPlayer!!.start()
                timer = Timer()
                timer.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        curPos.postValue(musicService!!.mediaPlayer!!.currentPosition)
                    }
                }, 1000, 1000)
            }
            PlayerState.INIT -> {
                timer.cancel()
                playerState.value = PlayerState.PLAYING
                musicService!!.createMediaPlayer(musicList.value!![curIndex.value!!])
                timer = Timer()
                timer.scheduleAtFixedRate(object : TimerTask() {
                    override fun run() {
                        curPos.postValue(musicService!!.mediaPlayer!!.currentPosition)
                    }
                }, 1000, 1000)
            }
        }
    }

    fun skipNext() {
        if (musicList.value!!.size - 1 > curIndex.value!!) {
            curIndex.value = curIndex.value!! + 1
            playerState.value = PlayerState.INIT
            playNPause()
        }
    }

    fun skipPrevious() {
        if (curIndex.value!! > 0) {
            curIndex.value = curIndex.value!! - 1
            playerState.value = PlayerState.INIT
            playNPause()
        }
    }

    fun getSongs(context: Context) {
        val songs: ArrayList<Music> = ArrayList()
        val cursor = makeSongCursor(context)
        if (cursor != null && cursor.moveToFirst()) {
            do {
                songs.add(getSongFromCursorImpl(cursor))
            } while (cursor.moveToNext())
        }
        cursor?.close()
        musicList.value = songs
    }

    private fun getSongFromCursorImpl(cursor: Cursor): Music {
        val id = cursor.getInt(0)
        val title = cursor.getString(1)
        val trackNumber = cursor.getInt(2)
        val year = cursor.getInt(3)
        val duration = cursor.getLong(4)
        val data = cursor.getString(5)
        val dateModified = cursor.getLong(6)
        val albumId = cursor.getInt(7)
        val albumName = cursor.getString(8)
        val artistId = cursor.getInt(9)
        val artistName = cursor.getString(10)
        return Music(
            id,
            title,
            trackNumber,
            year,
            duration,
            data,
            dateModified,
            albumId,
            albumName,
            artistId,
            artistName
        )
    }

    fun makeSongCursor(
        context: Context,
    ): Cursor? {
        val BASE_PROJECTION = arrayOf(
            BaseColumns._ID,  // 0
            AudioColumns.TITLE,  // 1
            AudioColumns.TRACK,  // 2
            AudioColumns.YEAR,  // 3
            AudioColumns.DURATION,  // 4
            AudioColumns.DATA,  // 5
            AudioColumns.DATE_MODIFIED,  // 6
            AudioColumns.ALBUM_ID,  // 7
            AudioColumns.ALBUM,  // 8
            AudioColumns.ARTIST_ID,  // 9
            AudioColumns.ARTIST
        )


        val selection = AudioColumns.IS_MUSIC + "!= 0"
        return try {
            context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                BASE_PROJECTION, selection, null, null
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun setService(currentService: MusicService?) {
        musicService = currentService
    }

    fun showNotification(bMap:Bitmap) {
        musicService!!.showNotification(getCurMusic(),bMap)
    }

    fun setMediaPlayerPosition(progress:Int) {
        musicService!!.mediaPlayer!!.seekTo(progress)
    }
}