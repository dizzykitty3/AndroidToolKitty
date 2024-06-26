package me.dizzykitty3.androidtoolkitty.utils

import android.app.Activity
import android.content.Context
import androidx.annotation.CheckResult
import androidx.core.app.ActivityCompat
import me.dizzykitty3.androidtoolkitty.BT
import me.dizzykitty3.androidtoolkitty.BT_ADMIN
import me.dizzykitty3.androidtoolkitty.BT_CONNECT
import me.dizzykitty3.androidtoolkitty.COARSE_LOCATION
import me.dizzykitty3.androidtoolkitty.FINE_LOCATION
import me.dizzykitty3.androidtoolkitty.GRANTED

/**
 * Remember to use Activity Context to check/request permission.
 */
object PermissionUtil {
    /**
     * @return true if the app does NOT have the required permissions, false otherwise.
     */
    @CheckResult
    fun noBluetoothPermission(context: Context): Boolean {
        return if (OSVersion.a12()) check(context, BT_CONNECT)
        else check(context, BT) || check(context, BT_ADMIN)
    }

    @CheckResult
    fun noLocationPermission(context: Context): Boolean =
        check(context, FINE_LOCATION) && check(context, COARSE_LOCATION)

    private fun check(context: Context, permission: String): Boolean =
        ActivityCompat.checkSelfPermission(context, permission) != GRANTED

    fun requestBluetoothPermission(context: Context) {
        request(context, if (OSVersion.a12()) arrayOf(BT_CONNECT) else arrayOf(BT, BT_ADMIN))
    }

    fun requestLocationPermission(context: Context) {
        request(context, if (OSVersion.a12()) arrayOf(COARSE_LOCATION) else arrayOf(FINE_LOCATION))
    }

    private fun request(context: Context, permission: Array<String>) =
        ActivityCompat.requestPermissions(context as Activity, permission, 1)
}