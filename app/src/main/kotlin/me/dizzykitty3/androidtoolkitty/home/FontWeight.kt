package me.dizzykitty3.androidtoolkitty.home

import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_FONT_WEIGHT_TEST
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CardSpacePadding
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupTitle
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.utils.StringUtil

@Composable
fun FontWeight(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current
    Card(
        R.string.font_weight_test,
        Icons.Outlined.FontDownload,
        true,
        {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_FONT_WEIGHT_TEST)
        }) {
        FontWeightTest()
    }
}

@Composable
fun FontWeightTestScreen() {
    Screen {
        Card(R.string.font_weight_test, Icons.Outlined.FontDownload) {
            GroupTitle("Font weight test 1")
            FontWeightTest()
            CardSpacePadding()
            GroupTitle("Font weight test 2")
            RowFontWeightTest()
            CardSpacePadding()
            GroupTitle("Font family test")
            FontFamilyTest()
        }
    }
}

@Composable
private fun FontWeightTest() {
    Thin(R.string.w1)
    ExtraLight(R.string.w2)
    Light(R.string.w3)
    Normal(R.string.w4)
    Medium(R.string.w5)
    SemiBold(R.string.w6)
    Bold(R.string.w7)
    ExtraBold(R.string.w8)
    Black(R.string.w9)
}

@Composable
private fun RowFontWeightTest() {
    val showCJK = StringUtil.sysLangCJK
    RowFontWeightTestImpl(R.string.a)
    if (showCJK) {
        RowFontWeightTestImpl(R.string.chinese_que)
        RowFontWeightTestImpl(R.string.japanese_ki)
    }
}

@Composable
private fun RowFontWeightTestImpl(@StringRes text: Int) {
    Row {
        Thin(text)
        ExtraLight(text)
        Light(text)
        Normal(text)
        Medium(text)
        SemiBold(text)
        Bold(text)
        ExtraBold(text)
        Black(text)
    }
}

@Composable
private fun FontFamilyTest() {
    val showCJK = StringUtil.sysLangCJK
    Column(Modifier.fillMaxWidth()) {
        Text(
            "SansSerif SansSerif SansSerif SansSerif SansSerif SansSerif SansSerif SansSerif",
            fontFamily = FontFamily.SansSerif,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
        Text(
            "Serif Serif Serif Serif Serif Serif Serif Serif Serif Serif Serif Serif Serif",
            fontFamily = FontFamily.Serif,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
        Text(
            "Cursive Cursive Cursive Cursive Cursive Cursive Cursive Cursive Cursive Cursive",
            fontFamily = FontFamily.Cursive,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
        Text(
            "Monospace Monospace Monospace Monospace Monospace Monospace Monospace Monospace",
            fontFamily = FontFamily.Monospace,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
    }
    if (showCJK) {
        Column(
            Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
        ) {
            Row {
                Text(
                    stringResource(R.string.japanese_sentence),
                    fontFamily = FontFamily.SansSerif
                )
                Text(" (SansSerif)")
            }
            Row {
                Text(
                    stringResource(R.string.japanese_sentence),
                    fontFamily = FontFamily.Serif
                )
                Text(" (Serif)")
            }
            Row {
                Text(
                    stringResource(R.string.japanese_sentence),
                    fontFamily = FontFamily.Cursive
                )
                Text(" (Cursive)")
            }
            Row {
                Text(
                    stringResource(R.string.japanese_sentence),
                    fontFamily = FontFamily.Monospace
                )
                Text(" (Monospace)")
            }
        }
    }
}

@Composable
private fun Thin(@StringRes id: Int) = W100(stringResource(id))

@Composable
private fun ExtraLight(@StringRes id: Int) = W200(stringResource(id))

@Composable
private fun Light(@StringRes id: Int) = W300(stringResource(id))

@Composable
private fun Normal(@StringRes id: Int) = W400(stringResource(id))

@Composable
private fun Medium(@StringRes id: Int) = W500(stringResource(id))

@Composable
private fun SemiBold(@StringRes id: Int) = W600(stringResource(id))

@Composable
private fun Bold(@StringRes id: Int) = W700(stringResource(id))

@Composable
private fun ExtraBold(@StringRes id: Int) = W800(stringResource(id))

@Composable
private fun Black(@StringRes id: Int) = W900(stringResource(id))

@Composable
private fun W100(text: String) = Text(text, fontWeight = FontWeight.W100)

@Composable
private fun W200(text: String) = Text(text, fontWeight = FontWeight.W200)

@Composable
private fun W300(text: String) = Text(text, fontWeight = FontWeight.W300)

@Composable
private fun W400(text: String) = Text(text, fontWeight = FontWeight.W400)

@Composable
private fun W500(text: String) = Text(text, fontWeight = FontWeight.W500)

@Composable
private fun W600(text: String) = Text(text, fontWeight = FontWeight.W600)

@Composable
private fun W700(text: String) = Text(text, fontWeight = FontWeight.W700)

@Composable
private fun W800(text: String) = Text(text, fontWeight = FontWeight.W800)

@Composable
private fun W900(text: String) = Text(text, fontWeight = FontWeight.W900)