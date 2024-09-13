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
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.HTTPS
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_SEARCH
import me.dizzykitty3.androidtoolkitty.WHAT_IS_PACKAGE_NAME_URL
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.uicomponents.ButtonDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.ClearInput
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.uicomponents.ErrorTip
import me.dizzykitty3.androidtoolkitty.uicomponents.ItalicText
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.ScreenTitle
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.uicomponents.Tip
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.checkOnMarket
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openSearch
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.searchOnYouTube
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.utils.StringUtil.dropSpaces
import me.dizzykitty3.androidtoolkitty.utils.StringUtil.isInvalidUsername
import me.dizzykitty3.androidtoolkitty.utils.URLUtil
import me.dizzykitty3.androidtoolkitty.utils.URLUtil.getSuffix
import me.dizzykitty3.androidtoolkitty.utils.URLUtil.toFullURL
import timber.log.Timber

@Composable
fun Search(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current
    Card(
        R.string.search,
        Icons.Outlined.Search,
        true,
        {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_SEARCH)
        }) {
        Search()
    }
}

@Composable
fun SearchScreen(settingsViewModel: SettingsViewModel) {
    Screen {
        ScreenTitle(R.string.search)
        Card(R.string.webpage) { Webpage() }
        Card(R.string.social_profile) { SocialMediaProfile(settingsViewModel) }
        Card(R.string.check_app_on_market) { CheckAppOnMarket() }
        Card(R.string.search) { Search() }
    }
}

@Composable
private fun Search() {
    val view = LocalView.current
    val focus = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current
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
                view.context.onTapSearchButton(searchQuery)
            }
        ),
        trailingIcon = {
            ClearInput(searchQuery) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                searchQuery = ""
            }
        },
    )

    Row(
        Modifier.horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton({
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            focus.clearFocus()
            view.context.onTapSearchButton(searchQuery)
        }) {
            Text(stringResource(R.string.search))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
            )
        }
        ButtonDivider()
        TextButton({
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            focus.clearFocus()
            view.context.onTapCheckOnYouTubeButton(searchQuery)
        }) {
            Text(stringResource(R.string.search_on_youtube))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
            )
        }
    }
}

@Composable
private fun Webpage() {
    val view = LocalView.current
    val focus = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current
    var url by remember { mutableStateOf("") }
    val fullWidthPeriod = "。"
    val halfWidthPeriod = "."

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
                view.context.onTapVisitURLButton(url)
            }
        ),
        trailingIcon = {
            ClearInput(url) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
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
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        focus.clearFocus()
        view.context.onTapVisitURLButton(url)
    }) {
        Text(stringResource(R.string.visit))
        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
        )
    }
}

@Composable
private fun SocialMediaProfile(settingsViewModel: SettingsViewModel) {
    val view = LocalView.current
    val focus = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current
    var username by remember { mutableStateOf("") }
    var platformIndex by remember { mutableIntStateOf(settingsViewModel.settings.value.lastSelectedPlatformIndex) }
    val platform = URLUtil.Platform.entries[platformIndex]
    val platformList = URLUtil.Platform.entries.map { stringResource(it.platform) }

    CustomDropdownMenu(
        items = platformList,
        onItemSelected = { platformIndex = it },
        label = { Text(stringResource(R.string.platform)) },
        selectedPlatformIndex = platformIndex
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
                    view.context.onTapVisitProfileButton(username, platformIndex)
                    settingsViewModel.update(
                        settingsViewModel.settings.value.copy(
                            lastSelectedPlatformIndex = platformIndex
                        )
                    )
                } else {
                    view.showSnackbar(
                        view.context.getString(
                            R.string.invalid_username_input_tip,
                            platform
                        )
                    )
                }
            }
        ),
        trailingIcon = {
            ClearInput(username) {
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                username = ""
            }
        },
        supportingText = {
            Column {
                Text(
                    toProfileFullURL(platform, username),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    )

    if (isCaseSensitive(platform)) {
        SpacerPadding()
        Tip(R.string.tip_case_sensitive)
    } else if (isInvalid(platform, username)) {
        SpacerPadding()
        ErrorTip(stringResource(R.string.invalid_username_input_tip, platform))
    }

    TextButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        focus.clearFocus()
        if (isValid(platform, username)) {
            view.context.onTapVisitProfileButton(username, platformIndex)
            settingsViewModel.update(settingsViewModel.settings.value.copy(lastSelectedPlatformIndex = platformIndex))
        } else {
            view.showSnackbar(view.context.getString(R.string.invalid_username_input_tip, platform))
        }
    }) {
        Text(stringResource(R.string.visit))

        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = null,
            modifier = Modifier.align(Alignment.CenterVertically),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
        )
    }
}

