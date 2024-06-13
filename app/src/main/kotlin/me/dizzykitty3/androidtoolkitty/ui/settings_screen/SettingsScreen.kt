package me.dizzykitty3.androidtoolkitty.ui.settings_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.ui_components.CustomCard
import me.dizzykitty3.androidtoolkitty.ui_components.CustomScreen
import me.dizzykitty3.androidtoolkitty.ui_components.CustomTip
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider

@Composable
fun SettingsScreen(navController: NavHostController) {
    CustomScreen {
        Appearance()
        General()
        Customize(navController = navController)
        CustomCard(title = "Online features") {
            CustomTip("Service Provider Data Privacy Disclaimer")
            UserSyncSection()
            GroupDivider()
            RuleUpdateSection()
        }
        About(navController)
    }
}