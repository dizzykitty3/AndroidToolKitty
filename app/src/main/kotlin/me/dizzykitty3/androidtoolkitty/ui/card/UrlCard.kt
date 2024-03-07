package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
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
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.util.Utils.onClickVisitButton

@Composable
fun UrlCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.url)) {
        var url by remember { mutableStateOf("") }
        Text(text = buildAnnotatedString {
            append(text = c.getString(R.string.url_input_hint_1))
            CustomItalicText(" .www ")
            append(text = c.getString(R.string.url_input_hint_2))
            CustomItalicText(" .com ")
            append(text = c.getString(R.string.url_input_hint_3))
            CustomItalicText(" .net ")
            append(text = c.getString(R.string.url_input_hint_4))
        })
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
            )
        )
        CustomSpacerPadding()
        Button(
            onClick = { onClickVisitButton(url) }
        ) {
            Text(text = c.getString(R.string.visit))
        }
    }
}
