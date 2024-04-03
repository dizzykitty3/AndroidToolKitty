package me.dizzykitty3.androidtoolkitty.foundation.context_service

import android.content.Context
import android.media.AudioManager

class AudioService(private val context: Context) {
    private lateinit var audioManager: AudioManager

    private fun audioService() {
        audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    fun maxVolumeIndex(): Int {
        audioService()
        return audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
    }

    fun setVolume(volume: Int) {
        audioService()
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, AudioManager.FLAG_SHOW_UI)
    }
}