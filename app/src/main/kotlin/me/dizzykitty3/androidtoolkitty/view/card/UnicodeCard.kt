package me.dizzykitty3.androidtoolkitty.view.card

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomItalicText
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.common.util.ClipboardUtils
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog
import me.dizzykitty3.androidtoolkitty.common.util.ToastUtils

@Composable
fun UnicodeCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.AutoMirrored.Outlined.Notes,
        title = c.getString(R.string.unicode),
        id = "card_unicode"
    ) {
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
                    onClickConvertButton(c, unicode, characters)
                }
            ),
            supportingText = {
                Text(
                    text = buildAnnotatedString {
                        append(c.getString(R.string.unicode_input_hint))
                        CustomItalicText(" 00610062")
                    }
                )
            }
        )
        OutlinedTextField(
            value = characters.value,
            onValueChange = {},
            label = { Text(c.getString(R.string.character)) },
            modifier = Modifier.fillMaxWidth()
        )
        TextButton(
            onClick = {
                onClickConvertButton(c, unicode, characters)
            }
        ) {
            Text(
                text = c.getString(R.string.convert)
            )
        }
        CustomTip(
            text = c.getString(R.string.temp3)
        )
    }
}

fun onClickConvertButton(c: Context, unicode: String, characterField: MutableState<String>) {
    if (unicode.isBlank()) return
    try {
        val result = StringUtils.convertUnicodeToCharacter(unicode)
        characterField.value = result
        ClipboardUtils(c).copyTextToClipboard(result)
    } catch (e: Exception) {
        ToastUtils(c).showToast(e.message?.ifBlank { "Unknown error occurred" })
    }
    debugLog("onClickConvertButton")
}