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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import me.dizzykitty3.androidtoolkitty.Actions
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.util.UrlUtils

@Composable
fun SocialMediaProfileCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.Person,
        title = c.getString(R.string.social_media_profile),
        isExpand = true
    ) {
        var username by remember { mutableStateOf("") }
        var platformIndex by remember { mutableIntStateOf(0) }
        CustomSpacerPadding()
        CustomDropdownMenu(
            items = listOf(
                c.getString(R.string.platform), // 0
                c.getString(R.string.bilibili),
                c.getString(R.string.github),
                c.getString(R.string.pixiv_artwork),
                c.getString(R.string.pixiv_user),
                c.getString(R.string.v2ex),
                c.getString(R.string.weibo),
                c.getString(R.string.x),
                c.getString(R.string.youtube),
                c.getString(R.string.platform_not_added_yet) // 9
            ),
            onItemSelected = { platformIndex = it }
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                if (platformIndex != 9) {
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
                    Actions.onVisitProfileButton(username, platformIndex)
                }
            ),
            supportingText = {
                if (platformIndex == 0) {
                    Text(c.getString(R.string.visit_profile_with_id_or_username))
                } else if (platformIndex != 9) {
                    Text(
                        text = "${UrlUtils.getProfilePrefix(platformIndex)}$username",
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
                Actions.onVisitProfileButton(username, platformIndex)
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