package me.dizzykitty3.androidtoolkitty.ui.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dagger.hilt.android.AndroidEntryPoint
import me.dizzykitty3.androidtoolkitty.CARD_1
import me.dizzykitty3.androidtoolkitty.CARD_10
import me.dizzykitty3.androidtoolkitty.CARD_11
import me.dizzykitty3.androidtoolkitty.CARD_12
import me.dizzykitty3.androidtoolkitty.CARD_2
import me.dizzykitty3.androidtoolkitty.CARD_3
import me.dizzykitty3.androidtoolkitty.CARD_4
import me.dizzykitty3.androidtoolkitty.CARD_5
import me.dizzykitty3.androidtoolkitty.CARD_6
import me.dizzykitty3.androidtoolkitty.CARD_7
import me.dizzykitty3.androidtoolkitty.CARD_8
import me.dizzykitty3.androidtoolkitty.CARD_9
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.datastore.LocalSettingsViewModel
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.theme.AppTheme
import me.dizzykitty3.androidtoolkitty.uicomponents.BaseCard
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomHideCardSettingSwitch
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding

@AndroidEntryPoint
class CustomizeHomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val viewModel: SettingsViewModel = hiltViewModel()
            val state by viewModel.settingsState.collectAsStateWithLifecycle()

            CompositionLocalProvider(LocalSettingsViewModel provides viewModel) {
                AppTheme(
                    dynamicColor = state.dynamicColor
                ) {
                    Scaffold(
                        containerColor = MaterialTheme.colorScheme.surfaceContainer,
                    ) { innerPadding ->
                        Box(
                            Modifier
                                .fillMaxSize()
                                .padding(
                                    start = innerPadding.calculateStartPadding(LocalLayoutDirection.current),
                                    end = innerPadding.calculateEndPadding(LocalLayoutDirection.current),
                                )
                        ) {
                            Screen(screenTitle = R.string.customize_home) {
                                CustomizeHomeComposable()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun CustomizeHomeComposable() {
    val vm = LocalSettingsViewModel.current
    val state by vm.settingsState.collectAsStateWithLifecycle()

    BaseCard(R.string.customize_home) {
        val haptic = LocalHapticFeedback.current

        val cardList = listOf(
            CARD_1, CARD_2, CARD_3, CARD_4, CARD_5, CARD_6,
            CARD_7, CARD_8, CARD_9, CARD_10, CARD_11, CARD_12
        )

        val cardTextMap = mapOf(
            CARD_1 to R.string.year_progress,
            CARD_2 to R.string.volume,
            CARD_3 to R.string.clipboard,
            CARD_4 to R.string.search,
            CARD_5 to R.string.system_shortcuts,
            CARD_6 to R.string.wheel_of_fortune,
            CARD_7 to R.string.bluetooth_devices,
            CARD_8 to R.string.codes_of_characters,
            CARD_9 to R.string.maps,
            CARD_10 to R.string.font_weight_test,
            CARD_11 to R.string.compose,
            CARD_12 to R.string.haptic_test
        )

        cardList.forEach { card ->
            CustomHideCardSettingSwitch(
                text = cardTextMap[card]!!,
                isChecked = state.cardShownStates[card] ?: true
            ) { newState ->
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                vm.saveShownState(card, newState)
            }
        }

        SpacerPadding()

        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                cardList.forEach { card -> vm.saveShownState(card, false) }
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.VisibilityOff,
                contentDescription = stringResource(R.string.hide_all_cards),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(stringResource(R.string.hide_all_cards))
        }

        Button(
            onClick = {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                cardList.forEach { card -> vm.saveShownState(card, true) }
            }
        ) {
            Icon(
                imageVector = Icons.Outlined.Visibility,
                contentDescription = stringResource(R.string.show_all_cards),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            SpacerPadding()
            Text(stringResource(R.string.show_all_cards))
        }
    }
}
