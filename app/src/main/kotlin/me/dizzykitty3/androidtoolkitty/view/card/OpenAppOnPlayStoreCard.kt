package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextDecoration
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.util.IntentUtils

@Composable
fun OpenAppOnPlayStoreCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.Shop,
        title = c.getString(R.string.open_app_on_google_play)
    ) {
        var packageName by remember { mutableStateOf("") }
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
                    IntentUtils(c).openAppOnMarket(packageName)
                }
            )
        )
        CustomSpacerPadding()
        Row {
            val linkUrl = "https://support.google.com/admob/answer/9972781"
            Text(
                text = buildAnnotatedString {
                    append(c.getString(R.string.whats))
                    append(" ")
                }
            )
            Row(
                modifier = Modifier.clickable(
                    onClick = { IntentUtils(c).openUrl(linkUrl) }
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
        TextButton(
            onClick = {
                IntentUtils(c).openAppOnMarket(packageName)
            }
        ) {
            Text(
                text = c.getString(R.string.open_on_google_play)
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        TextButton(
            onClick = {
                IntentUtils(c).openAppOnMarket(packageName, false)
            }
        ) {
            Text(
                text = c.getString(R.string.open_on_other_markets)
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}