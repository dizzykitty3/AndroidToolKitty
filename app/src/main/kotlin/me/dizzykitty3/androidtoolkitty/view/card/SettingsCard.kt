package me.dizzykitty3.androidtoolkitty.view.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import me.dizzykitty3.androidtoolkitty.Actions
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomStaticCard
import me.dizzykitty3.androidtoolkitty.viewmodel.SettingsViewModel

@Composable
fun SettingsCard() {
    val c = LocalContext.current
    CustomStaticCard(
        title = c.getString(R.string.settings)
    ) {
        val mIsAutoClearClipboard = SettingsViewModel(c).getIsAutoClearClipboard()
        var isAutoClearClipboard by remember { mutableStateOf(mIsAutoClearClipboard) }
        val mIsSingleHandMode = SettingsViewModel(c).getIsSingleHandMode()
        var isSingleHandMode by remember { mutableStateOf(mIsSingleHandMode) }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isAutoClearClipboard = !isAutoClearClipboard
                SettingsViewModel(c).setIsAutoClearClipboard(isAutoClearClipboard)
            }
        ) {
            Text(
                text = c.getString(R.string.clear_clipboard_on_launch)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isAutoClearClipboard,
                onCheckedChange = {
                    isAutoClearClipboard = it
                    SettingsViewModel(c).setIsAutoClearClipboard(it)
                }
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                isSingleHandMode = !isSingleHandMode
                SettingsViewModel(c).setIsSingleHandMode(isSingleHandMode)
            }
        ) {
            Text(
                text = c.getString(R.string.single_hand_mode)
            )
            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = isSingleHandMode,
                onCheckedChange = {
                    isSingleHandMode = it
                    SettingsViewModel(c).setIsSingleHandMode(it)
                }
            )
        }
        CustomSpacerPadding()
        HorizontalDivider()
        CustomSpacerPadding()
        CustomSpacerPadding()
        Button(
            onClick = { Actions.onClickAllCardsButton(isExpand = false) }
        ) {
            Text(
                text = c.getString(R.string.collapse_all_cards)
            )
        }
        Button(
            onClick = { Actions.onClickAllCardsButton(isExpand = true) }
        ) {
            Text(
                text = c.getString(R.string.expand_all_cards)
            )
        }
    }
}