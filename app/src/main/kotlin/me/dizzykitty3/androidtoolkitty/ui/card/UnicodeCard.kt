package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.util.Utils.onClickConvertButton

@Composable
fun UnicodeCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.unicode)) {
        var unicode by remember { mutableStateOf("") }
        val characters = remember { mutableStateOf("") }
        Text(
            text = buildAnnotatedString {
                append(c.getString(R.string.unicode_input_hint))
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append(" 00610062")
                }
            }
        )
        Row {
            OutlinedTextField(
                value = unicode,
                onValueChange = { unicode = it },
                label = { Text(c.getString(R.string.unicode)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onClickConvertButton(unicode, characters)
                    }
                )
            )
            OutlinedTextField(
                value = characters.value, // Access the value property of MutableState
                onValueChange = {}, // This field is read-only
                label = { Text(c.getString(R.string.character)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = dimensionResource(id = R.dimen.padding_spacer))
            )
        }
        CustomSpacerPadding()
        Button(
            onClick = {
                onClickConvertButton(unicode, characters)
            }
        ) {
            Text(text = c.getString(R.string.convert))
        }
    }
}