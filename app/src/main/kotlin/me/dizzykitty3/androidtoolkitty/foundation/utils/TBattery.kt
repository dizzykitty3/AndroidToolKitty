package me.dizzykitty3.androidtoolkitty.foundation.utils

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import me.dizzykitty3.androidtoolkitty.ToolKittyApp.Companion.app

object TBattery {
    fun batteryLevel(): Int {
        val batteryIntent = app.registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        if (level == -1 || scale == -1) return -1

        return (level.toFloat() / scale.toFloat() * 100).toInt()
    }
}