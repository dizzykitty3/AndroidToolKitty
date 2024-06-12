package me.dizzykitty3.androidtoolkitty.ui.home_screen

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui_components.CustomCard
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider
import me.dizzykitty3.androidtoolkitty.utils.StringUtil

@Preview
@Composable
fun FontWeightCard() {
    CustomCard(
        icon = Icons.Outlined.FontDownload,
        titleRes = R.string.font_weight_test
    ) {
        val showCJK = StringUtil.sysLangCJK()

        Thin(id = R.string.w1)
        ExtraLight(id = R.string.w2)
        Light(id = R.string.w3)
        Normal(id = R.string.w4)
        Medium(id = R.string.w5)
        SemiBold(id = R.string.w6)
        Bold(id = R.string.w7)
        ExtraBold(id = R.string.w8)
        Black(id = R.string.w9)
        GroupDivider()
        RowFontWeightTest(id = R.string.a)
        if (showCJK) {
            RowFontWeightTest(id = R.string.chinese_que)
            RowFontWeightTest(id = R.string.japanese_ki)
        }
        GroupDivider()
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "SansSerif SansSerif SansSerif SansSerif SansSerif SansSerif SansSerif SansSerif",
                fontFamily = FontFamily.SansSerif,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
            Text(
                text = "Serif Serif Serif Serif Serif Serif Serif Serif Serif Serif Serif Serif Serif",
                fontFamily = FontFamily.Serif,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
            Text(
                text = "Cursive Cursive Cursive Cursive Cursive Cursive Cursive Cursive Cursive Cursive",
                fontFamily = FontFamily.Cursive,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
            Text(
                text = "Monospace Monospace Monospace Monospace Monospace Monospace Monospace Monospace",
                fontFamily = FontFamily.Monospace,
                overflow = TextOverflow.Clip,
                maxLines = 1
            )
        }
        if (showCJK) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
            ) {
                Row {
                    Text(
                        text = stringResource(id = R.string.japanese_sentence),
                        fontFamily = FontFamily.SansSerif
                    )
                    Text(text = " (SansSerif)")
                }
                Row {
                    Text(
                        text = stringResource(id = R.string.japanese_sentence),
                        fontFamily = FontFamily.Serif
                    )
                    Text(text = " (Serif)")
                }
                Row {
                    Text(
                        text = stringResource(id = R.string.japanese_sentence),
                        fontFamily = FontFamily.Cursive
                    )
                    Text(text = " (Cursive)")
                }
                Row {
                    Text(
                        text = stringResource(id = R.string.japanese_sentence),
                        fontFamily = FontFamily.Monospace
                    )
                    Text(text = " (Monospace)")
                }
            }
        }
    }
}

@Composable
private fun RowFontWeightTest(@StringRes id: Int) {
    Row {
        Thin(id = id)
        ExtraLight(id = id)
        Light(id = id)
        Normal(id = id)
        Medium(id = id)
        SemiBold(id = id)
        Bold(id = id)
        ExtraBold(id = id)
        Black(id = id)
    }
}

@Composable
private fun Thin(@StringRes id: Int) {
    W100(text = stringResource(id = id))
}

@Composable
private fun ExtraLight(@StringRes id: Int) {
    W200(text = stringResource(id = id))
}

@Composable
private fun Light(@StringRes id: Int) {
    W300(text = stringResource(id = id))
}

@Composable
private fun Normal(@StringRes id: Int) {
    W400(text = stringResource(id = id))
}

@Composable
private fun Medium(@StringRes id: Int) {
    W500(text = stringResource(id = id))
}

@Composable
private fun SemiBold(@StringRes id: Int) {
    W600(text = stringResource(id = id))
}

@Composable
private fun Bold(@StringRes id: Int) {
    W700(text = stringResource(id = id))
}

@Composable
private fun ExtraBold(@StringRes id: Int) {
    W800(text = stringResource(id = id))
}

@Composable
private fun Black(@StringRes id: Int) {
    W900(text = stringResource(id = id))
}

@Composable
private fun W100(text: String) {
    Text(text = text, fontWeight = FontWeight.W100)
}

@Composable
private fun W200(text: String) {
    Text(text = text, fontWeight = FontWeight.W200)
}

@Composable
private fun W300(text: String) {
    Text(text = text, fontWeight = FontWeight.W300)
}

@Composable
private fun W400(text: String) {
    Text(text = text, fontWeight = FontWeight.W400)
}

@Composable
private fun W500(text: String) {
    Text(text = text, fontWeight = FontWeight.W500)
}

@Composable
private fun W600(text: String) {
    Text(text = text, fontWeight = FontWeight.W600)
}

@Composable
private fun W700(text: String) {
    Text(text = text, fontWeight = FontWeight.W700)
}

@Composable
private fun W800(text: String) {
    Text(text = text, fontWeight = FontWeight.W800)
}

@Composable
private fun W900(text: String) {
    Text(text = text, fontWeight = FontWeight.W900)
}