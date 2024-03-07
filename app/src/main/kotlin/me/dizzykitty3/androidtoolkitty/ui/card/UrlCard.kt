package me.dizzykitty3.androidtoolkitty.ui.card

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
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomItalicText
import me.dizzykitty3.androidtoolkitty.util.TextUtils
import me.dizzykitty3.androidtoolkitty.util.Utils.onClickVisitButton

@Composable
fun UrlCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.url)) {
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
                    onClickVisitButton(url)
                }
            ),
            supportingText = {
                Text(
                    text = buildAnnotatedString {
                        append(text = c.getString(R.string.url_input_hint_1))
                        CustomItalicText(" .www ")
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
                    text = TextUtils.getUrlSuffix(url)
                )
            },
            trailingIcon = {
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = null
                )
            }
        )
    }
}
