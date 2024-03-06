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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import me.dizzykitty3.androidtoolkitty.Utils.calculateDaysPassed
import me.dizzykitty3.androidtoolkitty.Utils.calculateTotalDaysInYear
import me.dizzykitty3.androidtoolkitty.Utils.calculateYearProgress
import me.dizzykitty3.androidtoolkitty.Utils.debugLog
import me.dizzykitty3.androidtoolkitty.Utils.displayYearProgressPercentage
import me.dizzykitty3.androidtoolkitty.Utils.onClearClipboardButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickCheckSetTimeAutomaticallyButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickConvertButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickOpenGoogleMapsButton
import me.dizzykitty3.androidtoolkitty.Utils.onClickVisitButton
import me.dizzykitty3.androidtoolkitty.Utils.onVisitProfile
import me.dizzykitty3.androidtoolkitty.Utils.openCertainAppOnPlayStore
import me.dizzykitty3.androidtoolkitty.Utils.openUrl
import me.dizzykitty3.androidtoolkitty.ui.component.CustomAnimatedProgressIndicator
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCardSpacePadding
import me.dizzykitty3.androidtoolkitty.ui.component.CustomGradientGreetingText
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSystemSettingsButton
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
        if (hasFocus) ClipboardUtils(this).clearClipboard()
    }

    override fun onStop() {
        super.onStop()
        currentFocus?.clearFocus()
        debugLog("focus cleared")
    }
}

@Composable
fun MainLayout() {
    val cardPadding = Modifier.padding(dimensionResource(id = R.dimen.padding_card_content))
    LazyColumn(
        modifier = cardPadding
    ) {
        item { CustomGradientGreetingText() }
        item { CustomCardSpacePadding() }
        item { YearProgressCard() }
        item { CustomCardSpacePadding() }
        item { ClipboardCard() }
        item { CustomCardSpacePadding() }
        item { URLCard() }
        item { CustomCardSpacePadding() }
        item { SocialMediaProfileCard() }
        item { CustomCardSpacePadding() }
        item { SystemSettingsCard() }
        item { CustomCardSpacePadding() }
        item { UnicodeCard() }
        item { CustomCardSpacePadding() }
        item { GoogleMapsCard() }
        item { CustomCardSpacePadding() }
        item { OpenCertainAppOnPlayStoreCard() }
        item { CustomCardSpacePadding() }
        item { TestCard() }
        item { CustomCardSpacePadding() }
    }
}

/**
 * Displays year progress indicator
 */
@Composable
fun YearProgressCard() {
    CustomCard(title = LocalContext.current.getString(R.string.year_progress)) {
        var isShowPercentage by remember { mutableStateOf(true) }
        CustomSpacerPadding()
        CustomAnimatedProgressIndicator()
        CustomSpacerPadding()
        val textToShow =
            if (isShowPercentage)
                "${(displayYearProgressPercentage(calculateYearProgress()))}%"
            else
                "${calculateDaysPassed()} / ${calculateTotalDaysInYear()} / ${calculateTotalDaysInYear() - calculateDaysPassed()} days remaining"
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
    CustomCard(title = LocalContext.current.getString(R.string.clipboard)) {
        Button(
            onClick = {
                onClearClipboardButton()
            }
        ) {
            Text(text = LocalContext.current.getString(R.string.clear_clipboard))
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
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.url)) {
        var url by remember { mutableStateOf("") }
        Text(
            text = buildAnnotatedString {
                append(c.getString(R.string.url_input_hint_1))
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append(" www. ")
                }
                append(c.getString(R.string.url_input_hint_2))
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append(" .com ")
                }
                append(c.getString(R.string.url_input_hint_3))
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append(" .net ")
                }
                append(c.getString(R.string.url_input_hint_4))
            }
        )
        OutlinedTextField(
            value = url,
            onValueChange = { url = it },
            label = { Text(c.getString(R.string.url)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onClickVisitButton(url)
                }
            )
        )
        CustomSpacerPadding()
        Button(
            onClick = { onClickVisitButton(url) }
        ) {
            Text(text = c.getString(R.string.visit))
        }
    }
}

/**
 * Visit user profile with given username and platform
 */
