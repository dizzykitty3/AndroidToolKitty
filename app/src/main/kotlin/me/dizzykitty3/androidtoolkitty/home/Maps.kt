package me.dizzykitty3.androidtoolkitty.home

import android.content.Context
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Place
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.checkOnGoogleMaps
import timber.log.Timber

@Composable
fun Maps() {
    Card(R.string.google_maps, Icons.Outlined.Place) {
        val view = LocalView.current
        val focus = LocalFocusManager.current
        val haptic = LocalHapticFeedback.current
        val focusRequester1 = remember { FocusRequester() }
        val focusRequester2 = remember { FocusRequester() }
        var latitude by remember { mutableStateOf("") }
        var longitude by remember { mutableStateOf("") }

        Row {
            OutlinedTextField(
                value = latitude,
                onValueChange = { latitude = it },
                label = { Text(stringResource(R.string.lat)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = dimensionResource(id = R.dimen.padding_spacer))
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
                    }
                },
            )

            OutlinedTextField(
                value = longitude,
                onValueChange = { longitude = it },
                label = { Text(stringResource(R.string.lng)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = dimensionResource(id = R.dimen.padding_spacer))
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
                    }
                },
            )
        }

        TextButton({
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            focus.clearFocus()
            view.context.onClickOpenGoogleMapsButton(latitude, longitude)
        }) {
            Text(stringResource(R.string.open_google_maps))

            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(R.string.open_google_maps),
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
            )
        }
    }
}

private fun Context.onClickOpenGoogleMapsButton(latitude: String, longitude: String) {
    if (latitude.isBlank() || longitude.isBlank()) return
    Timber.d("onClickOpenGoogleMapsButton")
    this.checkOnGoogleMaps(latitude, longitude)
}