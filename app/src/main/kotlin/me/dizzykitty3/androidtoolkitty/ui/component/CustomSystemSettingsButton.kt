package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.util.Utils.onOpenSystemSettings

@Composable
fun CustomSystemSettingsButton(settingType: String, buttonText: String) {
    Button(
        onClick = {
            onOpenSystemSettings(settingType)
        }
    ) {
        Text(
            text = buttonText
        )
    }
}