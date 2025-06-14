package me.dizzykitty3.androidtoolkitty.utils

import android.app.Activity
import android.content.Context
import androidx.annotation.CheckResult
import androidx.core.app.ActivityCompat
import me.dizzykitty3.androidtoolkitty.BT
import me.dizzykitty3.androidtoolkitty.BT_ADMIN
import me.dizzykitty3.androidtoolkitty.BT_CONNECT
import me.dizzykitty3.androidtoolkitty.GRANTED

/**
 * Remember to use Activity Context to check/request permission.
 */
object PermissionUtil {
    /**
     * @return true if the app does NOT have the required permissions, false otherwise.
     */
    @CheckResult
    fun Context.noBluetoothPermission(): Boolean =
        if (OSVersion.android12()) this.check(BT_CONNECT)
        else this.check(BT) || this.check(BT_ADMIN)

    private fun Context.check(permission: String): Boolean =
        ActivityCompat.checkSelfPermission(this, permission) != GRANTED

    fun Context.requestBluetoothPermission() =
        this.request(
            if (OSVersion.android12()) arrayOf(BT_CONNECT)
            else arrayOf(BT, BT_ADMIN)
        )

    private fun Context.request(permission: Array<String>) =
        ActivityCompat.requestPermissions(this as Activity, permission, 1)
}