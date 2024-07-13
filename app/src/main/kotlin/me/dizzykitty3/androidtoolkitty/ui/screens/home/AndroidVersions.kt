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

@Composable
fun AndroidVersions() {
    Card(
        icon = Icons.Outlined.Android,
        title = R.string.android_versions
    ) {
        RecentVersions()
        EarlyVersions()
    }
}

@Composable
private fun RecentVersions() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(0.4f)) {
            AndroidVersion(text = R.string.android_15)
            AndroidVersion(text = R.string.android_14)
            AndroidVersion(text = R.string.android_13)
            AndroidVersion(text = R.string.android_12_l)
            AndroidVersion(text = R.string.android_12)
        }

        Column(
            modifier = Modifier.weight(0.6f)
        ) {
            AndroidApiLevelAndName(text = R.string.api_35)
            AndroidApiLevelAndName(text = R.string.api_34)
            AndroidApiLevelAndName(text = R.string.api_33)
            AndroidApiLevelAndName(text = R.string.api_32)
            AndroidApiLevelAndName(text = R.string.api_31)
        }
    }
}

@Composable
private fun EarlyVersions() {
    val view = LocalView.current
    var expanded by remember { mutableStateOf(false) }
    if (expanded) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(0.4f)) {
                AndroidVersion(text = R.string.android_11)
                AndroidVersion(text = R.string.android_10)
                AndroidVersion(text = R.string.android_9)
                AndroidVersion(text = R.string.android_8_1)
                AndroidVersion(text = R.string.android_8)
                AndroidVersion(text = R.string.android_7_1_1)
                AndroidVersion(text = R.string.android_7)
                AndroidVersion(text = R.string.android_6)
                AndroidVersion(text = R.string.android_5_1)
                AndroidVersion(text = R.string.android_5)
                AndroidVersion(text = R.string.android_4_4_w)
                AndroidVersion(text = R.string.android_4_4)
                AndroidVersion(text = R.string.android_4_3)
                AndroidVersion(text = R.string.android_4_2)
                AndroidVersion(text = R.string.android_4_1)
                AndroidVersion(text = R.string.android_4_0_3)
                AndroidVersion(text = R.string.android_4_0)
                AndroidVersion(text = R.string.android_3_2)
                AndroidVersion(text = R.string.android_3_1)
                AndroidVersion(text = R.string.android_3_0)
                AndroidVersion(text = R.string.android_2_3_3)
                AndroidVersion(text = R.string.android_2_3)
                AndroidVersion(text = R.string.android_2_2)
                AndroidVersion(text = R.string.android_2_1)
                Text(text = "...")
            }

            Column(modifier = Modifier.weight(0.6f)) {
                AndroidApiLevelAndName(text = R.string.api_30)
                AndroidApiLevelAndName(text = R.string.api_29)
                AndroidApiLevelAndName(text = R.string.api_28)
                AndroidApiLevelAndName(text = R.string.api_27)
                AndroidApiLevelAndName(text = R.string.api_26)
                AndroidApiLevelAndName(text = R.string.api_25)
                AndroidApiLevelAndName(text = R.string.api_24)
                AndroidApiLevelAndName(text = R.string.api_23)
                AndroidApiLevelAndName(text = R.string.api_22)
                AndroidApiLevelAndName(text = R.string.api_21)
                AndroidApiLevelAndName(text = R.string.api_20)
                AndroidApiLevelAndName(text = R.string.api_19)
                AndroidApiLevelAndName(text = R.string.api_18)
                AndroidApiLevelAndName(text = R.string.api_17)
                AndroidApiLevelAndName(text = R.string.api_16)
                AndroidApiLevelAndName(text = R.string.api_15)
                AndroidApiLevelAndName(text = R.string.api_14)
                AndroidApiLevelAndName(text = R.string.api_13)
                AndroidApiLevelAndName(text = R.string.api_12)
                AndroidApiLevelAndName(text = R.string.api_11)
                AndroidApiLevelAndName(text = R.string.api_10)
                AndroidApiLevelAndName(text = R.string.api_9)
                AndroidApiLevelAndName(text = R.string.api_8)
                AndroidApiLevelAndName(text = R.string.api_7)
            }
        }
    }

    if (!expanded) {
        TextButton(onClick = {
            view.hapticFeedback()
            expanded = !expanded
        }) {
            Text(text = stringResource(R.string.show_more))
        }
    }
}

@Composable
private fun AndroidApiLevelAndName(@StringRes text: Int) {
    Box(Modifier.horizontalScroll(rememberScrollState())) {
        Text(text = buildAnnotatedString { Italic(text = stringResource(id = text)) })
    }
}

@Composable
private fun AndroidVersion(@StringRes text: Int) {
    Box(Modifier.horizontalScroll(rememberScrollState())) {
        Text(text = stringResource(id = text))
    }
}