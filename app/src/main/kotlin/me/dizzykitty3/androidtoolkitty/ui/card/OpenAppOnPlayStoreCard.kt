package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.util.Utils.openCertainAppOnPlayStore
import me.dizzykitty3.androidtoolkitty.util.Utils.openUrl

@Composable
fun OpenAppOnPlayStoreCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.open_app_on_google_play)) {
        var packageName by remember { mutableStateOf("") }
        val linkUrl = "https://support.google.com/admob/answer/9972781"
        OutlinedTextField(
            value = packageName,
            onValueChange = { packageName = it },
            label = { Text(c.getString(R.string.package_name_or_search)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    openCertainAppOnPlayStore(packageName)
                }
            )
        )
        CustomSpacerPadding()
        Row {
            Text(
                text = buildAnnotatedString {
                    append(c.getString(R.string.whats))
                    append(" ")
                }
            )
            Row(
                modifier = Modifier.clickable(
                    onClick = { openUrl(linkUrl) }
                )
            ) {
                Text(
                    text = c.getString(R.string.package_name),
                    textDecoration = TextDecoration.Underline
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = c.getString(R.string.content_description_link_icon_whats_package_name)
                )
            }
        }
    }
}