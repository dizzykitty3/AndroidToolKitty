package me.dizzykitty3.androidtoolkitty.view.card

import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui_component.CustomItalicText
import me.dizzykitty3.androidtoolkitty.foundation.utils.ClipboardService
import me.dizzykitty3.androidtoolkitty.foundation.utils.TString
import me.dizzykitty3.androidtoolkitty.foundation.utils.ToastService

private const val TAG = "UnicodeCard"

@Composable
fun UnicodeCard() {
    CustomCard(
        icon = Icons.AutoMirrored.Outlined.Notes,
        title = R.string.unicode
    ) {
        var unicode by remember { mutableStateOf("") }
        val characters = remember { mutableStateOf("") }

        OutlinedTextField(
            value = unicode,
            onValueChange = { unicode = it },
            label = { Text(stringResource(R.string.unicode)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onClickConvertButton(unicode, characters) }
            ),
            supportingText = {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.unicode_input_hint))
                        CustomItalicText(" 00610062")
                    }
                )
            }
        )

        OutlinedTextField(
            value = characters.value,
            onValueChange = {},
            label = { Text(stringResource(R.string.character)) },
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = { onClickConvertButton(unicode, characters) }
        ) {
            Text(text = stringResource(R.string.convert))
        }
    }
}

private fun onClickConvertButton(
    unicode: String,
    characterField: MutableState<String>
) {
    if (unicode.isBlank()) return

    try {
        val result = TString.unicodeToCharacter(unicode)
        characterField.value = result
        ClipboardService.copy(result)
    } catch (e: Exception) {
        ToastService.toast(e.message ?: "Unknown error occurred")
    }
    Log.d(TAG, "onClickConvertButton")
}