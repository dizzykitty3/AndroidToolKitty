package me.dizzykitty3.androidtoolkitty.foundation.util

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

object BluetoothUtil {
    private lateinit var bluetoothManager: BluetoothManager

    private fun bluetoothService() {
        bluetoothManager = app.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    }

    @JvmStatic
    fun bluetoothAdapter(): BluetoothAdapter {
        bluetoothService()
        return bluetoothManager.adapter
    }
}