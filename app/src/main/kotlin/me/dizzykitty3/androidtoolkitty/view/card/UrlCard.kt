package me.dizzykitty3.androidtoolkitty.view.card

import android.content.Context
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Link
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
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomItalicText
import me.dizzykitty3.androidtoolkitty.common.util.IntentUtils
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog
import me.dizzykitty3.androidtoolkitty.common.util.UrlUtils

@Composable
fun UrlCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.Link,
        title = c.getString(R.string.url),
        id = "card_url"
    ) {
        var url by remember { mutableStateOf("") }
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text(c.getString(R.string.url)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onClickVisitUrlButton(c, url)
                }
            ),
            supportingText = {
                Text(
                    text = buildAnnotatedString {
                        append(text = c.getString(R.string.url_input_hint_1))
                        CustomItalicText(" www. ")
                        append(text = c.getString(R.string.url_input_hint_2))
                        CustomItalicText(" .com ")
                        append(text = c.getString(R.string.url_input_hint_3))
                        CustomItalicText(" .net ")
                        append(text = c.getString(R.string.url_input_hint_4))
                    }
                )
            },
            prefix = {
                Text(
                    text = "https://"
                )
            },
            suffix = {
                Text(
                    text = UrlUtils.getUrlSuffix(url)
                )
            }
        )
        TextButton(
            onClick = {
                onClickVisitUrlButton(c, url)
            }
        ) {
            Text(
                text = c.getString(R.string.visit)
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

private fun onClickVisitUrlButton(c: Context, url: String) {
    if (url.isBlank()) return
    IntentUtils(c).openUrl(UrlUtils.processUrl(StringUtils.dropSpaces(url)))
    debugLog("onClickVisitButton")
}