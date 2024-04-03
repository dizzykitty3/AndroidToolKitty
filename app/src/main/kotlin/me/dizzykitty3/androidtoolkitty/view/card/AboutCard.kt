package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.context_service.IntentService
import me.dizzykitty3.androidtoolkitty.foundation.context_service.ToastService
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomCardNoIcon
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomDeveloperProfileLink
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomIconAndTextPadding
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomSpacerPadding

@Suppress("SpellCheckingInspection")
@Composable
fun AboutCard() {
    CustomCardNoIcon(
        title = R.string.about
    ) {
        CustomDeveloperProfileLink("dizzykitty3")
        CustomDeveloperProfileLink("HongjieCN")

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val context = LocalContext.current
            val sourceCodeUrl = "https://github.com/dizzykitty3/android_toolkitty"

            Icon(
                imageVector = Icons.Outlined.Code,
                contentDescription = null
            )
            CustomIconAndTextPadding()
            Row(
                modifier = Modifier.clickable {
                    ToastService(context).toast(context.getString(R.string.all_help_welcomed))
                    IntentService(context).openUrl(sourceCodeUrl)
                }
            ) {
                Text(
                    text = stringResource(R.string.source_code_on_github),
                    color = MaterialTheme.colorScheme.primary
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterVertically),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
        CustomSpacerPadding()
        Row {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = null
            )
            CustomIconAndTextPadding()
            Text(text = "${stringResource(R.string.version)} ${stringResource(R.string.version_number)}")
        }
    }
}