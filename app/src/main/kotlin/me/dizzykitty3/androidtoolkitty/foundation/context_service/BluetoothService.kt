package me.dizzykitty3.androidtoolkitty.foundation.context_service

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import me.dizzykitty3.androidtoolkitty.ToolKittyApp.Companion.app

object BluetoothService {
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