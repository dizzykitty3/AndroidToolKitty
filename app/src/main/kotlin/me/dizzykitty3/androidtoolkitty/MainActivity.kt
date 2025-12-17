package me.dizzykitty3.androidtoolkitty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.theme.AppTheme
import me.dizzykitty3.androidtoolkitty.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import timber.log.Timber
import java.util.concurrent.atomic.AtomicBoolean
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var continuation: Continuation<Unit>? = null
    private var continuationNotResumed = AtomicBoolean(true)
    private var isAutoClearClipboard = false
    private lateinit var settingsViewModel: SettingsViewModel

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        enableEdgeToEdge()
        setContent {
            settingsViewModel = hiltViewModel<SettingsViewModel>()
            isAutoClearClipboard = settingsViewModel.settings.value.autoClearClipboard

            AppTheme(
                dynamicColor = settingsViewModel.settings.value.dynamicColor
            ) {
                Scaffold(Modifier.fillMaxSize()) { innerPadding ->
                    AppNavHost(
                        Modifier.padding(top = innerPadding.calculateTopPadding()),
                        settingsViewModel
                    )
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Timber.d("onStart")
        continuationNotResumed.set(true)
        CoroutineScope(Dispatchers.Main).launch {
            suspendCancellableCoroutine { cont ->
                continuation = cont
            }
            if (isAutoClearClipboard) {
                if (ClipboardUtil.clear()) {
                    window.decorView.showSnackbar(R.string.clipboard_cleared_automatically)
                    Timber.i("Clipboard cleared automatically")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.d("onResume")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Timber.d("onWindowFocusChanged")
        if (hasFocus and continuationNotResumed.get()) { // Clipboard operations require window focus
            try {
                Timber.d("continuation resume start")
                continuation?.resume(Unit)
            } catch (e: IllegalStateException) {
                Timber.e(e)
            } finally {
                Timber.i("continuation resumed")
                continuationNotResumed.set(false)
            }
        }
    }

    override fun onPause() {
        super.onPause()
        Timber.d("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.d("onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.d("onDestroy")
    }
}