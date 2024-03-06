package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun CustomCard(title: String, content: @Composable () -> Unit) {
    val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_content))
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = cardPadding
        ) {
            var expanded by remember { mutableStateOf(true) }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = title, // Custom title here
                style = TextStyle(
                    fontFamily = FontFamily.Default,
                    fontWeight = FontWeight.Normal,
                    fontSize = 22.sp,
                    lineHeight = 28.0.sp,
                    letterSpacing = 0.0.sp
                )
            )
            AnimatedVisibility(expanded) {
                Column {
                    SpacerPadding()
                    content() // Custom contents here
                }
            }
        }
    }
}