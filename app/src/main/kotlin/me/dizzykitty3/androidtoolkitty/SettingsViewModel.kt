package me.dizzykitty3.androidtoolkitty

import android.content.Context
import androidx.lifecycle.ViewModel

class SettingsViewModel(context: Context) : ViewModel() {
    private val sharedPrefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun getIsAutoClearClipboard(): Boolean {
        Actions.debugLog("getIsAutoClearClipboard")
        return sharedPrefs.getBoolean(IS_AUTO_CLEAR_CLIPBOARD, false)
    }

    fun setIsAutoClearClipboard(isAutoClearClipboard: Boolean) {
        Actions.debugLog("setIsAutoClearClipboard = $isAutoClearClipboard")
        sharedPrefs.edit().putBoolean(IS_AUTO_CLEAR_CLIPBOARD, isAutoClearClipboard).apply()
    }

    companion object {
        private const val IS_AUTO_CLEAR_CLIPBOARD = "isAutoClearClipboard"
    }
}