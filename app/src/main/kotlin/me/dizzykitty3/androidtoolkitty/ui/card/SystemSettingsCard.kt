package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSystemSettingsButton
import me.dizzykitty3.androidtoolkitty.util.Utils.onClickCheckSetTimeAutomaticallyButton

@Composable
fun SystemSettingsCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.android_system_settings)) {
        CustomSystemSettingsButton(
            settingType = "display",
            buttonText = c.getString(R.string.open_display_settings)
        )
        CustomSystemSettingsButton(
            settingType = "auto_rotate",
            buttonText = c.getString(R.string.open_auto_rotate_settings)
        )
        CustomSystemSettingsButton(
            settingType = "locale",
            buttonText = c.getString(R.string.open_language_settings)
        )
        CustomSystemSettingsButton(
            settingType = "manage_default_apps",
            buttonText = c.getString(R.string.open_default_apps_settings)
        )
        CustomSystemSettingsButton(
            settingType = "bluetooth",
            buttonText = c.getString(R.string.open_bluetooth_settings)
        )
        Button(
            onClick = {
                onClickCheckSetTimeAutomaticallyButton()
            }
        ) {
            Text(text = c.getString(R.string.check_is_set_time_automatically_on))
        }
        CustomSystemSettingsButton(
            settingType = "date",
            buttonText = c.getString(R.string.open_date_and_time_settings)
        )
        CustomSystemSettingsButton(
            settingType = "ignore_battery_optimization",
            buttonText = c.getString(R.string.open_battery_optimization_settings)
        )
    }
}