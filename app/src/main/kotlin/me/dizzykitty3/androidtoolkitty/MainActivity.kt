package me.dizzykitty3.androidtoolkitty

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.util.AudioUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.ui.theme.AppTheme
import me.dizzykitty3.androidtoolkitty.ui.view.AppNavigationHost
import timber.log.Timber
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var continuation: Continuation<Unit>? = null
    private var isContinuationResumed = false
    private var isAutoClearClipboard = false

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        enableEdgeToEdge()
        setContent {
            AppTheme(dynamicColor = SettingsSharedPref.dynamicColor) {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppNavigationHost()
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")

        isContinuationResumed = false

        CoroutineScope(Dispatchers.Main).launch {
            isAutoClearClipboard = withContext(Dispatchers.IO) {
                SettingsSharedPref.autoClearClipboard
            }
            suspendCancellableCoroutine { cont ->
                continuation = cont
            }
            if (isAutoClearClipboard) {
                val cleared = ClipboardUtil.check()
                if (cleared) {
                    SnackbarUtil.snackbar(
                        window.decorView,
                        R.string.clipboard_cleared_automatically
                    )
                    Timber.i("Clipboard cleared automatically")
                }
            }
            if (SettingsSharedPref.autoSetMediaVolume != -1) AudioUtil.autoSetMediaVolume(
                SettingsSharedPref.autoSetMediaVolume
            )
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Timber.d("onWindowFocusChanged")
        if (hasFocus and !isContinuationResumed) { // Clipboard operations require window focus
            continuation?.resume(Unit)
            isContinuationResumed = true
            Timber.i("continuation resumed")
        }
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
        if (SettingsSharedPref.collapseKeyboard) {
            currentFocus?.clearFocus() // To collapse keyboard
            Timber.d("focus cleared")
        }
    }
}