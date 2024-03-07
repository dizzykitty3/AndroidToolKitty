package me.dizzykitty3.androidtoolkitty.ui.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui.card.ClipboardCard
import me.dizzykitty3.androidtoolkitty.ui.card.GoogleMapsCard
import me.dizzykitty3.androidtoolkitty.ui.card.GreetingText
import me.dizzykitty3.androidtoolkitty.ui.card.OpenAppOnPlayStoreCard
import me.dizzykitty3.androidtoolkitty.ui.card.SocialMediaProfileCard
import me.dizzykitty3.androidtoolkitty.ui.card.SystemSettingsCard
import me.dizzykitty3.androidtoolkitty.ui.card.TestCard
import me.dizzykitty3.androidtoolkitty.ui.card.UnicodeCard
import me.dizzykitty3.androidtoolkitty.ui.card.UrlCard
import me.dizzykitty3.androidtoolkitty.ui.card.YearProgressCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCardSpacePadding

@Composable
fun MainLayout() {
    val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_content))
    LazyColumn(
        modifier = cardPadding
    ) {
        item { GreetingText() }
        item { CustomCardSpacePadding() }

        item { YearProgressCard() }
        item { CustomCardSpacePadding() }

        item { ClipboardCard() }
        item { CustomCardSpacePadding() }

        item { UrlCard() }
        item { CustomCardSpacePadding() }

        item { SocialMediaProfileCard() }
        item { CustomCardSpacePadding() }

        item { SystemSettingsCard() }
        item { CustomCardSpacePadding() }

        item { UnicodeCard() }
        item { CustomCardSpacePadding() }

        item { GoogleMapsCard() }
        item { CustomCardSpacePadding() }

        item { OpenAppOnPlayStoreCard() }
        item { CustomCardSpacePadding() }

        item { TestCard() }
        item { CustomCardSpacePadding() }
    }
}
