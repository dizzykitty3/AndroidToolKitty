package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardSpacePadding
import me.dizzykitty3.androidtoolkitty.view.card.AboutCard
import me.dizzykitty3.androidtoolkitty.view.card.ClipboardCard
import me.dizzykitty3.androidtoolkitty.view.card.GoogleMapsCard
import me.dizzykitty3.androidtoolkitty.view.card.GreetingText
import me.dizzykitty3.androidtoolkitty.view.card.OpenAppOnPlayStoreCard
import me.dizzykitty3.androidtoolkitty.view.card.SocialMediaProfileCard
import me.dizzykitty3.androidtoolkitty.view.card.SystemSettingsCard
import me.dizzykitty3.androidtoolkitty.view.card.UnicodeCard
import me.dizzykitty3.androidtoolkitty.view.card.UrlCard
import me.dizzykitty3.androidtoolkitty.view.card.YearProgressCard

@Composable
fun MainLayout() {
    val cardPadding = dimensionResource(id = R.dimen.padding_card_content)
    LazyColumn(
        modifier = Modifier.padding(
            start = cardPadding,
            end = cardPadding
        )
    ) {
        item { CustomCardSpacePadding() }
        item { CustomCardSpacePadding() }
        item { CustomCardSpacePadding() }
        item { CustomCardSpacePadding() }

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

        item { AboutCard() }
        item { CustomCardSpacePadding() }

        item { CustomCardSpacePadding() }
    }
}