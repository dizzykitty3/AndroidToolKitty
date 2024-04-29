package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FontDownload
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomGroupDivider

@Composable
fun FontWeightCard() {
    CustomCard(
        icon = Icons.Outlined.FontDownload,
        title = R.string.font_weight
    ) {
        Thin(id = R.string.w1)
        ExtraLight(id = R.string.w2)
        Light(id = R.string.w3)
        Normal(id = R.string.w4)
        Medium(id = R.string.w5)
        SemiBold(id = R.string.w6)
        Bold(id = R.string.w7)
        ExtraBold(id = R.string.w8)
        Black(id = R.string.w9)
        CustomGroupDivider()
        Row {
            Thin(id = R.string.a)
            ExtraLight(id = R.string.a)
            Light(id = R.string.a)
            Normal(id = R.string.a)
            Medium(id = R.string.a)
            SemiBold(id = R.string.a)
            Bold(id = R.string.a)
            ExtraBold(id = R.string.a)
            Black(id = R.string.a)
        }
        Row {
            Thin(id = R.string.chinese_que)
            ExtraLight(id = R.string.chinese_que)
            Light(id = R.string.chinese_que)
            Normal(id = R.string.chinese_que)
            Medium(id = R.string.chinese_que)
            SemiBold(id = R.string.chinese_que)
            Bold(id = R.string.chinese_que)
            ExtraBold(id = R.string.chinese_que)
            Black(id = R.string.chinese_que)
        }
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