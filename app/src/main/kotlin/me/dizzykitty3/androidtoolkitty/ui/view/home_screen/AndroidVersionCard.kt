package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.Italic

@Preview
@Composable
fun AndroidVersionCard() {
    CustomCard(
        icon = Icons.Outlined.Android,
        titleRes = R.string.android_versions
    ) {
        RecentVersions()
        EarlyVersions()
    }
}

@Composable
private fun RecentVersions() {
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.weight(0.4f)) {
            AndroidVersion(textRes = R.string.android_15)
            AndroidVersion(textRes = R.string.android_14)
            AndroidVersion(textRes = R.string.android_13)
            AndroidVersion(textRes = R.string.android_12_l)
            AndroidVersion(textRes = R.string.android_12)
        }

        Column(modifier = Modifier.weight(0.6f)) {
            AndroidApiLevelAndName(textRes = R.string.api_35)
            AndroidApiLevelAndName(textRes = R.string.api_34)
            AndroidApiLevelAndName(textRes = R.string.api_33)
            AndroidApiLevelAndName(textRes = R.string.api_32)
            AndroidApiLevelAndName(textRes = R.string.api_31)
        }
    }
}

@Composable
private fun EarlyVersions() {
    var expanded by remember { mutableStateOf(false) }
    if (expanded) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.weight(0.4f)) {
                AndroidVersion(textRes = R.string.android_11)
                AndroidVersion(textRes = R.string.android_10)
                AndroidVersion(textRes = R.string.android_9)
                AndroidVersion(textRes = R.string.android_8_1)
                AndroidVersion(textRes = R.string.android_8)
                AndroidVersion(textRes = R.string.android_7_1_1)
                AndroidVersion(textRes = R.string.android_7)
                AndroidVersion(textRes = R.string.android_6)
                AndroidVersion(textRes = R.string.android_5_1)
                AndroidVersion(textRes = R.string.android_5)
                AndroidVersion(textRes = R.string.android_4_4_w)
                AndroidVersion(textRes = R.string.android_4_4)
                AndroidVersion(textRes = R.string.android_4_3)
                AndroidVersion(textRes = R.string.android_4_2)
                AndroidVersion(textRes = R.string.android_4_1)
                AndroidVersion(textRes = R.string.android_4_0_3)
                AndroidVersion(textRes = R.string.android_4_0)
                AndroidVersion(textRes = R.string.android_3_2)
                AndroidVersion(textRes = R.string.android_3_1)
                AndroidVersion(textRes = R.string.android_3_0)
                AndroidVersion(textRes = R.string.android_2_3_3)
                AndroidVersion(textRes = R.string.android_2_3)
                AndroidVersion(textRes = R.string.android_2_2)
                AndroidVersion(textRes = R.string.android_2_1)
            }

            Column(modifier = Modifier.weight(0.6f)) {
                AndroidApiLevelAndName(textRes = R.string.api_30)
                AndroidApiLevelAndName(textRes = R.string.api_29)
                AndroidApiLevelAndName(textRes = R.string.api_28)
                AndroidApiLevelAndName(textRes = R.string.api_27)
                AndroidApiLevelAndName(textRes = R.string.api_26)
                AndroidApiLevelAndName(textRes = R.string.api_25)
                AndroidApiLevelAndName(textRes = R.string.api_24)
                AndroidApiLevelAndName(textRes = R.string.api_23)
                AndroidApiLevelAndName(textRes = R.string.api_22)
                AndroidApiLevelAndName(textRes = R.string.api_21)
                AndroidApiLevelAndName(textRes = R.string.api_20)
                AndroidApiLevelAndName(textRes = R.string.api_19)
                AndroidApiLevelAndName(textRes = R.string.api_18)
                AndroidApiLevelAndName(textRes = R.string.api_17)
                AndroidApiLevelAndName(textRes = R.string.api_16)
                AndroidApiLevelAndName(textRes = R.string.api_15)
                AndroidApiLevelAndName(textRes = R.string.api_14)
                AndroidApiLevelAndName(textRes = R.string.api_13)
                AndroidApiLevelAndName(textRes = R.string.api_12)
                AndroidApiLevelAndName(textRes = R.string.api_11)
                AndroidApiLevelAndName(textRes = R.string.api_10)
                AndroidApiLevelAndName(textRes = R.string.api_9)
                AndroidApiLevelAndName(textRes = R.string.api_8)
                AndroidApiLevelAndName(textRes = R.string.api_7)
            }
        }
    }

    TextButton(onClick = { expanded = !expanded }) {
        if (!expanded) {
            Text(text = stringResource(R.string.show_more))
        } else {
            Text(text = stringResource(R.string.show_less))
        }
    }
}

@Composable
private fun AndroidApiLevelAndName(@StringRes textRes: Int) {
    Text(
        text = buildAnnotatedString { Italic(text = stringResource(id = textRes)) },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}

@Composable
private fun AndroidVersion(@StringRes textRes: Int) {
    Text(
        text = stringResource(id = textRes),
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
    )
}