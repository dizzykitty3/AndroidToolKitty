package me.dizzykitty3.androidtoolkitty.ui.component

import android.content.Context
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.Utils

@Composable
fun SystemSettingsButton(settingType: String, buttonText: String) {
    val context: Context = LocalContext.current
    Button(
        onClick = {
            Utils.onOpenSystemSettings(context, settingType)
        }
    ) {
        Text(text = buttonText)
    }
}