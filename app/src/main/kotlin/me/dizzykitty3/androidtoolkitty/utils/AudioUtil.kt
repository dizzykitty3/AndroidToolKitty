package me.dizzykitty3.androidtoolkitty.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.media.AudioManager
import android.view.View
import com.google.android.gms.location.LocationServices
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noLocationPermission
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import timber.log.Timber
import java.time.LocalTime
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

    private fun setMediaVolume(volume: Int, isAutoSetVolume: Boolean = false) =
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            volume,
            if (isAutoSetVolume) AudioManager.FLAG_SHOW_UI else 0
        )

    fun setVoiceCallVolume(volume: Int) =
        audioManager.setStreamVolume(
            AudioManager.STREAM_VOICE_CALL,
            volume,
            AudioManager.FLAG_SHOW_UI
        )

    private fun View.setMediaVolumeByPercentage(percentage: Int) {
        val indexedVolume = (maxMediaVolumeIndex * 0.01 * percentage).roundToInt()
        Timber.d("current = $mediaVolume, target = $indexedVolume")

        if (percentage in 0..100 && (mediaVolume != indexedVolume)) {
            setMediaVolume(indexedVolume, true)
            Timber.d("setVolumeAutomatically true")
            this.showSnackbar(R.string.volume_changed_auto)
        }
        Timber.d("setVolumeAutomatically false, current == target")
    }

    @SuppressLint("MissingPermission")
    fun View.autoSetMediaVolume(percentage: Int) {
        if (percentage !in 0..100) return
        if (this.context.noLocationPermission()) return

        var distance: Float
        val currentLocation = LocationServices.getFusedLocationProviderClient(appContext)

        currentLocation.lastLocation.addOnSuccessListener { location: Location? ->
            if (location != null) {
                distance = LocationUtil.getDistance(location.latitude, location.longitude)
                val notAtHome = distance.isNotAtHome()
                if (notAtHome) Timber.d("distance > 50 meters")
                this.showSnackbar(this.context.getString(R.string.distance_msg, distance.toInt()))
                this.setMediaVolumeByPercentage(if (notAtHome) 0 else if (LocalTime.now().hour !in 6..22) 20 else percentage)
            } else {
                this.showSnackbar("get location error")
            }
        }
    }

    private fun Float.isNotAtHome(): Boolean = this >= 50f

    fun View.setVolume(volume: Int) {
        setMediaVolume(volume)
        this.showSnackbar(R.string.volume_changed)
    }

    fun View.setVolume(volume: Double) {
        setMediaVolume(volume.roundToInt())
        this.showSnackbar(R.string.volume_changed)
    }
}