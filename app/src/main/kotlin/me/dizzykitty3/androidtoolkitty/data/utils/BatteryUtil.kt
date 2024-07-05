package me.dizzykitty3.androidtoolkitty.data.utils

import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext

object BatteryUtil {
    fun batteryLevel(): Int {
        val batteryIntent = appContext.registerReceiver(
            null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        )
        val level = batteryIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryIntent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1

        if (level == -1 || scale == -1) return -1

        return (level.toFloat() / scale.toFloat() * 100).toInt()
    }
}