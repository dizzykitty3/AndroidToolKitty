package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Android
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_ANDROID_VERSION_HISTORY
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.ScrollableItalicText
import me.dizzykitty3.androidtoolkitty.uicomponents.ScrollableText

@Composable
fun AndroidVersions(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current

    Card(
        title = R.string.android,
        icon = Icons.Outlined.Android,
        hasShowMore = true,
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_ANDROID_VERSION_HISTORY)
        }) {
        RecentVersions()
    }
}

@Composable
fun AndroidVersionHistoryScreen() {
    Screen {
        Card(title = R.string.android, icon = Icons.Outlined.Android) {
            RecentVersions()
            EarlyVersions()
        }
    }
}

@Composable
private fun RecentVersions() {
    Row(Modifier.fillMaxWidth()) {
        Column(Modifier.weight(0.4f)) {
            ScrollableText("Android 15")
            ScrollableText("Android 14")
            ScrollableText("Android 13")
            ScrollableText("Android 12L")
            ScrollableText("Android 12")
        }

        Column(Modifier.weight(0.6f)) {
            ScrollableItalicText("API 35, VanillaIceCream")
            ScrollableItalicText("API 34, UpsideDownCake")
            ScrollableItalicText("API 33, Tiramisu")
            ScrollableItalicText("API 32, Sv2")
            ScrollableItalicText("API 31, S")
        }
    }
}

@Composable
private fun EarlyVersions() {
    Row(Modifier.fillMaxWidth()) {
        Column(Modifier.weight(0.4f)) {
            ScrollableText("Android 11")
            ScrollableText("Android 10")
            ScrollableText("Android 9")
            ScrollableText("Android 8.1")
            ScrollableText("Android 8")
            ScrollableText("Android 7.1.1")
            ScrollableText("Android 7")
            ScrollableText("Android 6")
            ScrollableText("Android 5.1")
            ScrollableText("Android 5")
            ScrollableText("Android 4.4W")
            ScrollableText("Android 4.4")
            ScrollableText("Android 4.3")
            ScrollableText("Android 4.2")
            ScrollableText("Android 4.1")
            ScrollableText("Android 4.0.3")
            ScrollableText("Android 4.0")
            ScrollableText("Android 3.2")
            ScrollableText("Android 3.1")
            ScrollableText("Android 3.0")
            ScrollableText("Android 2.3.3")
            ScrollableText("Android 2.3")
            ScrollableText("Android 2.2")
            ScrollableText("Android 2.1")
            ScrollableText("...")
        }

        Column(Modifier.weight(0.6f)) {
            ScrollableItalicText("API 30, R")
            ScrollableItalicText("API 29, Q")
            ScrollableItalicText("API 28, Pie")
            ScrollableItalicText("API 27, Oreo")
            ScrollableItalicText("API 26, Oreo")
            ScrollableItalicText("API 25, Nougat")
            ScrollableItalicText("API 24, Nougat")
            ScrollableItalicText("API 23, Marshmallow")
            ScrollableItalicText("API 22, Lollipop")
            ScrollableItalicText("API 21, Lollipop")
            ScrollableItalicText("API 20, KitKat Wear")
            ScrollableItalicText("API 19, KitKat")
            ScrollableItalicText("API 18, Jelly Bean")
            ScrollableItalicText("API 17, Jelly Bean")
            ScrollableItalicText("API 16, Jelly Bean")
            ScrollableItalicText("API 15, IceCreamSandwich")
            ScrollableItalicText("API 14, IceCreamSandwich")
            ScrollableItalicText("API 13, Honeycomb")
            ScrollableItalicText("API 12, Honeycomb")
            ScrollableItalicText("API 11, Honeycomb")
            ScrollableItalicText("API 10, Gingerbread")
            ScrollableItalicText("API 9, Gingerbread")
            ScrollableItalicText("API 8, Froyo")
            ScrollableItalicText("API 7, Eclair")
        }
    }
}