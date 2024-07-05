package me.dizzykitty3.androidtoolkitty.ui.view.home

import android.content.Context
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.HTTPS
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.data.utils.IntentUtil.checkOnYouTube
import me.dizzykitty3.androidtoolkitty.data.utils.IntentUtil.openSearch
import me.dizzykitty3.androidtoolkitty.data.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.data.utils.StringUtil
import me.dizzykitty3.androidtoolkitty.data.utils.URLUtil
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.ClearInput
import me.dizzykitty3.androidtoolkitty.ui.components.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.ui.components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui.components.Italic
import timber.log.Timber

@Composable
fun Webpage() {
    Card(
        icon = Icons.Outlined.Link,
        title = R.string.webpage
    ) {
        val view = LocalView.current
        val settingsSharedPref = remember { SettingsSharedPref }
        val keepShowMore by remember { mutableStateOf(settingsSharedPref.keepWebpageCardShowMore) }
        var mShowMore by remember { mutableStateOf(false) }

        if (keepShowMore || mShowMore) GroupTitle(title = R.string.search)

        Search()

        if (keepShowMore || mShowMore) {
            GroupDivider()
            WebpageURL()
            GroupDivider()
            SocialMediaProfileIURL()
        }

        if (!keepShowMore && !mShowMore) {
            TextButton(onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                mShowMore = !mShowMore
            }) {
                Text(text = stringResource(id = R.string.show_more))
            }
        }
    }
}

@Composable
private fun Search() {
    val view = LocalView.current
    val focus = LocalFocusManager.current
    var searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        label = { Text(text = stringResource(id = R.string.query)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
                view.context.onClickSearchButton(searchQuery)
            }
        ),
        trailingIcon = {
            ClearInput(text = searchQuery) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                searchQuery = ""
            }
        },
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.horizontalScroll(rememberScrollState())
    ) {
        TextButton(
            onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                focus.clearFocus()
                view.context.onClickSearchButton(searchQuery)
            }
        ) {
            Text(text = stringResource(R.string.visit))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Text("|")
        TextButton(
            onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                focus.clearFocus()
                view.context.onCheckOnYouTube(searchQuery)
            }
        ) {
            Text(text = stringResource(R.string.search_on_youtube))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
}

@Composable
private fun WebpageURL() {
    GroupTitle(title = R.string.webpage)

    val view = LocalView.current
    val focus = LocalFocusManager.current
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
            onDone = {
                focus.clearFocus()
                view.context.onClickVisitURLButton(url)
            }
        ),
        trailingIcon = {
            ClearInput(text = url) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                url = ""
            }
        },
        supportingText = {
            Text(
                text = buildAnnotatedString {
                    append(text = stringResource(R.string.url_input_hint_1))
                    Italic(" www. ")
                    append(text = stringResource(R.string.url_input_hint_2))
                    Italic(" .com ")
                    append(text = stringResource(R.string.url_input_hint_3))
                    Italic(" .net ")
                    append(text = stringResource(R.string.url_input_hint_4))
                }
            )
        },
        prefix = {
            if (!url.contains(HTTPS)) {
                Text(text = HTTPS)
            }
        },
        suffix = { Text(text = URLUtil.suffixOf(url)) }
    )

    TextButton(onClick = {
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        focus.clearFocus()
        view.context.onClickVisitURLButton(url)
    }) {
        Text(text = stringResource(R.string.visit))
        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

@Composable
private fun SocialMediaProfileIURL() {
    val view = LocalView.current
    val focus = LocalFocusManager.current
    var username by remember { mutableStateOf("") }
    var platformIndex by remember { mutableIntStateOf(SettingsSharedPref.lastTimeSelectedSocialPlatform) }
    val platform = URLUtil.Platform.entries[platformIndex]
    val platformList = URLUtil.Platform.entries.map { stringResource(it.platform) }

    GroupTitle(title = R.string.social_media_profile)

    CustomDropdownMenu(
        items = platformList,
        onItemSelected = { platformIndex = it },
        label = { Text(stringResource(R.string.platform)) }
    )

    OutlinedTextField(
        value = username,
        onValueChange = { username = it },
        label = { Text(stringResource(R.string.username)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
                if (isValid(platform, username)) {
                    view.context.onVisitProfileButton(username, platformIndex)
                }
            }
        ),
        trailingIcon = {
            ClearInput(text = username) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                username = ""
            }
        },
        supportingText = {
            Column {
                Text(
                    text = toSocialMediaFullURL(platform, username),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                if (isInvalid(platform, username)) {
                    Text(
                        "username for $platform should only contains letters, numbers, and underscores",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    )

    TextButton({
        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
        focus.clearFocus()
        if (isValid(platform, username)) {
            view.context.onVisitProfileButton(username, platformIndex)
        }
    }) {
        Text(stringResource(R.string.visit))

        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }
}

private fun Context.onClickSearchButton(query: String) {
    if (query.isBlank()) return
    Timber.d("onClickSearchButton")
    this.openSearch(query)
}

private fun Context.onCheckOnYouTube(query: String) {
    if (query.isBlank()) return
    Timber.d("onCheckOnYouTube")
    this.checkOnYouTube(query)
}

private fun Context.onClickVisitURLButton(url: String) {
    if (url.isBlank()) return
    Timber.d("onClickVisitButton")
    this.openURL(URLUtil.toFullURL(StringUtil.dropSpacesAndLowercase(url)))
}

private fun Context.onVisitProfileButton(
    username: String,
    platformIndex: Int,
) {
    if (username.isBlank()) return
    Timber.d("onVisitProfile")
    val platform = URLUtil.Platform.entries.getOrNull(platformIndex) ?: return
    val url = toSocialMediaFullURL(platform, username)
    this.openURL(url)
}

/**
 * @see me.dizzykitty3.androidtoolkitty.data.utils.URLUtil.Platform
 */
private fun toSocialMediaFullURL(platform: URLUtil.Platform, username: String): String =
    when (platform) {
        URLUtil.Platform.BLUESKY ->
            if (username.contains("."))
                "${platform.prefix}$username" // user custom
            else if (username.isNotBlank())
                "${platform.prefix}${StringUtil.dropSpacesAndLowercase(username)}.bsky.social" // default
            else
                platform.prefix // for app UI display

        URLUtil.Platform.FANBOX,
        URLUtil.Platform.BOOTH,
        URLUtil.Platform.TUMBLR,
        URLUtil.Platform.CARRD -> "${StringUtil.dropSpacesAndLowercase(username)}${platform.prefix}"

        else -> "${platform.prefix}${StringUtil.dropSpacesAndLowercase(username)}"
    }

private fun isInvalid(platform: URLUtil.Platform, username: String): Boolean =
    platform == URLUtil.Platform.X
            && username.isNotBlank()
            && StringUtil.invalidUsername(StringUtil.dropSpaces(username))

private fun isValid(platform: URLUtil.Platform, username: String): Boolean =
    !isInvalid(platform, username)