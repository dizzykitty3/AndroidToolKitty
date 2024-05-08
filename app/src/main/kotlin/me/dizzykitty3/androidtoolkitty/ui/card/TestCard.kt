package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard

@Composable
fun TestCard() {
    CustomCard(title = "QR Code", icon = Icons.Outlined.QrCode) {
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "QR Code generator")
        }
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "QR Code scanner")
        }
    }
}