private fun Context.onTapSearchButton(query: String) {
    if (query.isBlank()) return
    Timber.d("onTapSearchButton")
    this.openSearch(query)
}

private fun Context.onTapCheckOnYouTubeButton(query: String) {
    if (query.isBlank()) return
    Timber.d("onTapCheckOnYouTubeButton")
    this.searchOnYouTube(query)
}

private fun Context.onTapVisitURLButton(url: String) {
    if (url.isBlank()) return
    Timber.d("onTapVisitURLButton")
    this.openURL(url.dropSpaces().toFullURL())
}

private fun Context.onTapVisitProfileButton(username: String, platformIndex: Int) {
    if (username.isBlank()) return
    Timber.d("onTapVisitProfileButton")
    val platform = URLUtil.Platform.entries.getOrNull(platformIndex) ?: return
    val url = toProfileFullURL(platform, username)
    this.openURL(url)
}

/**
 * @see me.dizzykitty3.androidtoolkitty.utils.URLUtil.Platform
 */
private fun toProfileFullURL(platform: URLUtil.Platform, username: String): String =
    when (platform) {
        URLUtil.Platform.BLUESKY ->
            if (username.contains("."))
                "${platform.prefix}$username" // user custom
            else if (username.isNotBlank())
                "${platform.prefix}${username.dropSpaces()}.bsky.social" // default
            else
                platform.prefix // for app UI display

        URLUtil.Platform.FANBOX,
        URLUtil.Platform.BOOTH,
        URLUtil.Platform.TUMBLR,
        URLUtil.Platform.CARRD -> "${username.dropSpaces()}${platform.prefix}"

        URLUtil.Platform.YOUTUBE_SEARCH -> "${platform.prefix}${username.trim()}"

        else -> "${platform.prefix}${username.dropSpaces()}"
    }

private fun isInvalid(platform: URLUtil.Platform, username: String): Boolean =
    platform == URLUtil.Platform.X
            && username.isNotBlank()
            && username.dropSpaces().isInvalidUsername()

private fun isValid(platform: URLUtil.Platform, username: String): Boolean =
    !isInvalid(platform, username)

private fun isCaseSensitive(platform: URLUtil.Platform): Boolean =
    platform == URLUtil.Platform.LIT_LINK

@Composable
private fun CheckAppOnMarket() {
    val view = LocalView.current
    val focus = LocalFocusManager.current
    val haptic = LocalHapticFeedback.current
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
                haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
                packageName = ""
            }
        }
    )
    Row(
        Modifier.horizontalScroll(rememberScrollState()),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton({
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            focus.clearFocus()
            view.context.checkOnMarket(packageName)
        }
        ) {
            Text(stringResource(R.string.open_on_google_play))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(R.string.check_app_on_market),
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
            )
        }
        ButtonDivider()
        TextButton({
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            focus.clearFocus()
            view.context.checkOnMarket(packageName, false)
        }
        ) {
            Text(stringResource(R.string.open_on_other_markets))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(R.string.open_on_other_markets),
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
            )
        }
    }
    WhatIsPackageName()
}

@Composable
private fun WhatIsPackageName() {
    val view = LocalView.current
    val haptic = LocalHapticFeedback.current

    TextButton({
        haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
        view.context.openURL(WHAT_IS_PACKAGE_NAME_URL)
    }) {
        Text(stringResource(R.string.what_is_package_name))
        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = stringResource(R.string.what_is_package_name),
            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.3F)
        )
    }
}