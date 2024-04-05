package me.dizzykitty3.androidtoolkitty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.dizzykitty3.androidtoolkitty.foundation.context_service.ClipboardService
import me.dizzykitty3.androidtoolkitty.foundation.theme.MyApplicationTheme
import me.dizzykitty3.androidtoolkitty.foundation.utils.TLog.debugLog
import me.dizzykitty3.androidtoolkitty.view.NavHostLayout
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    private var isAutoClearClipboard = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme(
                dynamicColor = SettingsViewModel().getIsDynamicColor(this)
            ) {
                NavHostLayout()
            }
        }
        debugLog("MainActivity onCreate called")
    }

    override fun onResume() {
        super.onResume()
        getSettingsSharedPreferences()
        debugLog("MainActivity onResume called")
    }

    private fun getSettingsSharedPreferences() {
        isAutoClearClipboard = SettingsViewModel().getIsAutoClearClipboard(this)
        debugLog("MainActivity settings sp got")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus and isAutoClearClipboard) // Clipboard operations require window focus
            ClipboardService(this).clear()
    }

    override fun onStop() {
        super.onStop()
        currentFocus?.clearFocus() // To collapse keyboard
        debugLog("MainActivity focus cleared")
    }
}