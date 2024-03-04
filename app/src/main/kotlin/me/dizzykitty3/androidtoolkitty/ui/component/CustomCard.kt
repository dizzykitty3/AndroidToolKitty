package me.dizzykitty3.androidtoolkitty.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R

@Composable
fun CustomCard(title: String, content: @Composable () -> Unit) {
    val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card))
    val spacerPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_spacer))
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
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    Spacer(
                        modifier = spacerPadding
                    )
                    content() // Custom contents here
                }
            }
        }
    }
}