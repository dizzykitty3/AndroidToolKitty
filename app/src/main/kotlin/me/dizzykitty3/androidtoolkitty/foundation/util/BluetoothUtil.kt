package me.dizzykitty3.androidtoolkitty.foundation.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

object BluetoothUtil {
    private var bluetoothManager: BluetoothManager =
        app.applicationContext.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

    fun bluetoothAdapter(): BluetoothAdapter {
        return bluetoothManager.adapter
    }
}