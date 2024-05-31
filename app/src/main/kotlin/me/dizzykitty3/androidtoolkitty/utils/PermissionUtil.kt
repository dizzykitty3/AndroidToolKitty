package me.dizzykitty3.androidtoolkitty.utils

import android.app.Activity
import android.content.Context
import androidx.core.app.ActivityCompat

/**
 * Remember to use Activity Context to check/request permission.
 */
object PermissionUtil {
    /**
     * @return true if the app does NOT have the required permissions, false otherwise.
     */
    fun noBluetoothPermission(context: Context): Boolean {
        return if (OSVersion.android12())
            check(context, BT_CONNECT)
        else
            check(context, BT) || check(context, BT_ADMIN)
    }

    private fun check(context: Context, permission: String): Boolean {
        return ActivityCompat.checkSelfPermission(context, permission) != GRANTED
    }

    fun requestBluetoothPermission(context: Context) {
        if (OSVersion.android12()) {
            request(context, arrayOf(BT_CONNECT))
            return
        }
        request(context, arrayOf(BT, BT_ADMIN))
    }

    private fun request(context: Context, permission: Array<String>) {
        ActivityCompat.requestPermissions(context as Activity, permission, 1)
    }
}