@Composable
fun SocialMediaProfileCard() {
    CustomCard(title = "Social Media Profile") {
        var username by remember { mutableStateOf("") }
        var platform by remember { mutableStateOf("") }
        val c = LocalContext.current
        Text(text = "Visit profile with id or username")
        var selectedPlatform by remember { mutableStateOf("") }
        val platformOptions = listOf(
            "GitHub", "X (Twitter)"
        )
        OutlinedTextField(
            value = platform,
            onValueChange = { platform = it },
            label = { Text("platform") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onVisitProfile(username, platform)
                }
            )
        )
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("username") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    onVisitProfile(username, platform)
                }
            )
        )
        CustomSpacerPadding()
        Button(
            onClick = { onVisitProfile(username, platform) }
        ) {
            Text(text = c.getString(R.string.visit))
        }
    }
}

/**
 * Open certain system setting pages
 */
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

/**
 * Convert Unicode to characters and vice versa
 */
@Composable
fun UnicodeCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.unicode)) {
        var unicode by remember { mutableStateOf("") }
        val characters = remember { mutableStateOf("") }
        Text(
            text = buildAnnotatedString {
                append(c.getString(R.string.unicode_input_hint))
                withStyle(
                    style = SpanStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append(" 00610062")
                }
            }
        )
        Row {
            OutlinedTextField(
                value = unicode,
                onValueChange = { unicode = it },
                label = { Text(c.getString(R.string.unicode)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions(
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
                label = { Text(c.getString(R.string.character)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = dimensionResource(id = R.dimen.padding_spacer))
            )
        }
        CustomSpacerPadding()
        Button(
            onClick = {
                onClickConvertButton(unicode, characters)
            }
        ) {
            Text(text = c.getString(R.string.convert))
        }
    }
}

/**
 * Opens Google Maps with the specified latitude and longitude
 */
@Composable
fun GoogleMapsCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.google_maps)) {
        var latitude by remember { mutableStateOf("") }
        var longitude by remember { mutableStateOf("") }
        Row {
            OutlinedTextField(
                value = latitude,
                onValueChange = { latitude = it },
                label = { Text(c.getString(R.string.latitude)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(end = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions(
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
                label = { Text(c.getString(R.string.longitude)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = dimensionResource(id = R.dimen.padding_spacer)),
                keyboardOptions = KeyboardOptions(
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
        CustomSpacerPadding()
        Button(
            onClick = {
                onClickOpenGoogleMapsButton(latitude, longitude)
            }
        ) {
            Text(text = c.getString(R.string.open_google_maps))
        }
    }
}

/**
 * Open a certain app on Google Play Store
 */
@Composable
fun OpenCertainAppOnPlayStoreCard() {
    val c = LocalContext.current
    CustomCard(title = c.getString(R.string.open_app_on_google_play)) {
        var packageName by remember { mutableStateOf("") }
        val linkUrl = "https://support.google.com/admob/answer/9972781"
        OutlinedTextField(
            value = packageName,
            onValueChange = { packageName = it },
            label = { Text(c.getString(R.string.package_name_or_search)) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    openCertainAppOnPlayStore(packageName)
                }
            )
        )
        CustomSpacerPadding()
        Row {
            Text(
                text = buildAnnotatedString {
                    append(c.getString(R.string.whats))
                    append(" ")
                }
            )
            Row(
                modifier = Modifier.clickable(
                    onClick = { openUrl(linkUrl) }
                )
            ) {
                Text(
                    text = c.getString(R.string.package_name),
                    textDecoration = TextDecoration.Underline
                )
                Icon(
                    imageVector = Icons.Outlined.ArrowOutward,
                    contentDescription = c.getString(R.string.content_description_link_icon_whats_package_name)
                )
            }
        }
        CustomSpacerPadding()
        Button(
            onClick = {
                openCertainAppOnPlayStore(packageName)
            }
        ) {
            Text(text = c.getString(R.string.open_on_google_play))
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
        CustomSpacerPadding()
        Text(
            text = "Default 中文测试 日本語テスト",
            fontFamily = FontFamily.Default
        )
        Text(
            text = "SansSerif 中文测试 日本語テスト",
            fontFamily = FontFamily.SansSerif
        )
        Text(
            text = "Serif 中文测试 日本語テスト",
            fontFamily = FontFamily.Serif
        )
        Text(
            text = "Monospace 中文测试 日本語テスト",
            fontFamily = FontFamily.Monospace
        )
        Text(
            text = "Cursive 中文测试 日本語テスト",
            fontFamily = FontFamily.Cursive
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