package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.util.Utils.getProfilePrefix
import me.dizzykitty3.androidtoolkitty.util.Utils.onVisitProfile

@Composable
fun SocialMediaProfileCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.Person,
        title = c.getString(R.string.social_media_profile),
        isExpand = true
    ) {
        var username by remember { mutableStateOf("") }
        var platform by remember { mutableStateOf("") }
        CustomDropdownMenu(
            items = listOf(
                c.getString(R.string.bilibili),
                c.getString(R.string.github),
                c.getString(R.string.x),
                c.getString(R.string.weibo),
                c.getString(R.string.youtube),
                c.getString(R.string.platform_not_added_yet),
            ),
            selectedItem = platform,
            onItemSelected = { platform = it },
            label =
            if (platform != c.getString(R.string.platform_not_added_yet)) {
                c.getString(R.string.platform)
            } else {
                ""
            }
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                if (platform != c.getString(R.string.platform_not_added_yet)) {
                    Text(c.getString(R.string.username))
                } else {
                    Text(c.getString(R.string.platform))
                }
            },
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
                if (platform == "") {
                    Text(c.getString(R.string.visit_profile_with_id_or_username))
                } else if (platform != c.getString(R.string.platform_not_added_yet)) {
                    Text(
                        text = "${getProfilePrefix(platform)}$username",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                } else {
                    Text(c.getString(R.string.submit_the_platform_you_need))
                }
            }
        )
        TextButton(
            onClick = {
                onVisitProfile(username, platform)
            }
        ) {
            Text(
                text = c.getString(R.string.visit)
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}