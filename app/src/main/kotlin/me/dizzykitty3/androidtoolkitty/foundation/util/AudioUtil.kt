package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.Context
import android.media.AudioManager
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext

object AudioUtil {
    private var audioManager: AudioManager =
        appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    fun volume(): Int =
        audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)


    fun maxVolumeIndex(): Int =
        audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)


    fun setVolume(volume: Int) =
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)

    fun setVolume(volume: Double) =
        setVolume(volume.toInt())

    fun setVolumePercentage(volume: Int) {
        if (volume in 0..100) {
            setVolume(maxVolumeIndex() * 0.01 * volume)
        }
    }
}