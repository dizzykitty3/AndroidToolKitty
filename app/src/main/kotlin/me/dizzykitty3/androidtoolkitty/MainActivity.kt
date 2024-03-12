package me.dizzykitty3.androidtoolkitty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import me.dizzykitty3.androidtoolkitty.ui.layout.MainLayout
import me.dizzykitty3.androidtoolkitty.ui.theme.MyApplicationTheme
import me.dizzykitty3.androidtoolkitty.util.ClipboardUtils

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
    }

    override fun onResume() {
        super.onResume()
        getSettingsSharedPreferences()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus and isAutoClearClipboard)
            ClipboardUtils(this).clearClipboard()
    }

    override fun onStop() {
        super.onStop()
        currentFocus?.clearFocus()
        Actions.debugLog("focus cleared")
    }

    private fun getSettingsSharedPreferences() {
        isAutoClearClipboard = SettingsViewModel(this).getIsAutoClearClipboard()
    }
}

