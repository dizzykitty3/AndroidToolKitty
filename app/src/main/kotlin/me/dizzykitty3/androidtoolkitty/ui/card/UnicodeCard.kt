package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
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
import me.dizzykitty3.androidtoolkitty.util.Utils.onClickConvertButton

@Composable
fun UnicodeCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.unicode)) {
        var unicode by remember { mutableStateOf("") }
        val characters = remember { mutableStateOf("") }
        OutlinedTextField(
            value = unicode,
            onValueChange = { unicode = it },
            label = { Text(c.getString(R.string.unicode)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onClickConvertButton(unicode, characters)
                }
            ),
            placeholder = {
                Text(
                    text = buildAnnotatedString {
                        append(c.getString(R.string.unicode_input_hint))
                        CustomItalicText(" 00610062")
                    },
                    style = MaterialTheme.typography.labelSmall
                )
            }
        )
        OutlinedTextField(
            value = characters.value,
            onValueChange = {},
            label = { Text(c.getString(R.string.character)) },
            modifier = Modifier.fillMaxWidth()
        )
//        }
    }
}