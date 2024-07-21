package me.dizzykitty3.androidtoolkitty.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.CardShowMore
import me.dizzykitty3.androidtoolkitty.ui.components.ScrollableItalicText
import me.dizzykitty3.androidtoolkitty.ui.components.ScrollableText

@Composable
fun AndroidVersions() {
    Card(R.string.android_versions, Icons.Outlined.Android) {
        RecentVersions()

        var showMore by remember { mutableStateOf(false) }
        CardShowMore(
            showMore = showMore,
            onDismissRequest = { showMore = false },
            onShowMoreClick = { showMore = true }
        ) {
            EarlyVersions()
        }
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
            ScrollableItalicText(R.string.api_35)
            ScrollableItalicText(R.string.api_34)
            ScrollableItalicText(R.string.api_33)
            ScrollableItalicText(R.string.api_32)
            ScrollableItalicText(R.string.api_31)
        }
    }
}

@Composable
private fun EarlyVersions() {
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
            ScrollableItalicText(R.string.api_30)
            ScrollableItalicText(R.string.api_29)
            ScrollableItalicText(R.string.api_28)
            ScrollableItalicText(R.string.api_27)
            ScrollableItalicText(R.string.api_26)
            ScrollableItalicText(R.string.api_25)
            ScrollableItalicText(R.string.api_24)
            ScrollableItalicText(R.string.api_23)
            ScrollableItalicText(R.string.api_22)
            ScrollableItalicText(R.string.api_21)
            ScrollableItalicText(R.string.api_20)
            ScrollableItalicText(R.string.api_19)
            ScrollableItalicText(R.string.api_18)
            ScrollableItalicText(R.string.api_17)
            ScrollableItalicText(R.string.api_16)
            ScrollableItalicText(R.string.api_15)
            ScrollableItalicText(R.string.api_14)
            ScrollableItalicText(R.string.api_13)
            ScrollableItalicText(R.string.api_12)
            ScrollableItalicText(R.string.api_11)
            ScrollableItalicText(R.string.api_10)
            ScrollableItalicText(R.string.api_9)
            ScrollableItalicText(R.string.api_8)
            ScrollableItalicText(R.string.api_7)
        }
    }
}