package me.dizzykitty3.androidtoolkitty.foundation.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import me.dizzykitty3.androidtoolkitty.MainApp.Companion.app

object NetworkUtil {
    private const val STATE_CODE_UNKNOWN = 0
    const val STATE_CODE_WIFI = 1
    const val STATE_CODE_MOBILE = 2
    const val STATE_CODE_OFFLINE = 3
    private lateinit var connectivityManager: ConnectivityManager

    private fun connectivityService() {
        connectivityManager =
            app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    @JvmStatic
    fun networkState(): Int {
        connectivityService()
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