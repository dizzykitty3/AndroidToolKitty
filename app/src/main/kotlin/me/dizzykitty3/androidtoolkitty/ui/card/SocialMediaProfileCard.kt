package me.dizzykitty3.androidtoolkitty.ui.card

import android.content.Context
import android.util.Log
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.foundation.utils.TIntent
import me.dizzykitty3.androidtoolkitty.foundation.utils.TString
import me.dizzykitty3.androidtoolkitty.foundation.utils.TToast
import me.dizzykitty3.androidtoolkitty.foundation.utils.TUrl

private const val TAG = "SocialMediaProfileCard"

@Composable
fun SocialMediaProfileCard() {
    CustomCard(
        icon = Icons.Outlined.Person,
        title = R.string.social_media_profile
    ) {
        val context = LocalContext.current

        var username by remember { mutableStateOf("") }

        val platformIndex = SettingsViewModel.getLastTimeSelectedSocialPlatform()
        var mPlatformIndex by remember { mutableIntStateOf(platformIndex) }

        val platformList = TUrl.Platform.entries.map { stringResource(it.nameResId) }
        if (mPlatformIndex == TUrl.Platform.PLATFORM_NOT_ADDED_YET.ordinal) CustomTip(resId = R.string.temp2)

        CustomDropdownMenu(
            items = platformList,
            onItemSelected = { mPlatformIndex = it },
            label = {
                if (mPlatformIndex != TUrl.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
                    Text(stringResource(R.string.platform))
                } else {
                    Text("")
                }
            }
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = {
                if (mPlatformIndex != TUrl.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
                    Text(stringResource(R.string.username))
                } else {
                    Text(stringResource(R.string.platform))
                }
            },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { onVisitProfileButton(context, username, mPlatformIndex) }
            ),
            supportingText = {
                if (mPlatformIndex != TUrl.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
                    val platform = TUrl.Platform.entries[mPlatformIndex]
                    Text(
                        text = "${TUrl.profilePrefix(platform)}$username",
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                } else {
                    Text(stringResource(R.string.submit_the_platform_you_need))
                }
            }
        )

        TextButton(
            onClick = { onVisitProfileButton(context, username, mPlatformIndex) }
        ) {
            Text(text = stringResource(R.string.visit))

            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

private fun onVisitProfileButton(context: Context, username: String, platformIndex: Int) {
    if (username.isBlank()) return

    val platform = TUrl.Platform.entries.getOrNull(platformIndex) ?: return

    if (platform == TUrl.Platform.PLATFORM_NOT_ADDED_YET) {
        TToast.toastAndLog(
            "${context.getString(R.string.platform)}: \"$username\" ${
                context.getString(
                    R.string.uploaded
                )
            }"
        )
        return
    }

    val prefix = platform.prefix
    TIntent.openUrl("$prefix${TString.dropSpaces(username)}")
    Log.d(TAG, "onVisitProfile")
}