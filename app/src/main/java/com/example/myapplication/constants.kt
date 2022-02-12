package com.example.myapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaMetadataRetriever
import android.net.Uri

object constants {
    val IMAGE_BASE = "https://image.tmdb.org/t/p/w500/"
    val API_KEY = "eb7786e5fa9f76e923010eb722ee0173"
    val CAT_POPULAR = "Popular"
    val CAT_UPCOMING = "Upcoming"
}
enum class PlayerState{
    INIT,
    PLAYING,
    PAUSED,
    FINISH
}
fun getImgFromUri(uri: Uri, context: Context): Bitmap {
    val mmr = MediaMetadataRetriever()
    mmr.setDataSource(context, uri)
    val rawArt: ByteArray? = mmr.embeddedPicture
    val bitmap = BitmapFactory.decodeByteArray(rawArt, 0, rawArt!!.size)
    return bitmap
}
fun getTimmedString(sec: Int):String {
    val seconds = sec % 60
    val minutes = sec / 60
    val hours = minutes / 60
    var time = ""
    if (hours > 0) {
        if (hours < 10)
            time += "0$hours:"
        else
            time += "$hours:"
    }
    if (minutes < 10)
        time += "0$minutes:"
    else
        time += "$minutes:"
    if (seconds < 10)
        time += "0$seconds"
    else
        time += "$seconds"
    return time
}