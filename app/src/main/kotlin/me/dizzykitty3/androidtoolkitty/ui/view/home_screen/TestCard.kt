package me.dizzykitty3.androidtoolkitty.ui.view.home_screen

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import me.dizzykitty3.androidtoolkitty.QR_CODE_GENERATOR_SCREEN
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
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            OutlinedButton(
                onClick = { navController.navigate(QR_CODE_GENERATOR_SCREEN) }
            ) {
                Text(text = "QR Code generator")
            }
        }
    }
}