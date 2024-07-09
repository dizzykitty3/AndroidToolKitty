package me.dizzykitty3.androidtoolkitty.domain.utils

import android.app.Activity
import android.content.Context
import androidx.annotation.CheckResult
import androidx.core.app.ActivityCompat
import me.dizzykitty3.androidtoolkitty.data.BT
import me.dizzykitty3.androidtoolkitty.data.BT_ADMIN
import me.dizzykitty3.androidtoolkitty.data.BT_CONNECT
import me.dizzykitty3.androidtoolkitty.data.COARSE_LOCATION
import me.dizzykitty3.androidtoolkitty.data.FINE_LOCATION
import me.dizzykitty3.androidtoolkitty.data.GRANTED

/**
 * Remember to use Activity Context to check/request permission.
 */
object PermissionUtil {
    /**
     * @return true if the app does NOT have the required permissions, false otherwise.
     */
    @CheckResult
    fun Context.noBluetoothPermission(): Boolean =
        if (OSVersion.a12()) this.check(BT_CONNECT)
        else this.check(BT) || this.check(BT_ADMIN)

    @CheckResult
    fun Context.noLocationPermission(): Boolean =
        if (OSVersion.a12()) this.check(COARSE_LOCATION) && this.check(FINE_LOCATION)
        else this.check(FINE_LOCATION)

    private fun Context.check(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(this, permission) != GRANTED

    fun Context.requestBluetoothPermission() {
        this.request(if (OSVersion.a12()) arrayOf(BT_CONNECT) else arrayOf(BT, BT_ADMIN))
    }

    fun Context.requestLocationPermission() {
        this.request(if (OSVersion.a12()) arrayOf(COARSE_LOCATION) else arrayOf(FINE_LOCATION))
    }

    private fun Context.request(permission: Array<String>) =
        ActivityCompat.requestPermissions(this as Activity, permission, 1)
}