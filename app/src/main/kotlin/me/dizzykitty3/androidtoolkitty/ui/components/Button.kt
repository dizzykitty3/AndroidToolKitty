package me.dizzykitty3.androidtoolkitty.ui.components

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.domain.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.openSystemSettings

@Composable
fun SystemSettingButton(
    settingType: String,
    @StringRes text: Int
) {
    val view = LocalView.current

    TextButton(
        onClick = {
            view.hapticFeedback()
            view.context.openSystemSettings(settingType)
        }
    ) {
        Text(text = stringResource(id = text))

        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = stringResource(id = text),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

/**
 * TrailingIcon param of a @Composable TextField, to clear the user input.
 */
@Composable
fun ClearInput(
    text: String,
    onClick: () -> Unit
) {
    if (text.isNotEmpty()) {
        IconButton(onClick = onClick) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.clear_input),
            )
        }
    }
}