package me.dizzykitty3.androidtoolkitty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.dizzykitty3.androidtoolkitty.common.ui.theme.MyApplicationTheme
import me.dizzykitty3.androidtoolkitty.common.util.ClipboardUtils
import me.dizzykitty3.androidtoolkitty.view.layout.MainLayout
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

class MainActivity : ComponentActivity() {
    private var isAutoClearClipboard = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Actions.init(this)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MainLayout()
            }
        }
        Actions.debugLog("MainActivity onCreate called")
    }

    override fun onResume() {
        super.onResume()
        getSettingsSharedPreferences()
        Actions.debugLog("MainActivity onResume called")
    }

    private fun getSettingsSharedPreferences() {
        isAutoClearClipboard = SettingsViewModel(this).getIsAutoClearClipboard()
        Actions.debugLog("MainActivity settings sp got")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus and isAutoClearClipboard) // Clipboard operations require window focus
            ClipboardUtils(this).clearClipboard()
    }

    override fun onStop() {
        super.onStop()
        currentFocus?.clearFocus() // Collapses keyboard
        Actions.debugLog("MainActivity focus cleared")
    }
}