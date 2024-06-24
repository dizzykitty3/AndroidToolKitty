package me.dizzykitty3.androidtoolkitty.ui.home

import android.view.HapticFeedbackConstants
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
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.app_components.MainApp.Companion.appContext
import me.dizzykitty3.androidtoolkitty.ui_components.Card
import me.dizzykitty3.androidtoolkitty.ui_components.ClearInput
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui_components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui_components.Italic
import me.dizzykitty3.androidtoolkitty.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.utils.StringUtil
import timber.log.Timber

@Preview
@Composable
fun Unicode() {
    var unicode by remember { mutableStateOf("") }
    var characters by remember { mutableStateOf("") }
    var isUnicodeInput by remember { mutableStateOf(false) }
    var isCharacterInput by remember { mutableStateOf(false) }

    Card(
        icon = Icons.AutoMirrored.Outlined.Notes,
        title = R.string.unicode
    ) {
        val view = LocalView.current
        val focus = LocalFocusManager.current

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
                        onClickConvertButton(view, unicode, { characters = it }, true)
                        focus.clearFocus()
                    }
                }
            ),
            trailingIcon = {
                ClearInput(text = unicode) {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
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
                        onClickConvertButton(view, characters, { unicode = it }, false)
                        focus.clearFocus()
                    }
                }
            ),
            trailingIcon = {
                ClearInput(text = characters) {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    characters = ""
                }
            },
        )

        TextButton(
            onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                if (isUnicodeInput) {
                    onClickConvertButton(view, unicode, { characters = it }, true)
                    focus.clearFocus()
                } else if (isCharacterInput) {
                    onClickConvertButton(view, characters, { unicode = it }, false)
                    focus.clearFocus()
                }
            }
        ) {
            Text(text = stringResource(R.string.convert))
        }

        GroupDivider()
        GroupTitle("to ASCII")

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
                    toASCIIResult = StringUtil.toASCII(stringToASCII)
                    focus.clearFocus()
                }
            ),
            trailingIcon = {
                ClearInput(text = stringToASCII) {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    stringToASCII = ""
                }
            },
        )

        Text("result = $toASCIIResult")

        TextButton({
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            toASCIIResult = StringUtil.toASCII(stringToASCII)
        }) { Text("Convert to ASCII values") }
    }
}

private fun onClickConvertButton(
    view: View,
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
        SnackbarUtil.show(view, "$result ${appContext.getString(R.string.copied)}")
    } catch (e: Exception) {
        SnackbarUtil.show(view, e.message ?: "Unknown error occurred")
    }
}