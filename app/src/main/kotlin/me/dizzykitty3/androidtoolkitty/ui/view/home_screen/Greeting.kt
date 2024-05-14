package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.util.StringUtil
import me.dizzykitty3.androidtoolkitty.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.ui.component.Gradient

@Preview
@Composable
fun Greeting() {
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.clickable { showDialog = true }
    ) {
        Gradient(
            textToDisplay = StringUtil.greeting(),
            colors = listOf(
                MaterialTheme.colorScheme.primary,
                MaterialTheme.colorScheme.tertiaryContainer
            )
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            icon = {
                Icon(imageVector = Icons.Outlined.WbSunny, contentDescription = null)
            },
            title = {
                Text(text = "Auto set media volume")
            },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    CustomTip(id = R.string.under_development)
                    Row {
                        Column(modifier = Modifier.weight(0.5f)) {
                            Text(text = "8:00 AM - 5:59 PM")
                            Text(text = "6:00 PM - 10:59 PM")
                            Text(text = "11:00 PM - 5:59 AM")
                            Text(text = "6:00 PM - 7:59 AM")
                        }
                        Column(modifier = Modifier.weight(0.5f)) {
                            Text(text = "mute")
                            Text(text = "60%")
                            Text(text = "25%")
                            Text(text = "60%")
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    showDialog = false
                    SettingsSharedPref.autoSetMediaVolume = true
                }) {
                    Text(text = stringResource(id = R.string.turn_on))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog = false
                    SettingsSharedPref.autoSetMediaVolume = false
                }) {
                    Text(text = stringResource(id = R.string.turn_off))
                }
            }
        )
    }
}