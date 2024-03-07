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
import androidx.compose.ui.text.input.ImeAction
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.util.Utils
import me.dizzykitty3.androidtoolkitty.util.Utils.onVisitProfile

@Composable
fun SocialMediaProfileCard() {
    val c = LocalContext.current
    CustomCard(title = "Social Media Profile") {
        var username by remember { mutableStateOf("") }
        var platform by remember { mutableStateOf("") }
        var selectedPlatform by remember { mutableStateOf("") }
        val platformOptions = listOf(
            "GitHub", "X (Twitter)"
        )
        Text(text = "Visit profile with id or username")
        OutlinedTextField(
            value = platform,
            onValueChange = { platform = it },
            label = { Text("platform") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    Utils.onVisitProfile(username, platform)
                }
            )
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onVisitProfile(username, platform)
                }
            )
        )
        CustomSpacerPadding()
        Button(
            onClick = { onVisitProfile(username, platform) }
        ) {
            Text(text = c.getString(R.string.visit))
        }
    }
}
