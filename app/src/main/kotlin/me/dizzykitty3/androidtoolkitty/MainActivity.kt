package me.dizzykitty3.androidtoolkitty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Image
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
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
import me.dizzykitty3.androidtoolkitty.domain.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.ui.AppNavHost
import me.dizzykitty3.androidtoolkitty.ui.theme.AppTheme
import me.dizzykitty3.androidtoolkitty.ui.viewmodel.SettingsViewModel
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            settingsViewModel = hiltViewModel<SettingsViewModel>()
            val snackbarHostState = remember { SnackbarHostState() }
            val scope = rememberCoroutineScope()
            val bottomAppBar = settingsViewModel.settings.value.bottomAppBar

            AppTheme(dynamicColor = settingsViewModel.settings.value.dynamicColor) {
                Scaffold(Modifier.fillMaxSize(),
                    snackbarHost = {
                        if (bottomAppBar)
                            SnackbarHost(hostState = snackbarHostState)
                    },
                    bottomBar = {
                        if (bottomAppBar)
                            BottomAppBar(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.primary,
                            ) {
                                Text(
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Center,
                                    text = "Bottom app bar",
                                )
                            }
                    },
                    floatingActionButton = {
                        if (bottomAppBar)
                            ExtendedFloatingActionButton(
                                text = { Text("Show snackbar") },
                                icon = { Icon(Icons.Filled.Image, contentDescription = "") },
                                onClick = {
                                    scope.launch {
                                        snackbarHostState.showSnackbar("Snackbar")
                                    }
                                }
                            )
                    }
                ) { innerPadding ->
                    AppNavHost(
                        Modifier.padding(
                            top = innerPadding.calculateTopPadding(),
                            bottom = innerPadding.calculateBottomPadding()
                        ),
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
            if (SettingsSharedPref.enabledAutoSetMediaVolume) {
                if (this@MainActivity.isHeadsetConnected()) {
                    Timber.i("Set media volume automatically: cancelled: BT headset connected")
                } else {
                    Timber.i("Set media volume automatically")
                    window.decorView.autoSetMediaVolume(
                        SettingsSharedPref.autoSetMediaVolume,
                        settingsViewModel
                    )
                }
            }
        }
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
}