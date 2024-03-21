package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomStaticCard

@Composable
fun HomeCardEdit(navController: NavHostController) {
    val c = LocalContext.current
    CustomStaticCard(title = "Edit home") {
        Button(
            onClick = {
                navController.navigate("HideCardSettingScreen")
            }
        ) {
            Text(
                text = "Customize my home page"
            )
        }
    }
}