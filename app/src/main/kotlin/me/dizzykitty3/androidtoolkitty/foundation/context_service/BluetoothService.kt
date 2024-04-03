package me.dizzykitty3.androidtoolkitty.foundation.context_service

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context

class BluetoothService(private val context: Context) {
    private lateinit var bluetoothManager: BluetoothManager

    private fun bluetoothService() {
        bluetoothManager = context.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
    }

    fun bluetoothAdapter(): BluetoothAdapter {
        bluetoothService()
        return bluetoothManager.adapter
    }
}