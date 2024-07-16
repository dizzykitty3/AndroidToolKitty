package me.dizzykitty3.androidtoolkitty.ui.screens.home

import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.domain.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.Italic
import me.dizzykitty3.androidtoolkitty.ui.components.ScrollableText

@Composable
fun AndroidVersions() {
    Card(R.string.android_versions, Icons.Outlined.Android) {
        RecentVersions()
        EarlyVersions()
    }
}

@Composable
private fun RecentVersions() {
    Row(Modifier.fillMaxWidth()) {
        Column(Modifier.weight(0.4f)) {
            ScrollableText(R.string.android_15)
            ScrollableText(R.string.android_14)
            ScrollableText(R.string.android_13)
            ScrollableText(R.string.android_12_l)
            ScrollableText(R.string.android_12)
        }

        Column(Modifier.weight(0.6f)) {
            AndroidApiLevelAndName(R.string.api_35)
            AndroidApiLevelAndName(R.string.api_34)
            AndroidApiLevelAndName(R.string.api_33)
            AndroidApiLevelAndName(R.string.api_32)
            AndroidApiLevelAndName(R.string.api_31)
        }
    }
}

@Composable
private fun EarlyVersions() {
    val view = LocalView.current
    var expanded by remember { mutableStateOf(false) }
    if (expanded) {
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.weight(0.4f)) {
                ScrollableText(R.string.android_11)
                ScrollableText(R.string.android_10)
                ScrollableText(R.string.android_9)
                ScrollableText(R.string.android_8_1)
                ScrollableText(R.string.android_8)
                ScrollableText(R.string.android_7_1_1)
                ScrollableText(R.string.android_7)
                ScrollableText(R.string.android_6)
                ScrollableText(R.string.android_5_1)
                ScrollableText(R.string.android_5)
                ScrollableText(R.string.android_4_4_w)
                ScrollableText(R.string.android_4_4)
                ScrollableText(R.string.android_4_3)
                ScrollableText(R.string.android_4_2)
                ScrollableText(R.string.android_4_1)
                ScrollableText(R.string.android_4_0_3)
                ScrollableText(R.string.android_4_0)
                ScrollableText(R.string.android_3_2)
                ScrollableText(R.string.android_3_1)
                ScrollableText(R.string.android_3_0)
                ScrollableText(R.string.android_2_3_3)
                ScrollableText(R.string.android_2_3)
                ScrollableText(R.string.android_2_2)
                ScrollableText(R.string.android_2_1)
                Text("...")
            }

            Column(Modifier.weight(0.6f)) {
                AndroidApiLevelAndName(R.string.api_30)
                AndroidApiLevelAndName(R.string.api_29)
                AndroidApiLevelAndName(R.string.api_28)
                AndroidApiLevelAndName(R.string.api_27)
                AndroidApiLevelAndName(R.string.api_26)
                AndroidApiLevelAndName(R.string.api_25)
                AndroidApiLevelAndName(R.string.api_24)
                AndroidApiLevelAndName(R.string.api_23)
                AndroidApiLevelAndName(R.string.api_22)
                AndroidApiLevelAndName(R.string.api_21)
                AndroidApiLevelAndName(R.string.api_20)
                AndroidApiLevelAndName(R.string.api_19)
                AndroidApiLevelAndName(R.string.api_18)
                AndroidApiLevelAndName(R.string.api_17)
                AndroidApiLevelAndName(R.string.api_16)
                AndroidApiLevelAndName(R.string.api_15)
                AndroidApiLevelAndName(R.string.api_14)
                AndroidApiLevelAndName(R.string.api_13)
                AndroidApiLevelAndName(R.string.api_12)
                AndroidApiLevelAndName(R.string.api_11)
                AndroidApiLevelAndName(R.string.api_10)
                AndroidApiLevelAndName(R.string.api_9)
                AndroidApiLevelAndName(R.string.api_8)
                AndroidApiLevelAndName(R.string.api_7)
            }
        }
    }

    if (!expanded) {
        TextButton({
            view.hapticFeedback()
            expanded = !expanded
        }) { Text(stringResource(R.string.show_more)) }
    }
}

@Composable
private fun AndroidApiLevelAndName(@StringRes text: Int) {
    Box(Modifier.horizontalScroll(rememberScrollState())) {
        Text(buildAnnotatedString { Italic(stringResource(text)) })
    }
}