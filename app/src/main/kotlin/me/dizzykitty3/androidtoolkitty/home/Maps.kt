package me.dizzykitty3.androidtoolkitty.home

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.checkOnGoogleMaps
import timber.log.Timber
import kotlin.math.absoluteValue

@Composable
fun Maps() {
    Card(title = R.string.maps, icon = Icons.Outlined.Map) {
        val view = LocalView.current
        val focus = LocalFocusManager.current
        val haptic = LocalHapticFeedback.current
        val focusRequester1 = remember { FocusRequester() }
        val focusRequester2 = remember { FocusRequester() }
        val settingsSharedPref = remember { SettingsSharedPref }
        var latitude by remember { mutableStateOf(settingsSharedPref.latitude) }
        var longitude by remember { mutableStateOf(settingsSharedPref.longitude) }

        OutlinedTextField(
            value = latitude,
            onValueChange = {
                latitude = it
                settingsSharedPref.latitude = it
            },
            suffix = {
                Text(
                    latitude.getLatitudeSuffix(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                )
            },
            label = { Text(stringResource(R.string.latitude)) },
            supportingText = { Text(stringResource(R.string.latitude_description)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester1),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (longitude == "") {
                        focusRequester2.requestFocus()
                    } else {
                        focus.clearFocus()
                        view.context.onClickOpenGoogleMapsButton(latitude, longitude)
                    }
                }
            ),
            trailingIcon = {
                ClearInput(latitude) {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    latitude = ""
                    settingsSharedPref.latitude = ""
                }
            },
        )

        OutlinedTextField(
            value = longitude,
            onValueChange = {
                longitude = it
                settingsSharedPref.longitude = it
            },
            suffix = {
                Text(
                    longitude.getLongitudeSuffix(),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3F)
                )
            },
            label = { Text(stringResource(R.string.longitude)) },
            supportingText = { Text(stringResource(R.string.longitude_description)) },
            modifier = Modifier
                .fillMaxWidth()
                .focusRequester(focusRequester2),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (latitude == "") {
                        focusRequester1.requestFocus()
                    } else {
                        focus.clearFocus()
                        view.context.onClickOpenGoogleMapsButton(latitude, longitude)
                    }
                }
            ),
            trailingIcon = {
                ClearInput(longitude) {
                    haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                    longitude = ""
                    settingsSharedPref.longitude = ""
                }
            },
        )

        TextButton({
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            focus.clearFocus()
            view.context.onClickOpenGoogleMapsButton(latitude, longitude)
        }) {
            Text(stringResource(R.string.google_maps))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(R.string.google_maps),
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
            )
        }
    }
}

private fun Context.onClickOpenGoogleMapsButton(latitude: String, longitude: String) {
    if (latitude.isBlank() || longitude.isBlank()
        || latitude.toValidFloat().absoluteValue > 90
        || longitude.toValidFloat().absoluteValue > 180
    ) return

    Timber.d("onClickOpenGoogleMapsButton")
    this.checkOnGoogleMaps(latitude, longitude)
}

private fun String.getLatitudeSuffix(): String {
    val input = this.toValidFloat()
    if (input > 0F && input <= 90F) return "N"
    if (input < 0F && input >= -90F) return "S"
    return ""
}

private fun String.getLongitudeSuffix(): String {
    val input = this.toValidFloat()
    if (input > 0F && input <= 180F) return "E"
    if (input < 0F && input >= -180F) return "W"
    return ""
}

private fun String.toValidFloat(): Float = try {
    this.substringAfter("-").toFloat()
} catch (_: NumberFormatException) {
    -999F // error input
}