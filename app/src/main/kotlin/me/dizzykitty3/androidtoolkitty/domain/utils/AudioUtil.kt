package me.dizzykitty3.androidtoolkitty.domain.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.media.AudioManager
import android.view.View
import com.google.android.gms.location.LocationServices
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.domain.utils.PermissionUtil.noLocationPermission
import me.dizzykitty3.androidtoolkitty.domain.utils.SnackbarUtil.showSnackbar
import timber.log.Timber
import java.time.LocalTime

object AudioUtil {
    private var audioManager = appContext.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    val volume: Int
        get() = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

    val maxVolumeIndex: Int
        get() = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)

    fun setVolume(volume: Int, isAutoSetVolume: Boolean = false) =
        audioManager.setStreamVolume(
            AudioManager.STREAM_MUSIC,
            volume,
            if (isAutoSetVolume) AudioManager.FLAG_SHOW_UI else 0
        )

    private fun View.setVolumeByPercentage(percentage: Int) {
        val indexedVolume = (maxVolumeIndex * 0.01 * percentage).toInt()
        Timber.d("current = $volume, target = $indexedVolume")

        if (percentage in 0..100 && (volume != indexedVolume)) {
            setVolume(indexedVolume, true)
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
                if (SettingsSharedPref.devMode) this.showSnackbar(
                    "${this.context.getString(R.string.dev_mode_message)} ${
                        this.context.getString(R.string.distance)
                    } = $distance"
                )
                this.setVolumeByPercentage(if (notAtHome) 0 else if (LocalTime.now().hour !in 6..22) 20 else percentage)
            }
        }
    }

    private fun Float.isNotAtHome(): Boolean = this >= 50f
}