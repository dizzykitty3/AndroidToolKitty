package me.dizzykitty3.androidtoolkitty.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Shop
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextDecoration
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.domain.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.checkOnMarket
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.ClearInput
import me.dizzykitty3.androidtoolkitty.ui.components.SpacerPadding

@Composable
fun AppMarket() {
    Card(
        icon = Icons.Outlined.Shop,
        title = R.string.check_app_on_market
    ) {
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
                ClearInput(text = packageName) {
                    view.hapticFeedback()
                    packageName = ""
                }
            }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {
            TextButton(
                onClick = {
                    view.hapticFeedback()
                    focus.clearFocus()
                    view.context.checkOnMarket(packageName)
                }
            ) {
                Text(text = stringResource(R.string.open_on_google_play))
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = stringResource(id = R.string.check_app_on_market),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Text("|")
            TextButton(
                onClick = {
                    view.hapticFeedback()
                    focus.clearFocus()
                    view.context.checkOnMarket(packageName, false)
                }
            ) {
                Text(text = stringResource(R.string.open_on_other_markets))
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = stringResource(id = R.string.open_on_other_markets),
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
            Text("|")
            SpacerPadding()
            WhatIsPackageName()
        }
    }
}

@Composable
private fun WhatIsPackageName() {
    Row {
        val view = LocalView.current
        val linkURL = "https://support.google.com/admob/answer/9972781"

        Text(
            text = buildAnnotatedString {
                append(stringResource(R.string.whats))
                append(" ")
            }
        )

        Row(
            modifier = Modifier.clickable(
                onClick = {
                    view.hapticFeedback()
                    view.context.openURL(linkURL)
                }
            )
        ) {
            Text(
                text = stringResource(R.string.package_name),
                textDecoration = TextDecoration.Underline
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(R.string.content_description_link_icon_whats_package_name)
            )
        }
    }
}