package me.dizzykitty3.androidtoolkitty

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.domain.utils.AudioUtil.autoSetMediaVolume
import me.dizzykitty3.androidtoolkitty.domain.utils.BluetoothUtil.isHeadsetConnected
import me.dizzykitty3.androidtoolkitty.domain.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.domain.utils.DateUtil
import me.dizzykitty3.androidtoolkitty.domain.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.ui.AppNavHost
import me.dizzykitty3.androidtoolkitty.ui.screens.settings.model.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.ui.theme.AppTheme
import timber.log.Timber
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var continuation: Continuation<Unit>? = null
    private var continuationNotResumed = true
    private var isAutoClearClipboard = false

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            val settingsViewModel = hiltViewModel<SettingsViewModel>()

            AppTheme(dynamicColor = SettingsSharedPref.dynamicColor) {
                Scaffold(Modifier.fillMaxSize()) {
                    AppNavHost(settingsViewModel)
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
        continuationNotResumed = true
        CoroutineScope(Dispatchers.Main).launch {
            isAutoClearClipboard = withContext(Dispatchers.IO) {
                SettingsSharedPref.autoClearClipboard
            }
            suspendCancellableCoroutine { cont ->
                continuation = cont
            }
            if (isAutoClearClipboard) {
                if (ClipboardUtil.clear()) {
                    window.decorView.showSnackbar(R.string.clipboard_cleared_automatically)
                    Timber.i("Clipboard cleared automatically")
                }
            }
            if (SettingsSharedPref.enabledAutoSetMediaVolume && DateUtil.isNotWeekend) {
                if (this@MainActivity.isHeadsetConnected()) {
                    Timber.i("Set media volume automatically: cancelled: BT headset connected")
                } else {
                    Timber.i("Set media volume automatically")
                    window.decorView.autoSetMediaVolume(SettingsSharedPref.autoSetMediaVolume)
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Timber.d("onWindowFocusChanged")
        if (hasFocus and continuationNotResumed) { // Clipboard operations require window focus
            try {
                Timber.d("continuation resume start")
                continuation?.resume(Unit)
            } catch (e: IllegalStateException) {
                Timber.e(e)
            } finally {
                Timber.i("continuation resumed")
                continuationNotResumed = false
            }
        }
    }
}