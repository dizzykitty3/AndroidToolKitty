package me.dizzykitty3.androidtoolkitty.utils

import android.content.Context
import android.media.AudioManager
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext
import timber.log.Timber
import java.time.LocalTime

object AudioUtil {
    private var audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val volume: Int
        get() = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    val maxVolumeIndex: Int
        get() = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    fun setVolume(volume: Int) =
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)

    fun setVolume(volume: Double) = setVolume(volume.toInt())

    private fun setVolumeByPercentage(percentage: Int) {
        val indexedVolume = (maxVolumeIndex * 0.01 * percentage).toInt()
        Timber.d("current = $volume, target = $indexedVolume")
        if (percentage in 0..100 && (volume != indexedVolume)) {
            setVolume(indexedVolume)
        }
    }

    fun autoSetMediaVolume(percentage: Int) {
        if (percentage in 0..100) when (LocalTime.now().hour) {
            in 6..7 -> setVolumeByPercentage(percentage)
            in 8..17 -> setVolumeByPercentage(0)
            in 18..22 -> setVolumeByPercentage(percentage)
            else -> setVolumeByPercentage(25)
        }
    }
}