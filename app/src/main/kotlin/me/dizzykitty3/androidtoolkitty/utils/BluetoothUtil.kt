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

    fun bluetoothAdapter(): BluetoothAdapter =
        bluetoothManager.adapter

    @SuppressLint("MissingPermission")
    fun isHeadsetConnected(): Boolean =
        bluetoothAdapter().getProfileConnectionState(BluetoothProfile.HEADSET) == BluetoothAdapter.STATE_CONNECTED
}