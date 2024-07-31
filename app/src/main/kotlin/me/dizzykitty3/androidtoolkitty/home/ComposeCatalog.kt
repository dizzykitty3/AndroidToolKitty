package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_COMPOSE_CATALOG
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen

@Composable
fun ComposeCatalog(navController: NavHostController) {
    val haptic = LocalHapticFeedback.current
    Card(
        R.string.compose_catalog,
        hasShowMore = true,
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            navController.navigate(SCR_COMPOSE_CATALOG)
        }
    ) { Text(stringResource(R.string.compose_catalog)) }
}

@Composable
fun ComposeCatalogScreen() {
    Screen {
        Column(Modifier.fillMaxWidth()) {
            Text("Display Large", style = MaterialTheme.typography.displayLarge)
            HorizontalDivider()
            Text("Display Medium", style = MaterialTheme.typography.displayMedium)
            HorizontalDivider()
            Text("Display Small", style = MaterialTheme.typography.displaySmall)
            HorizontalDivider()
            Text("Headline Large", style = MaterialTheme.typography.headlineLarge)
            HorizontalDivider()
            Text("Headline Medium", style = MaterialTheme.typography.headlineMedium)
            HorizontalDivider()
            Text("Headline Small", style = MaterialTheme.typography.headlineSmall)
            HorizontalDivider()
            Text("Title Large", style = MaterialTheme.typography.titleLarge)
            HorizontalDivider()
            Text("Title Medium", style = MaterialTheme.typography.titleMedium)
            HorizontalDivider()
            Text("Title Small", style = MaterialTheme.typography.titleSmall)
            HorizontalDivider()
            Text("Body Large", style = MaterialTheme.typography.bodyLarge)
            HorizontalDivider()
            Text("Body Medium", style = MaterialTheme.typography.bodyMedium)
            HorizontalDivider()
            Text("Body Small", style = MaterialTheme.typography.bodySmall)
            HorizontalDivider()
            Text("Label Large", style = MaterialTheme.typography.labelLarge)
            HorizontalDivider()
            Text("Label Medium", style = MaterialTheme.typography.labelMedium)
            HorizontalDivider()
            Text("Label Small", style = MaterialTheme.typography.labelSmall)
        }
    }
}