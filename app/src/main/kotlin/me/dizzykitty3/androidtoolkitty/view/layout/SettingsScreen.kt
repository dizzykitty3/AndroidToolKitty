package me.dizzykitty3.androidtoolkitty.view.layout

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.common.ui.component.CustomCardSpacePadding
import me.dizzykitty3.androidtoolkitty.view.card.AboutCard

@Composable
fun SettingsScreen(navController: NavHostController) {
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

        // test
        item {
            Button(
                onClick = { navController.navigate("HomeScreen") }
            ) {
                Text(
                    text = "Go to Home Screen"
                )
            }
        }
        item { CustomCardSpacePadding() }

        item { AboutCard() }
        item { CustomCardSpacePadding() }

        item { CustomCardSpacePadding() }
    }
}