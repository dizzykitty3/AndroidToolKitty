package me.dizzykitty3.androidtoolkitty.view.card

import android.content.Context
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
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.common.util.IntentUtils
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils
import me.dizzykitty3.androidtoolkitty.common.util.StringUtils.debugLog
import me.dizzykitty3.androidtoolkitty.common.util.ToastUtils
import me.dizzykitty3.androidtoolkitty.common.util.UrlUtils
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun SocialMediaProfileCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.Person,
        title = c.getString(R.string.social_media_profile),
        id = "card_social_media_profile"
    ) {
        var username by remember { mutableStateOf("") }
        val mPlatformIndex = SettingsViewModel().getLastTimeSelectedSocialPlatform(c)
        var platformIndex by remember { mutableIntStateOf(mPlatformIndex) }
        val platformList = UrlUtils.Platform.entries.map { c.getString(it.nameResId) }
        if (platformIndex == UrlUtils.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
            CustomTip(
                text = c.getString(R.string.temp2)
            )
        }
        CustomDropdownMenu(
            items = platformList,
            onItemSelected = { platformIndex = it },
            label = {
                if (platformIndex != UrlUtils.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
                    Text(c.getString(R.string.platform))
                } else {
                    Text("")
                }
            }
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                if (platformIndex != UrlUtils.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
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
                    onVisitProfileButton(c, username, platformIndex)
                }
            ),
            supportingText = {
                if (platformIndex != UrlUtils.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
                    val platform = UrlUtils.Platform.entries[platformIndex]
                    Text(
                        text = "${UrlUtils.getProfilePrefix(platform)}$username",
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
                onVisitProfileButton(c, username, platformIndex)
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

private fun onVisitProfileButton(c: Context, username: String, platformIndex: Int) {
    if (username.isBlank()) return
    val platform = UrlUtils.Platform.entries.getOrNull(platformIndex) ?: return
    if (platform == UrlUtils.Platform.PLATFORM_NOT_ADDED_YET) {
        ToastUtils(c).showToastAndRecordLog(
            "${c.getString(R.string.platform)}: \"$username\" ${
                c.getString(
                    R.string.uploaded
                )
            }"
        )
        return
    }
    val prefix = platform.prefix
    IntentUtils(c).openUrl("$prefix${StringUtils.dropSpaces(username)}")
    debugLog("onVisitProfile")
}