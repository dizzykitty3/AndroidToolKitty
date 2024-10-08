package me.dizzykitty3.androidtoolkitty.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_MOBILE
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.NetworkCapabilities
import androidx.annotation.CheckResult
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext

object NetworkUtil {
    private const val STATE_CODE_UNKNOWN = 0
    const val STATE_CODE_WIFI = 1
    const val STATE_CODE_MOBILE = 2
    const val STATE_CODE_OFFLINE = 3
    private var connectivityManager =
        appContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Suppress("DEPRECATION")
    @SuppressLint("NewApi")
    @CheckResult
    fun networkState(): Int {
        if (!OSVersion.android6()) {
            val activeNetwork = connectivityManager.activeNetworkInfo ?: return STATE_CODE_OFFLINE
            return when (activeNetwork.type) {
                TYPE_WIFI -> STATE_CODE_WIFI
                TYPE_MOBILE -> STATE_CODE_MOBILE
                else -> STATE_CODE_UNKNOWN
            }
        }

        val activeNetwork = connectivityManager.activeNetwork ?: return STATE_CODE_OFFLINE
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return STATE_CODE_UNKNOWN

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> STATE_CODE_WIFI
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> STATE_CODE_MOBILE
            else -> STATE_CODE_UNKNOWN
        }
    }
}