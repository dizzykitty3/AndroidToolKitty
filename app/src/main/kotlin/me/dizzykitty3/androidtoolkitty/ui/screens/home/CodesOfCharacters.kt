package me.dizzykitty3.androidtoolkitty.ui.screens.home

import android.view.View
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.domain.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.domain.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.domain.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.domain.utils.StringUtil
import me.dizzykitty3.androidtoolkitty.domain.utils.StringUtil.toASCII
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.ClearInput
import me.dizzykitty3.androidtoolkitty.ui.components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui.components.Italic
import timber.log.Timber

@Composable
fun CodesOfCharacters() {
    var unicode by remember { mutableStateOf("") }
    var characters by remember { mutableStateOf("") }
    var isUnicodeInput by remember { mutableStateOf(false) }
    var isCharacterInput by remember { mutableStateOf(false) }

    Card(
        icon = Icons.AutoMirrored.Outlined.Notes,
        title = R.string.codes_of_characters
    ) {
        val view = LocalView.current
        val focus = LocalFocusManager.current

        GroupTitle(R.string.unicode)

        OutlinedTextField(
            value = unicode,
            onValueChange = {
                unicode = it
                characters = ""
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
                        Italic(" 00610062")
                    }
                )
            },
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isUnicodeInput) {
                        focus.clearFocus()
                        view.onClickConvertButton(unicode, { characters = it }, true)
                    }
                }
            ),
            trailingIcon = {
                ClearInput(unicode) {
                    view.hapticFeedback()
                    unicode = ""
                }
            },
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
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    if (isCharacterInput) {
                        focus.clearFocus()
                        view.onClickConvertButton(characters, { unicode = it }, false)
                    }
                }
            ),
            trailingIcon = {
                ClearInput(characters) {
                    view.hapticFeedback()
                    characters = ""
                }
            },
        )

        TextButton(
            onClick = {
                view.hapticFeedback()
                focus.clearFocus()
                if (isUnicodeInput) {
                    view.onClickConvertButton(unicode, { characters = it }, true)
                } else if (isCharacterInput) {
                    view.onClickConvertButton(characters, { unicode = it }, false)
                }
            }
        ) {
            Text(text = stringResource(R.string.convert))
        }

        GroupDivider()
        GroupTitle(R.string.ascii)

        var stringToASCII by remember { mutableStateOf("") }
        var toASCIIResult by remember { mutableStateOf("") }

        OutlinedTextField(
            value = stringToASCII,
            onValueChange = { stringToASCII = it },
            label = { Text(stringResource(R.string.character)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focus.clearFocus()
                    toASCIIResult = stringToASCII.toASCII()
                }
            ),
            trailingIcon = {
                ClearInput(stringToASCII) {
                    view.hapticFeedback()
                    stringToASCII = ""
                }
            },
        )

        if (toASCIIResult != "") Text("${stringResource(R.string.result)} $toASCIIResult")

        TextButton({
            view.hapticFeedback()
            focus.clearFocus()
            toASCIIResult = stringToASCII.toASCII()
        }) { Text(stringResource(R.string.convert_to_ascii_values)) }
    }
}

private fun View.onClickConvertButton(
    input: String,
    updateResult: (String) -> Unit,
    isUnicodeToChar: Boolean
) {
    if (input.isBlank()) return
    Timber.d("onClickConvertButton")
    try {
        val result = if (isUnicodeToChar) StringUtil.unicodeToCharacter(input)
        else StringUtil.characterToUnicode(input)
        updateResult(result)
        ClipboardUtil.copy(result)
        this.showSnackbar("$result ${appContext.getString(R.string.copied)}")
    } catch (e: Exception) {
        this.showSnackbar(e.message ?: "Unknown error occurred")
    }
}