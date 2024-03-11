package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomIconAndTextPadding
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.ui.component.CustomStaticCard
import me.dizzykitty3.androidtoolkitty.util.Utils.openUrl
import me.dizzykitty3.androidtoolkitty.util.Utils.showToast

@Suppress("SpellCheckingInspection")
@Composable
fun AboutCard() {
    val c = LocalContext.current
    CustomStaticCard(title = c.getString(R.string.about)) {
        val sourceCodeUrl = "https://github.com/dizzykitty3/android_toolkitty"
        CustomSpacerPadding()
        Row {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null
            )
            CustomIconAndTextPadding()
            Text(
                text = "dizzykitty3",
            )
        }
        CustomSpacerPadding()
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Code,
                contentDescription = null
            )
            TextButton(
                onClick = {
                    showToast("All help is greatly welcomed! :D")
                    openUrl(sourceCodeUrl)
                }
            ) {
                Text(
                    text = c.getString(R.string.source_code_on_github)
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
            Text(
                text = "Version 20240309"
            )
        }
    }
}