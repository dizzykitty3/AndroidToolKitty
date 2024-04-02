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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomItalicText
import me.dizzykitty3.androidtoolkitty.common.util.IntentUtils
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog
import me.dizzykitty3.androidtoolkitty.common.util.UrlUtils

@Composable
fun UrlCard() {
    CustomCard(
        icon = Icons.Outlined.Link,
        title = R.string.url
    ) {
        val context = LocalContext.current

        var url by remember { mutableStateOf("") }

        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text(stringResource(R.string.url)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Ascii
            ),
            keyboardActions = KeyboardActions(
                onDone = { onClickVisitUrlButton(context, url) }
            ),
            supportingText = {
                Text(
                    text = buildAnnotatedString {
                        append(text = stringResource(R.string.url_input_hint_1))
                        CustomItalicText(" www. ")
                        append(text = stringResource(R.string.url_input_hint_2))
                        CustomItalicText(" .com ")
                        append(text = stringResource(R.string.url_input_hint_3))
                        CustomItalicText(" .net ")
                        append(text = stringResource(R.string.url_input_hint_4))
                    }
                )
            },
            prefix = {
                Text(text = "https://")
            },
            suffix = {
                Text(text = UrlUtils.getUrlSuffix(url))
            }
        )

        TextButton(
            onClick = { onClickVisitUrlButton(context, url) }
        ) {
            Text(text = stringResource(R.string.visit))

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