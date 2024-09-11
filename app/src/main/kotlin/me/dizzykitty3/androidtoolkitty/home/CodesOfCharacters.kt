package me.dizzykitty3.androidtoolkitty.home

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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_CODES_OF_CHARACTERS
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.uicomponents.ItalicText
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.ScreenTitle
import me.dizzykitty3.androidtoolkitty.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.utils.DateUtil
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.utils.StringUtil
import me.dizzykitty3.androidtoolkitty.utils.StringUtil.toASCII
import timber.log.Timber

@Composable
fun CodesOfCharacters(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current
    Card(
        R.string.codes_of_characters,
        Icons.AutoMirrored.Outlined.Notes,
        true,
        {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_CODES_OF_CHARACTERS)
        }) {
        Unicode()
    }
}

@Composable
fun CodesOfCharactersScreen() {
    Screen {
        ScreenTitle(R.string.codes_of_characters)
        Card(R.string.unicode) { Unicode() }
        Card(R.string.ascii) { ASCII() }
        Card("Unix Timestamp") { UnixTimestamp() }
    }
}

@Composable
private fun Unicode() {
    var unicode by remember { mutableStateOf("") }
    var characters by remember { mutableStateOf("") }
    var isUnicodeInput by remember { mutableStateOf(false) }
    var isCharacterInput by remember { mutableStateOf(false) }
    val view = LocalView.current
    val focus = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current

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
            Text(buildAnnotatedString {
                append(stringResource(R.string.unicode_input_hint))
                ItalicText(" 00610062")
            })
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
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
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
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                characters = ""
            }
        }
    )

    TextButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        focus.clearFocus()
        if (isUnicodeInput) {
            view.onClickConvertButton(unicode, { characters = it }, true)
        } else if (isCharacterInput) {
            view.onClickConvertButton(characters, { unicode = it }, false)
        }
    }) {
        Text(stringResource(R.string.convert))
    }
}

@Composable
private fun ASCII() {
    val focus = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current
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
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                stringToASCII = ""
            }
        },
    )

    if (toASCIIResult != "") Text("${stringResource(R.string.result)} $toASCIIResult")

    TextButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        focus.clearFocus()
        toASCIIResult = stringToASCII.toASCII()
    }) { Text(stringResource(R.string.convert_to_ascii_values)) }
}

@Composable
private fun UnixTimestamp() {
    Text("current unix timestamp:")
    Text(DateUtil.unixTimestamp)
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
        e.message?.let { this.showSnackbar(it) }
    }
}