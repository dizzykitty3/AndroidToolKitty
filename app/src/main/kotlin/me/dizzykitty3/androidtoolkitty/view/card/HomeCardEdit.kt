package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardNoIcon

@Composable
fun HomeCardEdit(navController: NavHostController) {
    val c = LocalContext.current
    CustomCardNoIcon(title = c.getString(R.string.edit_home)) {
        Button(
            onClick = { navController.navigate("HideCardSettingScreen") }
        ) {
            Text(text = c.getString(R.string.customize_my_home_page))
        }
    }
}