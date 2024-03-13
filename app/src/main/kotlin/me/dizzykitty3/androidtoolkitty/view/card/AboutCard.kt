package me.dizzykitty3.androidtoolkitty.view.card

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
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomIconAndTextPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomStaticCard
import me.dizzykitty3.androidtoolkitty.common.util.IntentUtils
import me.dizzykitty3.androidtoolkitty.common.util.ToastUtils

@Composable
fun AboutCard() {
    val c = LocalContext.current
    CustomStaticCard(
        title = c.getString(R.string.about)
    ) {
        CustomSpacerPadding()
        Row {
            Icon(
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = null
            )
            CustomIconAndTextPadding()
            Text(
                text = c.getString(R.string.developer)
            )
        }
        CustomSpacerPadding()
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            val sourceCodeUrl = "https://github.com/dizzykitty3/android_toolkitty"
            Icon(
                imageVector = Icons.Outlined.Code,
                contentDescription = null
            )
            TextButton(
                onClick = {
                    ToastUtils(c).showToast(c.getString(R.string.all_help_welcomed))
                    IntentUtils(c).openUrl(sourceCodeUrl)
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
                text = "Version 20240312"
            )
        }
    }
}