package me.dizzykitty3.androidtoolkitty.home

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
import androidx.compose.material.icons.outlined.Search
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
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.HTTPS
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_WEBPAGE
import me.dizzykitty3.androidtoolkitty.WHAT_IS_PACKAGE_NAME_URL
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CardShowMore
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupTitle
import me.dizzykitty3.androidtoolkitty.uicomponents.ItalicText
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.checkOnMarket
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openSearch
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.searchOnYouTube
import me.dizzykitty3.androidtoolkitty.utils.StringUtil.dropSpaces
import me.dizzykitty3.androidtoolkitty.utils.StringUtil.isInvalidUsername
import me.dizzykitty3.androidtoolkitty.utils.URLUtil
import me.dizzykitty3.androidtoolkitty.utils.URLUtil.getSuffix
import me.dizzykitty3.androidtoolkitty.utils.URLUtil.toFullURL
import timber.log.Timber

@Composable
fun SearchAndWebpage(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    Card(R.string.search_and_webpage, Icons.Outlined.Search) {
        Search()
        CardShowMore { navController.navigate(SCR_WEBPAGE) }
    }
}

@Composable
fun WebpageScreen() {
    Screen {
        Card(R.string.search_and_webpage, Icons.Outlined.Search) {
            GroupTitle(R.string.search)
            Search()
            GroupDivider()
            WebpageURL()
            GroupDivider()
            SocialMediaProfileIURL()
            GroupDivider()
            CheckAppOnMarket()
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
        label = { Text(stringResource(R.string.query)) },
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
            ClearInput(searchQuery) {
                view.hapticFeedback()
                searchQuery = ""
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
            view.context.onClickSearchButton(searchQuery)
        }) {
            Text(text = stringResource(R.string.search))
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
    val view = LocalView.current
    val focus = LocalFocusManager.current
    var url by remember { mutableStateOf("") }
    val fullWidthPeriod = "。"
    val halfWidthPeriod = "."

    GroupTitle(R.string.webpage)

    OutlinedTextField(
        value = url,
        onValueChange = { url = it.replace(fullWidthPeriod, halfWidthPeriod) },
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
            ClearInput(url) {
                view.hapticFeedback()
                url = ""
            }
        },
        supportingText = {
            Text(buildAnnotatedString {
                append(stringResource(R.string.url_input_hint_1))
                ItalicText(" www. ")
                append(stringResource(R.string.url_input_hint_2))
                ItalicText(" .com ")
                append(stringResource(R.string.url_input_hint_3))
                ItalicText(" .net ")
                append(stringResource(R.string.url_input_hint_4))
            })
        },
        prefix = {
            if (!url.contains(HTTPS)) {
                Text(HTTPS)
            }
        },
        suffix = { Text(url.getSuffix()) }
    )

    TextButton({
        view.hapticFeedback()
        focus.clearFocus()
        view.context.onClickVisitURLButton(url)
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
    var username by remember { mutableStateOf("") }
    var platformIndex by remember { mutableIntStateOf(SettingsSharedPref.lastTimeSelectedSocialPlatform) }
    val platform = URLUtil.Platform.entries[platformIndex]
    val platformList = URLUtil.Platform.entries.map { stringResource(it.platform) }

    GroupTitle(R.string.social_media_profile)

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
            ClearInput(username) {
                view.hapticFeedback()
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
    this.searchOnYouTube(query)
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
 * @see me.dizzykitty3.androidtoolkitty.utils.URLUtil.Platform
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

@Composable
private fun CheckAppOnMarket() {
    GroupTitle(R.string.check_app_on_market)

    val view = LocalView.current
    val focus = LocalFocusManager.current
    var packageName by remember { mutableStateOf("") }

    OutlinedTextField(
        value = packageName,
        onValueChange = { packageName = it },
        label = { Text(stringResource(R.string.package_name_or_search)) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focus.clearFocus()
                view.context.checkOnMarket(packageName)
            }
        ),
        trailingIcon = {
            ClearInput(packageName) {
                view.hapticFeedback()
                packageName = ""
            }
        }
    )
    Row(
        Modifier.horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton({
            view.hapticFeedback()
            focus.clearFocus()
            view.context.checkOnMarket(packageName)
        }
        ) {
            Text(stringResource(R.string.open_on_google_play))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(id = R.string.check_app_on_market),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Text("|")
        TextButton({
            view.hapticFeedback()
            focus.clearFocus()
            view.context.checkOnMarket(packageName, false)
        }
        ) {
            Text(stringResource(R.string.open_on_other_markets))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(id = R.string.open_on_other_markets),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
    }
    WhatIsPackageName()
}

@Composable
private fun WhatIsPackageName() {
    val view = LocalView.current

    TextButton({
        view.hapticFeedback()
        view.context.openURL(WHAT_IS_PACKAGE_NAME_URL)
    }) {
        Text(stringResource(R.string.what_is_package_name))
        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = stringResource(R.string.what_is_package_name)
        )
    }
}