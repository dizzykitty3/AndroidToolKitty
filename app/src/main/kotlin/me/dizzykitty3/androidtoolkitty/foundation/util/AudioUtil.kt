package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.Context
import android.media.AudioManager
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext

object AudioUtil {
    private var audioManager: AudioManager =
        appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun volume(): Int {
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    fun maxVolumeIndex(): Int {
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }

    fun setVolume(volume: Int) =
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)

    fun setVolume(volume: Double) = setVolume(volume.toInt())
}