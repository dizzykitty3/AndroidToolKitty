package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

import android.content.Context
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.const.HTTPS
import me.dizzykitty3.androidtoolkitty.foundation.util.IntentUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.StringUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.UrlUtil
import me.dizzykitty3.androidtoolkitty.ui.component.ClearInput
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomDropdownMenu
import me.dizzykitty3.androidtoolkitty.ui.component.CustomGroupDivider
import me.dizzykitty3.androidtoolkitty.ui.component.CustomGroupTitleText
import me.dizzykitty3.androidtoolkitty.ui.component.CustomItalicText
import me.dizzykitty3.androidtoolkitty.ui.component.CustomTip

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
            Text(text = UrlUtil.suffixOf(url))
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

    val platformIndex = SettingsSharedPref.lastTimeSelectedSocialPlatform
    var mPlatformIndex by remember { mutableIntStateOf(platformIndex) }

    val platformList = UrlUtil.Platform.entries.map { stringResource(it.nameResId) }

    CustomGroupTitleText(id = R.string.social_media_profile)

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
            onDone = { onVisitProfileButton(username, mPlatformIndex, context) }
        ),
        trailingIcon = {
            ClearInput(text = username) {
                username = ""
            }
        },
        supportingText = {
            val platform = UrlUtil.Platform.entries[mPlatformIndex]
            Text(
                text = if (needAlterUrlStyle(platform))
                    "$username${UrlUtil.prefixOf(platform)}"
                else
                    "${UrlUtil.prefixOf(platform)}$username",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    )

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
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

        NoPlatformYouNeedHere()
    }
}

@Composable
private fun NoPlatformYouNeedHere() {
    var showDialog by remember { mutableStateOf(false) }

    Text(
        text = buildAnnotatedString {
            CustomItalicText(stringResource(id = R.string.platform_not_added_yet))
        },
        textDecoration = TextDecoration.Underline,
        modifier = Modifier.clickable { showDialog = true })

    var platformNameInput by remember { mutableStateOf("") }
    var platformExampleUrlInput by remember { mutableStateOf("") }

    if (showDialog) {
        AlertDialog(
            icon = { Icon(imageVector = Icons.Outlined.Upload, contentDescription = null) },
            onDismissRequest = {
                // Ignore
            },
            title = { Text(text = stringResource(id = R.string.submit_the_platform_you_need)) },
            text = {
                Column {
                    CustomTip(id = R.string.under_development)
                    OutlinedTextField(
                        value = platformNameInput,
                        onValueChange = { platformNameInput = it },
                        label = { Text(text = stringResource(R.string.platform)) },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            ClearInput(text = platformNameInput) {
                                platformNameInput = ""
                            }
                        },
                    )

                    OutlinedTextField(
                        value = platformExampleUrlInput,
                        onValueChange = { platformExampleUrlInput = it },
                        label = { Text(text = stringResource(id = R.string.platform_example_url)) },
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            ClearInput(text = platformExampleUrlInput) {
                                platformExampleUrlInput = ""
                            }
                        }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showDialog = false },
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
                    onClick = { showDialog = false }
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

    IntentUtil.openSearch(query, context)
    Log.d(TAG, "onClickSearchButton")
}

private fun onClickVisitUrlButton(url: String, context: Context) {
    if (url.isBlank()) return

    IntentUtil.openUrl(UrlUtil.toFullUrl(StringUtil.dropSpaces(url)), context)
    Log.d(TAG, "onClickVisitButton")
}

private fun onVisitProfileButton(
    username: String,
    platformIndex: Int,
    context: Context
) {
    if (username.isBlank()) return

    val platform = UrlUtil.Platform.entries.getOrNull(platformIndex) ?: return

    val prefix = platform.prefix
    val url =
        if (needAlterUrlStyle(platform))
            "${StringUtil.dropSpaces(username)}$prefix"
        else
            "$prefix${StringUtil.dropSpaces(username)}"
    IntentUtil.openUrl(url, context)
    Log.d(TAG, "onVisitProfile")
}

private fun needAlterUrlStyle(platform: UrlUtil.Platform): Boolean =
    platform == UrlUtil.Platform.FANBOX
            || platform == UrlUtil.Platform.BOOTH
            || platform == UrlUtil.Platform.TUMBLR