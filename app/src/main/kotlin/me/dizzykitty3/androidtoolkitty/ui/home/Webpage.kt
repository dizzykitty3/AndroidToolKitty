package me.dizzykitty3.androidtoolkitty.ui.home

import android.content.Context
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Link
import androidx.compose.material.icons.outlined.Upload
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import me.dizzykitty3.androidtoolkitty.HTTPS
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui_components.Card
import me.dizzykitty3.androidtoolkitty.ui_components.ClearInput
import me.dizzykitty3.androidtoolkitty.ui_components.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.ui_components.CustomTip
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui_components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui_components.Italic
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.StringUtil
import me.dizzykitty3.androidtoolkitty.utils.URLUtil
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

        if (keepShowMore || mShowMore) GroupTitle(id = R.string.search)

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
                settingsSharedPref.haveTappedWebpageCardShowMore = true
            }) {
                Text(text = stringResource(id = R.string.show_more))
            }
        }
    }
}

@Composable
private fun Search() {
    val view = LocalView.current
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
            onDone = { onClickSearchButton(searchQuery, view.context) }
        ),
        trailingIcon = {
            ClearInput(text = searchQuery) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                searchQuery = ""
            }
        },
    )

    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        TextButton(
            onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                onClickSearchButton(searchQuery, view.context)
            }
        ) {
            Text(text = stringResource(R.string.visit))
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }

        TextButton(
            onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                onCheckOnYouTube(searchQuery, view.context)
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
    GroupTitle(id = R.string.webpage)

    val view = LocalView.current
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
            onDone = { onClickVisitURLButton(url, view.context) }
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
        onClickVisitURLButton(url, view.context)
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
    var username by remember { mutableStateOf("") }
    val platformIndex = SettingsSharedPref.lastTimeSelectedSocialPlatform
    var mPlatformIndex by remember { mutableIntStateOf(platformIndex) }
    val platformList = URLUtil.Platform.entries.map { stringResource(it.platform) }

    GroupTitle(id = R.string.social_media_profile)

    CustomDropdownMenu(
        items = platformList,
        onItemSelected = { mPlatformIndex = it },
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
            onDone = { onVisitProfileButton(username, mPlatformIndex, view.context) }
        ),
        trailingIcon = {
            ClearInput(text = username) {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                username = ""
            }
        },
        supportingText = {
            val platform = URLUtil.Platform.entries[mPlatformIndex]
            Text(
                text = toSocialMediaFullURL(platform, username),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    )

    Row(verticalAlignment = Alignment.CenterVertically) {
        TextButton(onClick = {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            onVisitProfileButton(username, mPlatformIndex, view.context)
        }) {
            Text(text = stringResource(R.string.visit))

            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        NoPlatformYouNeedHere()
    }
}

@Composable
private fun NoPlatformYouNeedHere() {
    val view = LocalView.current
    var showDialog by remember { mutableStateOf(false) }

    Text(
        text = buildAnnotatedString { Italic(stringResource(id = R.string.platform_not_added_yet)) },
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable {
            view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
            showDialog = true
        }
    )

    var platformNameInput by remember { mutableStateOf("") }
    var platformExampleURLInput by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            icon = { Icon(imageVector = Icons.Outlined.Upload, contentDescription = null) },
            onDismissRequest = {
                // Ignore
            },
            title = { Text(text = stringResource(id = R.string.submit_the_platform_you_need)) },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    CustomTip(id = R.string.under_development)
                    OutlinedTextField(
                        value = platformNameInput,
                        onValueChange = { platformNameInput = it },
                        label = { Text(text = stringResource(R.string.platform)) },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            ClearInput(text = platformNameInput) {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                platformNameInput = ""
                            }
                        },
                    )
                    OutlinedTextField(
                        value = platformExampleURLInput,
                        onValueChange = { platformExampleURLInput = it },
                        label = { Text(text = stringResource(id = R.string.platform_example_url)) },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            ClearInput(text = platformExampleURLInput) {
                                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                                platformExampleURLInput = ""
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                        showDialog = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = stringResource(android.R.string.ok),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                        showDialog = false
                    }
                ) {
                    Text(
                        text = stringResource(id = android.R.string.cancel),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        )
    }
}

private fun onClickSearchButton(query: String, context: Context) {
    if (query.isBlank()) return
    Timber.d("onClickSearchButton")
    IntentUtil.openSearch(query, context)
}

private fun onCheckOnYouTube(query: String, context: Context) {
    if (query.isBlank()) return
    Timber.d("onCheckOnYouTube")
    IntentUtil.checkOnYouTube(query, context)
}

private fun onClickVisitURLButton(url: String, context: Context) {
    if (url.isBlank()) return
    Timber.d("onClickVisitButton")
    IntentUtil.openURL(URLUtil.toFullURL(StringUtil.dropSpacesAndLowercase(url)), context)
}

private fun onVisitProfileButton(
    username: String,
    platformIndex: Int,
    context: Context
) {
    if (username.isBlank()) return
    Timber.d("onVisitProfile")
    val platform = URLUtil.Platform.entries.getOrNull(platformIndex) ?: return
    val url = toSocialMediaFullURL(platform, username)
    IntentUtil.openURL(url, context)
}

private fun toSocialMediaFullURL(platform: URLUtil.Platform, username: String): String =
    when (platform) {
        URLUtil.Platform.BLUESKY ->
            if (username.isNotBlank())
                "${platform.prefix}${StringUtil.dropSpacesAndLowercase(username)}.bsky.social"
            else
                platform.prefix // for display

        URLUtil.Platform.FANBOX,
        URLUtil.Platform.BOOTH,
        URLUtil.Platform.TUMBLR,
        URLUtil.Platform.CARRD -> "${StringUtil.dropSpacesAndLowercase(username)}${platform.prefix}"

        else -> "${platform.prefix}${StringUtil.dropSpacesAndLowercase(username)}"
    }