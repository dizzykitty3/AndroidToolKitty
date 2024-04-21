package me.dizzykitty3.androidtoolkitty.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.foundation.const.CARD_6
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomBottomPadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomTopPadding
import me.dizzykitty3.androidtoolkitty.ui.card.EditHomeCard
import me.dizzykitty3.androidtoolkitty.ui.card.EditSysSettingCard

@Composable
fun EditHomeScreen() {
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content)
    val sysSettingCard by remember { mutableStateOf(SettingsSharedPref.getCardShowedState(CARD_6)) }

    LazyColumn(
        modifier = Modifier.padding(
            start = cardPadding,
            end = cardPadding
        )
    ) {
        item { CustomTopPadding() }
        item { EditHomeCard() }
        if (sysSettingCard) item { EditSysSettingCard() }
        item { CustomBottomPadding() }
    }
}