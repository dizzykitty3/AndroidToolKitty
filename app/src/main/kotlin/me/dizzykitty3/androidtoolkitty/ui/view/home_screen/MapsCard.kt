package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.util.IntentUtil
import me.dizzykitty3.androidtoolkitty.ui.component.ClearInput
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard

private const val TAG = "MapsCard"

@Preview
@Composable
fun MapsCard() {
    CustomCard(
        icon = Icons.Outlined.Place,
        titleRes = R.string.google_maps
    ) {
        val context = LocalContext.current
        var latitude by remember { mutableStateOf("") }
        var longitude by remember { mutableStateOf("") }

        Row {
            OutlinedTextField(
                value = latitude,
                onValueChange = { latitude = it },
                label = { Text(stringResource(R.string.latitude)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onClickOpenGoogleMapsButton(latitude, longitude, context) }
                ),
                trailingIcon = {
                    ClearInput(text = latitude) {
                        latitude = ""
                    }
                },
            )

            OutlinedTextField(
                value = longitude,
                onValueChange = { longitude = it },
                label = { Text(stringResource(R.string.longitude)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onClickOpenGoogleMapsButton(latitude, longitude, context) }
                ),
                trailingIcon = {
                    ClearInput(text = longitude) {
                        longitude = ""
                    }
                },
            )
        }

        TextButton(
            onClick = { onClickOpenGoogleMapsButton(latitude, longitude, context) }
        ) {
            Text(text = stringResource(R.string.open_google_maps))

            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(id = R.string.open_google_maps),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

private fun onClickOpenGoogleMapsButton(latitude: String, longitude: String, context: Context) {
    if (latitude.isBlank() || longitude.isBlank()) return

    IntentUtil.openGoogleMaps(latitude, longitude, context)
    Log.d(TAG, "onClickOpenGoogleMapsButton")
}