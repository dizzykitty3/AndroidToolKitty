package me.dizzykitty3.androidtoolkitty.foundation.ui_component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.foundation.utils.IntentService

@Composable
fun CustomSystemSettingsButton(
    settingType: String,
    buttonText: Int
) {
    Button(
        onClick = { IntentService.openSystemSettings(settingType) },
        elevation = ButtonDefaults.buttonElevation(1.dp)
    ) {
        Text(text = stringResource(id = buttonText))

        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}