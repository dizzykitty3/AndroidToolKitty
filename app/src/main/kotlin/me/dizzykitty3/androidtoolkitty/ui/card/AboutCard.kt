package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomIconAndTextPadding
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.ui.component.CustomStaticCard
import me.dizzykitty3.androidtoolkitty.util.Utils.openUrl

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
        Row {
            Icon(
                imageVector = Icons.Outlined.Code,
                contentDescription = null
            )
            CustomIconAndTextPadding()
            Text(
                text = c.getString(R.string.source_code_on_github),
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable {
                    openUrl(sourceCodeUrl)
                }
            )
        }
        CustomSpacerPadding()
        Row {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = null
            )
            CustomIconAndTextPadding()
            Text(
                text = "version 20240308"
            )
        }
    }
}