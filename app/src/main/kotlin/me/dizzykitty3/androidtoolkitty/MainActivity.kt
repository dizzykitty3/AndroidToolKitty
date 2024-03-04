package me.dizzykitty3.androidtoolkitty

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import me.dizzykitty3.androidtoolkitty.Utils.calculateDaysPassed
import me.dizzykitty3.androidtoolkitty.Utils.calculateTotalDaysInYear
import me.dizzykitty3.androidtoolkitty.Utils.calculateYearProgress
import me.dizzykitty3.androidtoolkitty.Utils.onClearClipboardButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickCheckSetTimeAutomatically
import me.dizzykitty3.androidtoolkitty.Utils.onClickConvertButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickOpenGoogleMapsButton
import me.dizzykitty3.androidtoolkitty.Utils.onOpenSystemSettings
import me.dizzykitty3.androidtoolkitty.Utils.openCertainAppOnPlayStore
import me.dizzykitty3.androidtoolkitty.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                MyLayout()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        ClipboardUtils(this).clearClipboard()
    }
}

@Composable
fun MyLayout() {
    val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card))
    val spacerPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_spacer))
    val context = LocalContext.current

    LazyColumn(
        modifier = cardPadding
    ) {
        item {
            Text(
                text = context.getString(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = spacerPadding)
        }
        item {
            YearProgressCard()
            Spacer(modifier = spacerPadding)
        }
        item {
            ClipboardCard(context)
            Spacer(modifier = spacerPadding)
        }
        item {
            SystemSettingsCard(context)
            Spacer(modifier = spacerPadding)
        }
        item {
            UnicodeCard(context)
            Spacer(modifier = spacerPadding)
        }
        item {
            GoogleMapsCard(context)
            Spacer(modifier = spacerPadding)
        }
        item {
            OpenCertainAppOnPlayStoreCard(context)
            Spacer(modifier = spacerPadding)
        }
        item {
            TestCard()
            Spacer(modifier = spacerPadding)
        }
    }
}

/**
 * Displays year progress indicator
 */
@Composable
fun YearProgressCard() {
    val cardPadding = dimensionResource(id = R.dimen.padding_card)
    val spacerPadding = dimensionResource(id = R.dimen.padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            var expanded by remember { mutableStateOf(true) }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = "Year Progress",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    var showProgressIndicator by remember { mutableStateOf(true) }

                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    LinearProgressIndicator(
                        progress = { calculateYearProgress() },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    val textToShow =
                        if (showProgressIndicator) "${calculateYearProgress() * 100}%" else "${calculateDaysPassed()} / ${calculateTotalDaysInYear()}"
                    Text(
                        text = textToShow,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                showProgressIndicator = !showProgressIndicator
                            }
                    )
                }
            }
        }
    }
}

/**
 * Clear clipboard
 */
@Composable
fun ClipboardCard(context: Context) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card)
    val spacerPadding = dimensionResource(id = R.dimen.padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            var expanded by remember { mutableStateOf(true) }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = "Clipboard",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Button(
                        onClick = {
                            onClearClipboardButton(context)
                        }
                    ) {
                        Text(text = "Clear clipboard")
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        var checked by remember { mutableStateOf(true) }

                        Text(text = "test switch")
                        Spacer(modifier = Modifier.weight(1f))
                        Switch(
                            checked = checked,
                            onCheckedChange = { checked = it }
                        )
                    }
                }
            }
        }
    }
}

/**
 * Open certain system setting pages
 */
@Composable
fun SystemSettingsCard(context: Context) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card)
    val spacerPadding = dimensionResource(id = R.dimen.padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            var expanded by remember { mutableStateOf(true) }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = "Android System Settings",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    SystemSettingsButton(
                        settingType = "display",
                        buttonText = "Open display settings"
                    )
                    SystemSettingsButton(
                        settingType = "auto_rotate",
                        buttonText = "Open auto rotate settings"
                    )
                    SystemSettingsButton(
                        settingType = "locale",
                        buttonText = "Open locale settings"
                    )
                    SystemSettingsButton(
                        settingType = "manage_default_apps",
                        buttonText = "Open default apps settings"
                    )
                    SystemSettingsButton(
                        settingType = "bluetooth",
                        buttonText = "Open bluetooth settings"
                    )
                    Button(
                        onClick = {
                            onClickCheckSetTimeAutomatically(context)
                        }
                    ) {
                        Text(text = "Check is \"set time automatically\" on")
                    }
                    SystemSettingsButton(
                        settingType = "date",
                        buttonText = "Open date settings"
                    )
                    SystemSettingsButton(
                        settingType = "ignore_battery_optimization",
                        buttonText = "Open battery optimization settings"
                    )
                }
            }
        }
    }
}

