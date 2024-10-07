package me.dizzykitty3.androidtoolkitty.utils

import android.content.Context
import android.media.AudioManager
import android.view.View
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import kotlin.math.roundToInt

object AudioUtil {
    private var audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val mediaVolume: Int
        get() = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    val voiceCallVolume: Int
        get() = audioManager.getStreamVolume(AudioManager.STREAM_VOICE_CALL)

    val maxMediaVolumeIndex: Int
        get() = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    val maxVoiceCallVolumeIndex: Int
        get() = audioManager.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL)

    private fun setMediaVolume(volume: Int) =
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            volume,
            AudioManager.FLAG_SHOW_UI
        )

    fun setVoiceCallVolume(volume: Int) =
        audioManager.setStreamVolume(
            AudioManager.STREAM_VOICE_CALL,
            volume,
            AudioManager.FLAG_SHOW_UI
        )

    fun View.setVolume(volume: Int) {
        setMediaVolume(volume)
        this.showSnackbar(R.string.volume_changed)
    }

    fun View.setVolume(volume: Double) {
        setMediaVolume(volume.roundToInt())
        this.showSnackbar(R.string.volume_changed)
    }
}