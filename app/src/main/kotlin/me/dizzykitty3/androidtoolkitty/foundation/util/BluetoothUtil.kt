package me.dizzykitty3.androidtoolkitty.foundation.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.appContext

object BluetoothUtil {
    private var bluetoothManager =
        appContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    fun bluetoothAdapter(): BluetoothAdapter =
        bluetoothManager.adapter
}