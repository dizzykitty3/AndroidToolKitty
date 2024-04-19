package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.Context
import android.media.AudioManager
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

object AudioUtil {
    private lateinit var audioManager: AudioManager

    private fun audioService() {
        audioManager = app.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    @JvmStatic
    fun volume(): Int {
        audioService()
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    @JvmStatic
    fun maxVolumeIndex(): Int {
        audioService()
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }

    @JvmStatic
    fun setVolume(volume: Int) {
        audioService()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)
    }

    @JvmStatic
    fun setVolume(volume: Double) {
        audioService()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume.toInt(), AudioManager.FLAG_SHOW_UI)
    }
}