package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.Context
import android.media.AudioManager
import android.util.Log
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext
import java.time.LocalTime

private const val TAG = "AudioUtil"

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

    private fun setVolumePercentage(volume: Int) {
        val indexedVolume = (maxVolumeIndex() * 0.01 * volume).toInt()
        Log.d(TAG, "current = ${volume()}, target = $indexedVolume")
        if (volume in 0..100 && (volume() != indexedVolume)) {
            setVolume(indexedVolume)
        }
    }

    fun autoSetMediaVolume() =
        when (LocalTime.now().hour) {
            in 6..7 -> setVolumePercentage(60)
            in 8..17 -> setVolumePercentage(0)
            in 18..22 -> setVolumePercentage(60)
            else -> setVolumePercentage(25)
        }
}