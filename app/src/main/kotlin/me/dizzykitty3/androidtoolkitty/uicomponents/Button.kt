package me.dizzykitty3.androidtoolkitty.uicomponents

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openSystemSettings

@Composable
fun SystemSettingButton(
    settingType: String,
    @StringRes text: Int,
) {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    TextButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        view.context.openSystemSettings(settingType)
    }) {
        Text(stringResource(text))

        Icon(
            imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
        )
    }
}

/**
 * TrailingIcon param of a @Composable TextField, to clear the user input.
 */
@Composable
fun ClearInput(input: String, onClick: () -> Unit) {
    if (input.isNotEmpty()) {
        IconButton(onClick) {
            Icon(
                imageVector = Icons.Outlined.Clear,
                contentDescription = stringResource(R.string.clear_input),
            )
        }
    }
}

@Composable
fun NavBackButton(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current
    IconButton(onClick = {
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        navController.popBackStack()
    }) {
        Icon(
            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
            contentDescription = stringResource(R.string.back),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5F)
        )
    }
}