package me.dizzykitty3.androidtoolkitty.ui.screens.home

import android.content.Context
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
import me.dizzykitty3.androidtoolkitty.domain.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.checkOnYouTube
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.openSearch
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.domain.utils.StringUtil.dropSpaces
import me.dizzykitty3.androidtoolkitty.domain.utils.StringUtil.isInvalidUsername
import me.dizzykitty3.androidtoolkitty.domain.utils.URLUtil
import me.dizzykitty3.androidtoolkitty.domain.utils.URLUtil.getSuffix
import me.dizzykitty3.androidtoolkitty.domain.utils.URLUtil.toFullURL
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.ClearInput
import me.dizzykitty3.androidtoolkitty.ui.components.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.ui.components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui.components.Italic
import me.dizzykitty3.androidtoolkitty.ui.viewmodel.SettingsViewModel
import timber.log.Timber

@Composable
fun Webpage(settingsViewModel: SettingsViewModel) {
    Card(R.string.webpage, Icons.Outlined.Link) {
        val view = LocalView.current
        val fullWebapgeCard = settingsViewModel.settings.value.fullWebpageCard
        var _showMore by remember { mutableStateOf(false) }

        if (fullWebapgeCard || _showMore) GroupTitle(R.string.search)

        Search()

        if (fullWebapgeCard || _showMore) {
            GroupDivider()
            WebpageURL()
            GroupDivider()
            SocialMediaProfileIURL()
        }

        if (!fullWebapgeCard && !_showMore) {
            TextButton({
                view.hapticFeedback()
                _showMore = !_showMore
            }) { Text(stringResource(R.string.show_more)) }
        }
    }
}

@Composable
private fun Search() {
    val view = LocalView.current
    val focus = LocalFocusManager.current
    var _searchQuery by remember { mutableStateOf("") }

    OutlinedTextField(
        value = _searchQuery,
        onValueChange = { _searchQuery = it },
        label = { Text(stringResource(R.string.query)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
                view.context.onClickSearchButton(_searchQuery)
            }
        ),
        trailingIcon = {
            ClearInput(text = _searchQuery) {
                view.hapticFeedback()
                _searchQuery = ""
            }
        },
    )

    Row(
        Modifier.horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        TextButton({
            view.hapticFeedback()
            focus.clearFocus()
            view.context.onClickSearchButton(_searchQuery)
        }) {
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
                view.hapticFeedback()
                focus.clearFocus()
                view.context.onCheckOnYouTube(_searchQuery)
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
    val view = LocalView.current
    val focus = LocalFocusManager.current
    var _url by remember { mutableStateOf("") }
    val fullWidthPeriod = "。"
    val halfWidthPeriod = "."

    GroupTitle(R.string.webpage)

    OutlinedTextField(
        value = _url,
        onValueChange = { _url = it.replace(fullWidthPeriod, halfWidthPeriod) },
        label = { Text(stringResource(R.string.url)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Ascii
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
                view.context.onClickVisitURLButton(_url)
            }
        ),
        trailingIcon = {
            ClearInput(_url) {
                view.hapticFeedback()
                _url = ""
            }
        },
        supportingText = {
            Text(buildAnnotatedString {
                append(stringResource(R.string.url_input_hint_1))
                Italic(" www. ")
                append(stringResource(R.string.url_input_hint_2))
                Italic(" .com ")
                append(stringResource(R.string.url_input_hint_3))
                Italic(" .net ")
                append(stringResource(R.string.url_input_hint_4))
            })
        },
        prefix = {
            if (!_url.contains(HTTPS)) {
                Text(HTTPS)
            }
        },
        suffix = { Text(_url.getSuffix()) }
    )

    TextButton({
        view.hapticFeedback()
        focus.clearFocus()
        view.context.onClickVisitURLButton(_url)
    }) {
        Text(stringResource(R.string.visit))
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
    var _username by remember { mutableStateOf("") }
    var _platformIndex by remember { mutableIntStateOf(SettingsSharedPref.lastTimeSelectedSocialPlatform) }
    val platform = URLUtil.Platform.entries[_platformIndex]
    val platformList = URLUtil.Platform.entries.map { stringResource(it.platform) }

    GroupTitle(R.string.social_media_profile)

    CustomDropdownMenu(
        items = platformList,
        onItemSelected = { _platformIndex = it },
        label = { Text(stringResource(R.string.platform)) }
    )

    OutlinedTextField(
        value = _username,
        onValueChange = { _username = it },
        label = { Text(stringResource(R.string.username)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
                if (isValid(platform, _username)) {
                    view.context.onVisitProfileButton(_username, _platformIndex)
                }
            }
        ),
        trailingIcon = {
            ClearInput(text = _username) {
                view.hapticFeedback()
                _username = ""
            }
        },
        supportingText = {
            Column {
                Text(
                    text = toSocialMediaFullURL(platform, _username),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
                if (isInvalid(platform, _username)) {
                    Text(
                        stringResource(R.string.invalid_username_input_tip, platform),
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    )

    TextButton({
        view.hapticFeedback()
        focus.clearFocus()
        if (isValid(platform, _username)) {
            view.context.onVisitProfileButton(_username, _platformIndex)
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
    this.openURL(url.dropSpaces().lowercase().toFullURL())
}

private fun Context.onVisitProfileButton(username: String, platformIndex: Int) {
    if (username.isBlank()) return
    Timber.d("onVisitProfile")
    val platform = URLUtil.Platform.entries.getOrNull(platformIndex) ?: return
    val url = toSocialMediaFullURL(platform, username)
    this.openURL(url)
}

/**
 * @see me.dizzykitty3.androidtoolkitty.domain.utils.URLUtil.Platform
 */
private fun toSocialMediaFullURL(platform: URLUtil.Platform, username: String): String =
    when (platform) {
        URLUtil.Platform.BLUESKY ->
            if (username.contains("."))
                "${platform.prefix}$username" // user custom
            else if (username.isNotBlank())
                "${platform.prefix}${username.dropSpaces().lowercase()}.bsky.social" // default
            else
                platform.prefix // for app UI display

        URLUtil.Platform.FANBOX,
        URLUtil.Platform.BOOTH,
        URLUtil.Platform.TUMBLR,
        URLUtil.Platform.CARRD -> "${username.dropSpaces().lowercase()}${platform.prefix}"

        else -> "${platform.prefix}${username.dropSpaces().lowercase()}"
    }

private fun isInvalid(platform: URLUtil.Platform, username: String): Boolean =
    platform == URLUtil.Platform.X
            && username.isNotBlank()
            && username.dropSpaces().isInvalidUsername()

private fun isValid(platform: URLUtil.Platform, username: String): Boolean =
    !isInvalid(platform, username)