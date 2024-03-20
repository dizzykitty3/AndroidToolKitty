package me.dizzykitty3.androidtoolkitty.common.util

import android.content.Context
import android.media.AudioManager

class AudioUtils(private val context: Context) {
    private lateinit var audioManager: AudioManager

    private fun getSystemAudioService() {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    fun getVolume(): Int {
        getSystemAudioService()
        return audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
    }

    fun getMaxVolumeIndex(): Int {
        getSystemAudioService()
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }

    fun setVolume(volume: Int) {
        getSystemAudioService()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)
    }
}