package me.dizzykitty3.androidtoolkitty.utils

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import androidx.annotation.CheckResult
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noBluetoothPermission

object BluetoothUtil {
    private var bluetoothManager =
        appContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    val bluetoothAdapter: BluetoothAdapter?
        get() = if (OSVersion.android12())
            bluetoothManager.adapter
        else
            BluetoothAdapter.getDefaultAdapter()

    @CheckResult
    fun Context.isHeadsetConnected(): Boolean {
        if (this.noBluetoothPermission()) return false

        return if (bluetoothAdapter == null)
            false
        else
            bluetoothAdapter?.getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothAdapter.STATE_CONNECTED
    }

    @CheckResult
    fun Context.headsetNotConnected(): Boolean = !this.isHeadsetConnected()
}