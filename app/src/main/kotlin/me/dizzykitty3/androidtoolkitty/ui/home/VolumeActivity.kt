package me.dizzykitty3.androidtoolkitty.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import dagger.hilt.android.AndroidEntryPoint
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.home.MediaVolume
import me.dizzykitty3.androidtoolkitty.theme.AppTheme
import me.dizzykitty3.androidtoolkitty.uicomponents.BaseCard
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.utils.AudioUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar

@AndroidEntryPoint
class VolumeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Scaffold(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ) { innerPadding ->
                    Box(
                        Modifier.padding(
                            start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                            end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                        )
                    ) {
                        Screen(screenTitle = R.string.volume) {
                            VolumeComposable()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun VolumeComposable() {
    BaseCard(R.string.media_volume) { MediaVolume(isHome = false) }
    BaseCard(R.string.voice_call_volume) { VoiceCallVolume() }
}

@Composable
private fun VoiceCallVolume() {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current
    OutlinedButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        val index = AudioUtil.maxVoiceCallVolumeIndex
        AudioUtil.setVoiceCallVolume(index)
        if (AudioUtil.voiceCallVolume == index) {
            view.showSnackbar(R.string.volume_changed)
        }
    }) { Text(stringResource(R.string.max_out_voice_call_volume)) }
}
