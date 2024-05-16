package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.foundation.const.QR_CODE_GENERATOR_SCREEN
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard

@Preview
@Composable
private fun TestCardPreview() {
    val navController = rememberNavController()
    TestCard(navController)
}

@Composable
fun TestCard(navController: NavHostController) {
    CustomCard(title = "Test", icon = Icons.Outlined.Terminal) {
        OutlinedButton(
            onClick = { navController.navigate(QR_CODE_GENERATOR_SCREEN) }
        ) {
            Text(text = "QR Code generator")
        }
        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "QR Code scanner")
        }
        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "arrange home card")
        }
        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "extract apk(s)")
        }
        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "install by apk(s)")
        }
        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "jump to activity")
        }
        OutlinedButton(
            onClick = { /*TODO*/ }
        ) {
            Text(text = "to-do list")
        }
        Text(text = "To-do list")
        Text(text = "Date countdown feature")
        Text(text = "Add license info")
        Text(text = "Add @Preview")
        Text(text = "Tile, shortcut")
        Text(text = "app op with shizuku")
        Text(text = "scaffold ui")
        Text(text = "hilt, room, datastore, coroutines")
    }
}