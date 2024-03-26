package me.dizzykitty3.androidtoolkitty.view.card

import android.content.ContentResolver
import android.content.Context
import android.provider.Settings
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSystemSettingsButton
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.common.util.OsVersion
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

private const val SETTING_1 = "setting_display"
private const val SETTING_2 = "setting_auto_rotate"
private const val SETTING_3 = "setting_bluetooth"
private const val SETTING_4 = "setting_default_apps"
private const val SETTING_5 = "setting_battery_optimization"
private const val SETTING_6 = "setting_caption"
private const val SETTING_7 = "setting_locale"
private const val SETTING_8 = "setting_date_and_time"
private const val SETTING_9 = "setting_developer"

@Composable
fun SystemSettingsCard() {
    val c = LocalContext.current
    CustomCard(
        icon = Icons.Outlined.Settings,
        title = c.getString(R.string.android_system_settings)
    ) {
        val isShowSetting1 = SettingsViewModel().getCardShowedState(c, SETTING_1)
        val isShowSetting2 = SettingsViewModel().getCardShowedState(c, SETTING_2)
        val isShowSetting3 = SettingsViewModel().getCardShowedState(c, SETTING_3)
        val isShowSetting4 = SettingsViewModel().getCardShowedState(c, SETTING_4)
        val isShowSetting5 = SettingsViewModel().getCardShowedState(c, SETTING_5)
        val isShowSetting6 = SettingsViewModel().getCardShowedState(c, SETTING_6)
        val isShowSetting7 = SettingsViewModel().getCardShowedState(c, SETTING_7)
        val isShowSetting8 = SettingsViewModel().getCardShowedState(c, SETTING_8)
        val isShowSetting9 = SettingsViewModel().getCardShowedState(c, SETTING_9)
        if (!checkIsAutoTime(c)) {
            CustomTip(
                text = c.getString(R.string.set_time_automatically_is_off_tip)
            )
        }
        if (isShowSetting1 || isShowSetting2 || isShowSetting3 || isShowSetting4 || isShowSetting5 || isShowSetting6) {
            Text(
                text = c.getString(R.string.common),
                style = MaterialTheme.typography.titleMedium
            )
            CustomSpacerPadding()
        }
        if (isShowSetting1) {

            CustomSystemSettingsButton(
                settingType = "display",
                buttonText = c.getString(R.string.open_display_settings)
            )
        }
        if (isShowSetting2 && OsVersion.android12()) {
            CustomSystemSettingsButton(
                settingType = "auto_rotate",
                buttonText = c.getString(R.string.open_auto_rotate_settings)
            )
        }
        if (isShowSetting3) {
            CustomSystemSettingsButton(
                settingType = "bluetooth",
                buttonText = c.getString(R.string.open_bluetooth_settings)
            )
        }
        if (isShowSetting4) {
            CustomSystemSettingsButton(
                settingType = "manage_default_apps",
                buttonText = c.getString(R.string.open_default_apps_settings)
            )
        }
        if (isShowSetting5) {
            CustomSystemSettingsButton(
                settingType = "ignore_battery_optimization",
                buttonText = c.getString(R.string.open_battery_optimization_settings)
            )
        }
        if (isShowSetting6) {
            CustomSystemSettingsButton(
                settingType = "captioning",
                buttonText = c.getString(R.string.open_caption_preferences)
            )
        }
        if ((isShowSetting1 || isShowSetting2 || isShowSetting3 || isShowSetting4 || isShowSetting5 || isShowSetting6)
            && (isShowSetting7 || isShowSetting8 || isShowSetting9)
        ) {
            CustomSpacerPadding()
            HorizontalDivider()
            CustomSpacerPadding()
        }
        if (isShowSetting7 || isShowSetting8 || isShowSetting9) {
            Text(
                text = c.getString(R.string.debugging),
                style = MaterialTheme.typography.titleMedium
            )
            CustomSpacerPadding()
        }
        if (isShowSetting7) {
            CustomSystemSettingsButton(
                settingType = "locale",
                buttonText = c.getString(R.string.open_language_settings)
            )
        }
        if (isShowSetting8) {
            CustomSystemSettingsButton(
                settingType = "date",
                buttonText = c.getString(R.string.open_date_and_time_settings)
            )
        }
        if (isShowSetting9) {
            CustomSystemSettingsButton(
                settingType = "development_settings",
                buttonText = c.getString(R.string.open_developer_options)
            )
        }
    }
}

private fun checkIsAutoTime(c: Context): Boolean {
    val contentResolver: ContentResolver = c.contentResolver
    val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
    return isAutoTime == 1
}