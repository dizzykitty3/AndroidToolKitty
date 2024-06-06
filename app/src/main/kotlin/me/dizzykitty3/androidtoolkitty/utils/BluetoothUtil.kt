package me.dizzykitty3.androidtoolkitty.utils

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext

object BluetoothUtil {
    private var bluetoothManager =
        appContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    @Suppress("DEPRECATION")
    fun bluetoothAdapter(): BluetoothAdapter? =
        if (OSVersion.api31()) bluetoothManager.adapter else BluetoothAdapter.getDefaultAdapter()

    @SuppressLint("MissingPermission")
    fun isHeadsetConnected(): Boolean =
        if (bluetoothAdapter() == null) false else bluetoothAdapter()?.getProfileConnectionState(
            BluetoothProfile.HEADSET
        ) == BluetoothAdapter.STATE_CONNECTED
}