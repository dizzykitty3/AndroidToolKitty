package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Notes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
import me.dizzykitty3.androidtoolkitty.foundation.utils.TClipboard
import me.dizzykitty3.androidtoolkitty.foundation.utils.TString
import me.dizzykitty3.androidtoolkitty.foundation.utils.TToast

private const val TAG = "UnicodeCard"

@Composable
fun UnicodeCard() {
    var unicode by remember { mutableStateOf("") }
    var characters by remember { mutableStateOf("") }
    var isUnicodeInput by remember { mutableStateOf(false) }
    var isCharacterInput by remember { mutableStateOf(false) }

    CustomCard(
        icon = Icons.AutoMirrored.Outlined.Notes,
        title = R.string.unicode
    ) {
        OutlinedTextField(
            value = unicode,
            onValueChange = {
                unicode = it
                characters = "" // 清空字符字段
                isUnicodeInput = true
                isCharacterInput = false
            },
            label = { Text(stringResource(R.string.unicode)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
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
            value = characters,
            onValueChange = {
                characters = it
                unicode = ""
                isCharacterInput = true
                isUnicodeInput = false
            },
            label = { Text(stringResource(R.string.character)) },
            modifier = Modifier.fillMaxWidth()
        )

        TextButton(
            onClick = {
                if (isUnicodeInput) {
                    onClickConvertButton(unicode, { characters = it }, true)
                } else if (isCharacterInput) {
                    onClickConvertButton(characters, { unicode = it }, false)
                }
            }
        ) {
            Text(text = stringResource(R.string.convert))
        }
    }
}

private fun onClickConvertButton(
    input: String,
    updateResult: (String) -> Unit,
    isUnicodeToChar: Boolean
) {
    if (input.isBlank()) return

    try {
        val result = if (isUnicodeToChar) {
            TString.unicodeToCharacter(input)
        } else {
            TString.characterToUnicode(input)
        }
        updateResult(result)
        TClipboard.copy(result)
    } catch (e: Exception) {
        TToast.toast(e.message ?: "Unknown error occurred")
    }
}