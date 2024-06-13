package me.dizzykitty3.androidtoolkitty.ui.settings_screen

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
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
        CustomCard(titleRes = R.string.online_features) {
            CustomTip(R.string.service_provider_privacy_disclaimer)
            UserSyncSection()
            GroupDivider()
            RuleUpdateSection()
        }
        About(navController)
    }
}