/**
 * Convert Unicode to characters and vice versa
 */
@Composable
fun UnicodeCard(context: Context) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card)
    val spacerPadding = dimensionResource(id = R.dimen.padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            var expanded by remember { mutableStateOf(true) }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = "Unicode",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    var unicode by remember { mutableStateOf("") }
                    val characters = remember { mutableStateOf("") }

                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Text(
                        text = "Type in the last 4 digits of each Unicode like \"00610062\""
                    )
                    Row {
                        OutlinedTextField(
                            value = unicode,
                            onValueChange = { unicode = it },
                            label = { Text("Unicode") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = spacerPadding),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onClickConvertButton(context, unicode, characters)
                                }
                            )
                        )
                        OutlinedTextField(
                            value = characters.value, // Access the value property of MutableState
                            onValueChange = {}, // This field is read-only
                            label = { Text("Character") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = spacerPadding)
                        )
                    }
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Button(
                        onClick = {
                            onClickConvertButton(context, unicode, characters)
                        }
                    ) {
                        Text(text = "Convert")
                    }
                }
            }
        }
    }
}

/**
 * Opens Google Maps with the specified latitude and longitude
 */
@Composable
fun GoogleMapsCard(context: Context) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card)
    val spacerPadding = dimensionResource(id = R.dimen.padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            var expanded by remember { mutableStateOf(true) }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = "Google Maps",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    var latitude by remember { mutableStateOf("") }
                    var longitude by remember { mutableStateOf("") }

                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Row {
                        OutlinedTextField(
                            value = latitude,
                            onValueChange = { latitude = it },
                            label = { Text("Latitude") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = spacerPadding),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onClickOpenGoogleMapsButton(context, latitude, longitude)
                                }
                            )
                        )
                        OutlinedTextField(
                            value = longitude,
                            onValueChange = { longitude = it },
                            label = { Text("Longitude") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(start = spacerPadding),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    onClickOpenGoogleMapsButton(context, latitude, longitude)
                                }
                            )
                        )
                    }
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Button(
                        onClick = {
                            onClickOpenGoogleMapsButton(context, latitude, longitude)
                        }
                    ) {
                        Text(text = "Open Google Maps")
                    }
                }
            }
        }
    }
}

/**
 * Open a certain app on Google Play Store
 */
@Composable
fun OpenCertainAppOnPlayStoreCard(context: Context) {
    val cardPadding = dimensionResource(id = R.dimen.padding_card)
    val spacerPadding = dimensionResource(id = R.dimen.padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            var expanded by remember { mutableStateOf(true) }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = "Open app on Google Play",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    var packageName by remember { mutableStateOf("") }

                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    OutlinedTextField(
                        value = packageName,
                        onValueChange = { packageName = it },
                        label = { Text("Package Name") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                openCertainAppOnPlayStore(context, packageName)
                            }
                        )
                    )
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Button(
                        onClick = {
                            openCertainAppOnPlayStore(context, packageName)
                        }
                    ) {
                        Text(text = "Open on Google Play")
                    }
                }
            }
        }
    }
}

/**
 * Integer variable recomposition feature test
 */
@Composable
fun TestCard() {
    val cardPadding = dimensionResource(id = R.dimen.padding_card)
    val spacerPadding = dimensionResource(id = R.dimen.padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            var expanded by remember { mutableStateOf(true) }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = "test",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    var clicks by remember { mutableIntStateOf(0) }

                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    ClickCounter(
                        clicks = clicks,
                        onClick = {
                            clicks++
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ClickCounter(clicks: Int, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(
            text = "I've been clicked $clicks times"
        )
    }
}

// Example code: compose clickable card
@Suppress("unused")
@Composable
fun Example() {
    val cardPadding = dimensionResource(id = R.dimen.padding_card)
    val spacerPadding = dimensionResource(id = R.dimen.padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(cardPadding)
        ) {
            var expanded by remember { mutableStateOf(true) }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { expanded = !expanded },
                text = "// title here",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    // Contents here
                }
            }
        }
    }
}

@Composable
fun SystemSettingsButton(
    settingType: String,
    buttonText: String
) {
    val context: Context = LocalContext.current

    Button(
        onClick = {
            onOpenSystemSettings(context, settingType)
        }
    ) {
        Text(text = buttonText)
    }
}
