package me.dizzykitty3.androidtoolkitty.ui.card

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Link
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.composable.ClearInput
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupTitleText
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomItalicText
import me.dizzykitty3.androidtoolkitty.foundation.const.HTTPS
import me.dizzykitty3.androidtoolkitty.foundation.util.IntentUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.StringUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.ToastUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.UrlUtil

private const val TAG = "WebpageCard"

@Composable
fun WebpageCard() {
    CustomCard(
        icon = Icons.Outlined.Link,
        title = R.string.webpage
    ) {
        val settingsSharedPref = remember { SettingsSharedPref }

        val showMore = settingsSharedPref.webpageCardShowMore
        var mShowMore by remember { mutableStateOf(showMore) }

        if (mShowMore) CustomGroupTitleText(id = R.string.search)

        Search()

        if (mShowMore) {
            CustomGroupDivider()
            WebpageUrl()
            CustomGroupDivider()
            SocialMediaProfileIUrl()
        }

        TextButton(onClick = {
            mShowMore = !mShowMore
            settingsSharedPref.webpageCardShowMore = mShowMore
        }) {
            if (mShowMore)
                Text(text = stringResource(id = R.string.show_less))
            else
                Text(text = stringResource(id = R.string.show_more))
        }
    }
}

@Composable
private fun Search() {
    val context = LocalContext.current

    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        label = { Text(text = stringResource(id = R.string.search)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = { onClickSearchButton(searchQuery, context) }
        ),
        trailingIcon = {
            ClearInput(text = searchQuery) {
                searchQuery = ""
            }
        },
    )

    TextButton(
        onClick = { onClickSearchButton(searchQuery, context) }
    ) {
        Text(text = stringResource(R.string.search))
        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun WebpageUrl() {
    CustomGroupTitleText(id = R.string.webpage)

    val context = LocalContext.current
    var url by remember { mutableStateOf("") }

    OutlinedTextField(
        value = url,
        onValueChange = { url = it },
        label = { Text(stringResource(R.string.url)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Ascii
        ),
        keyboardActions = KeyboardActions(
            onDone = { onClickVisitUrlButton(url, context) }
        ),
        trailingIcon = {
            ClearInput(text = url) {
                url = ""
            }
        },
        supportingText = {
            Text(
                text = buildAnnotatedString {
                    append(text = stringResource(R.string.url_input_hint_1))
                    CustomItalicText(" www. ")
                    append(text = stringResource(R.string.url_input_hint_2))
                    CustomItalicText(" .com ")
                    append(text = stringResource(R.string.url_input_hint_3))
                    CustomItalicText(" .net ")
                    append(text = stringResource(R.string.url_input_hint_4))
                }
            )
        },
        prefix = {
            Text(text = HTTPS)
        },
        suffix = {
            Text(text = UrlUtil.urlSuffix(url))
        }
    )

    TextButton(
        onClick = { onClickVisitUrlButton(url, context) }
    ) {
        Text(text = stringResource(R.string.visit))
        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun SocialMediaProfileIUrl() {
    val context = LocalContext.current

    var username by remember { mutableStateOf("") }

    val platformIndex = SettingsSharedPref.getLastTimeSelectedSocialPlatform()
    var mPlatformIndex by remember { mutableIntStateOf(platformIndex) }

    val platformList = UrlUtil.Platform.entries.map { stringResource(it.nameResId) }

    CustomGroupTitleText(id = R.string.social_media_profile)

    CustomDropdownMenu(
        items = platformList,
        onItemSelected = { mPlatformIndex = it },
        label = {
            if (mPlatformIndex != UrlUtil.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
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
            if (mPlatformIndex != UrlUtil.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
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
            onDone = { onVisitProfileButton(username, mPlatformIndex, context) }
        ),
        trailingIcon = {
            ClearInput(text = username) {
                username = ""
            }
        },
        supportingText = {
            if (mPlatformIndex != UrlUtil.Platform.PLATFORM_NOT_ADDED_YET.ordinal) {
                val platform = UrlUtil.Platform.entries[mPlatformIndex]
                Text(
                    text = if (platform != UrlUtil.Platform.FANBOX)
                        "${UrlUtil.profilePrefix(platform)}$username"
                    else
                        "$username${UrlUtil.profilePrefix(platform)}",
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            } else {
                Text(stringResource(R.string.submit_the_platform_you_need))
            }
        }
    )

    TextButton(
        onClick = { onVisitProfileButton(username, mPlatformIndex, context) }
    ) {
        Text(text = stringResource(R.string.visit))

        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

private fun onClickSearchButton(query: String, context: Context) {
    if (query.isBlank()) return

    IntentUtil.openSearch(query, context)
    Log.d(TAG, "onClickSearchButton")
}

private fun onClickVisitUrlButton(url: String, context: Context) {
    if (url.isBlank()) return

    IntentUtil.openUrl(UrlUtil.toFullUrl(StringUtil.dropSpaces(url)), context)
    Log.d(TAG, "onClickVisitButton")
}

private fun onVisitProfileButton(username: String, platformIndex: Int, context: Context) {
    if (username.isBlank()) return

    val platform = UrlUtil.Platform.entries.getOrNull(platformIndex) ?: return

    if (platform == UrlUtil.Platform.PLATFORM_NOT_ADDED_YET) {
//        ToastUtil.toast("${context.getString(R.string.platform)}: \"$username\" ${context.getString(R.string.uploaded)}")
        ToastUtil.toast("under development")
        return
    }

    val prefix = platform.prefix
    val url =
        if (platform == UrlUtil.Platform.FANBOX)
            "${StringUtil.dropSpaces(username)}$prefix"
        else
            "$prefix${StringUtil.dropSpaces(username)}"
    IntentUtil.openUrl(url, context)
    Log.d(TAG, "onVisitProfile")
}