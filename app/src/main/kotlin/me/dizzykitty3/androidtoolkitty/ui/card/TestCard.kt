package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.foundation.composable.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.const.QR_CODE_GENERATOR_SCREEN

@Composable
fun TestCard(navController: NavHostController) {
    CustomCard(title = "Test", icon = Icons.Outlined.Terminal) {
        Button(
            onClick = { navController.navigate(QR_CODE_GENERATOR_SCREEN) }
        ) {
            Text(text = "QR Code generator")
        }
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "QR Code scanner")
        }
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "arrange home card")
        }
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "extract apk(s)")
        }
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "install by apk(s)")
        }
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "jump to activity")
        }
        Button(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "to-do list")
        }
    }
}