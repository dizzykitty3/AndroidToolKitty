package me.dizzykitty3.androidtoolkitty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import me.dizzykitty3.androidtoolkitty.Utils.calculateDaysPassed
import me.dizzykitty3.androidtoolkitty.Utils.calculateTotalDaysInYear
import me.dizzykitty3.androidtoolkitty.Utils.calculateYearProgress
import me.dizzykitty3.androidtoolkitty.Utils.onClearClipboardButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickCheckSetTimeAutomaticallyButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickConvertButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickOpenGoogleMapsButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickVisitButton
import me.dizzykitty3.androidtoolkitty.Utils.openCertainAppOnPlayStore
import me.dizzykitty3.androidtoolkitty.Utils.openUrl
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.SystemSettingsButton
import me.dizzykitty3.androidtoolkitty.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.init(this)
        setContent {
            MyApplicationTheme {
                MainLayout()
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        ClipboardUtils(this).clearClipboard()
    }
}

@Composable
fun MainLayout() {
    val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card))
    val spacerPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_spacer))
    LazyColumn(
        modifier = cardPadding
    ) {
        item {
            Text(
                text = LocalContext.current.getString(R.string.app_name),
                style = MaterialTheme.typography.headlineLarge
            )
            Spacer(modifier = spacerPadding)
        }
        item {
            YearProgressCard()
            Spacer(modifier = spacerPadding)
        }
        item {
            ClipboardCard()
            Spacer(modifier = spacerPadding)
        }
        item {
            URLCard()
            Spacer(modifier = spacerPadding)
        }
        item {
            SystemSettingsCard()
            Spacer(modifier = spacerPadding)
        }
        item {
            UnicodeCard()
            Spacer(modifier = spacerPadding)
        }
        item {
            GoogleMapsCard()
            Spacer(modifier = spacerPadding)
        }
        item {
            OpenCertainAppOnPlayStoreCard()
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
    CustomCard(title = "Year progress") {
        val spacerPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_spacer))
        var isShowPercentage by remember { mutableStateOf(true) }
        LinearProgressIndicator(
            progress = { calculateYearProgress() },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(
            modifier = spacerPadding
        )
        val textToShow =
            if (isShowPercentage) "${calculateYearProgress() * 100}%" else "${calculateDaysPassed()} / ${calculateTotalDaysInYear()}"
        Text(
            text = textToShow,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    isShowPercentage = !isShowPercentage
                }
        )
    }
}

/**
 * Clear clipboard
 */
@Composable
fun ClipboardCard() {
    CustomCard(title = "Clipboard") {
        Button(
            onClick = {
                onClearClipboardButton()
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

/**
 * Visit url without inputting prefix and suffix
 */
@Composable
fun URLCard() {
    CustomCard(title = "URL") {
        val spacerPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_spacer))
        var url by remember { mutableStateOf("") }
        Text(
            text = "Visit url without inputting prefix \"https//www.\" and suffix \".com\" (or \".net\", etc.)"
        )
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text("URL") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onClickVisitButton(url)
                }
            )
        )
        Spacer(modifier = spacerPadding)
        Button(
            onClick = { onClickVisitButton(url) }
        ) {
            Text(text = "Visit")
        }
    }
}

/**
 * Open certain system setting pages
 */
@Composable
fun SystemSettingsCard() {
    CustomCard(title = "Android system settings") {
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
                onClickCheckSetTimeAutomaticallyButton()
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

/**
 * Convert Unicode to characters and vice versa
 */
@Composable
fun UnicodeCard() {
    CustomCard(title = "Unicode") {
        val spacerPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_spacer))
        var unicode by remember { mutableStateOf("") }
        val characters = remember { mutableStateOf("") }
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
                    .padding(end = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onClickConvertButton(unicode, characters)
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
                    .padding(start = dimensionResource(id = R.dimen.padding_spacer))
            )
        }
        Spacer(
            modifier = spacerPadding
        )
        Button(
            onClick = {
                onClickConvertButton(unicode, characters)
            }
        ) {
            Text(text = "Convert")
        }
    }
}

/**
 * Opens Google Maps with the specified latitude and longitude
 */
@Composable
fun GoogleMapsCard() {
    CustomCard(title = "Google Map") {
        val spacerPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_spacer))
        var latitude by remember { mutableStateOf("") }
        var longitude by remember { mutableStateOf("") }
        Row {
            OutlinedTextField(
                value = latitude,
                onValueChange = { latitude = it },
                label = { Text("Latitude") },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onClickOpenGoogleMapsButton(latitude, longitude)
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
                    .padding(start = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onClickOpenGoogleMapsButton(latitude, longitude)
                    }
                )
            )
        }
        Spacer(
            modifier = spacerPadding
        )
        Button(
            onClick = {
                onClickOpenGoogleMapsButton(latitude, longitude)
            }
        ) {
            Text(text = "Open Google Maps")
        }
    }
}

/**
 * Open a certain app on Google Play Store
 */
@Composable
fun OpenCertainAppOnPlayStoreCard() {
    CustomCard(title = "Open app on Google Play") {
        val spacerPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_spacer))
        var packageName by remember { mutableStateOf("") }
        val linkUrl = "https://support.google.com/admob/answer/9972781"
        Row {
            Text(
                text = buildAnnotatedString {
                    append("what is a ")
                    pushStringAnnotation(tag = "URL", annotation = linkUrl)
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("package name")
                    }
                    pop()
                }
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = "Link Icon",
                modifier = Modifier.clickable {
                    openUrl(linkUrl)
                }
            )
        }
        OutlinedTextField(
            value = packageName,
            onValueChange = { packageName = it },
            label = { Text("Package name or search") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    openCertainAppOnPlayStore(packageName)
                }
            )
        )
        Spacer(
            modifier = spacerPadding
        )
        Button(
            onClick = {
                openCertainAppOnPlayStore(packageName)
            }
        ) {
            Text(text = "Open on Google Play")
        }
    }
}

/**
 * Integer variable recomposition feature test
 */
@Composable
fun TestCard() {
    CustomCard(title = "test") {
        var clicks by remember { mutableIntStateOf(0) }
        ClickCounter(
            clicks = clicks,
            onClick = {
                clicks++
            }
        )
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