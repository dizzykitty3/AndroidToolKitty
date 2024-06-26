package me.dizzykitty3.androidtoolkitty.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.media.AudioManager
import android.view.View
import com.google.android.gms.location.LocationServices
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.snackbar
import timber.log.Timber
import java.time.LocalTime

object AudioUtil {
    private var audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val volume: Int
        get() = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    val maxVolumeIndex: Int
        get() = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    fun setVolume(volume: Int, isAutoSetVolume: Boolean = false) {
        val showSystemUI = SettingsSharedPref.showSystemVolumeUI
        if (showSystemUI || isAutoSetVolume)
            audioManager.setStreamVolume(
                AudioManager.STREAM_MUSIC,
                volume,
                AudioManager.FLAG_SHOW_UI
            )
        else
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0)

    }

    fun setVolume(volume: Double) = setVolume(volume.toInt())

    private fun View.setVolumeByPercentage(percentage: Int) {
        val indexedVolume = (maxVolumeIndex * 0.01 * percentage).toInt()
        Timber.d("current = $volume, target = $indexedVolume")
        if (percentage in 0..100 && (volume != indexedVolume)) {
            setVolume(indexedVolume, true)
            Timber.d("setVolumeAutomatically true")
            this.snackbar(R.string.volume_changed_auto)
        }
        Timber.d("setVolumeAutomatically false, current == target")
    }

    @SuppressLint("MissingPermission")
    fun View.autoSetMediaVolume(percentage: Int) {
        if (percentage !in 0..100) return

        if (SettingsSharedPref.enableLocation) {
            if (PermissionUtil.noLocationPermission(appContext)) return
            var distance: Float
            val currentLocation = LocationServices.getFusedLocationProviderClient(appContext)
            currentLocation.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    distance =
                        LocationUtil.calculateDistanceToSaved(location.latitude, location.longitude)
                    Timber.d("latitude = ${location.latitude}")
                    Timber.d("longitude = ${location.longitude}")
                    Timber.d("distance = $distance")
                    this.setVolumeByPercentage(if (distance >= 200f) 0 else percentage)
                }
            }
            return
        }

        when (LocalTime.now().hour) {
            6 -> this.setVolumeByPercentage(percentage)
            in 7..17 -> this.setVolumeByPercentage(0)
            in 18..22 -> this.setVolumeByPercentage(percentage)
            else -> this.setVolumeByPercentage(25)
        }
    }
}