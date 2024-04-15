package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard

@Composable
fun PermissionRequestCard() {
    CustomCard(
        title = (R.string.permission_request),
        icon = Icons.Outlined.Shield
    ) {
        Text(text = stringResource(id = R.string.permission_request))
    }
}