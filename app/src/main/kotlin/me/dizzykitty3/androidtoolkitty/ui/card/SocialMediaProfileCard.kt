package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.util.Utils.onVisitProfile

@Suppress("SpellCheckingInspection")
@Composable
fun SocialMediaProfileCard() {
    CustomCard(title = "Social Media Profile") {
        var username by remember { mutableStateOf("") }
        var platform by remember { mutableStateOf("") }
        CustomDropdownMenu(
            items = listOf(
                "Bilibili",
                "GitHub",
                "X (Twitter)",
                "Weibo",
                "YouTube",
                "No platform you need here?"
            ),
            selectedItem = platform,
            onItemSelected = { platform = it },
            label = "platform"
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
            ),
            supportingText = {
                Text(
                    text = "Visit profile with id or username"
                )
            }
        )
    }
}
