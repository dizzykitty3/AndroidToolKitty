package me.dizzykitty3.androidtoolkitty

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.input.KeyboardType
import me.dizzykitty3.androidtoolkitty.Utils.convertUnicodeToCharacter
import me.dizzykitty3.androidtoolkitty.Utils.openGoogleMaps
import me.dizzykitty3.androidtoolkitty.Utils.showToast
import me.dizzykitty3.androidtoolkitty.Utils.showToastAndRecordLog
import me.dizzykitty3.androidtoolkitty.ui.theme.MyApplicationTheme
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                MyLayout()
            }
        }
    }
}

@Composable
fun MyLayout() {
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    LazyColumn(
        modifier = Modifier.padding(cardPadding)
    ) {
        item {
            // Clear clipboard
            YearProgress()
            Spacer(modifier = Modifier.padding(spacerPadding))
        }

        item {
            // Clear clipboard
            ClipboardGroup(LocalContext.current)
            Spacer(modifier = Modifier.padding(spacerPadding))
        }

        item {
            // Open certain system setting pages
            SystemSettingsGroup(LocalContext.current)
            Spacer(modifier = Modifier.padding(spacerPadding))
        }

        item {
            // Convert unicode to characters and vice versa
            UnicodeGroup(LocalContext.current)
            Spacer(modifier = Modifier.padding(spacerPadding))
        }

        item {
            // Opens Google Maps with the specified latitude and longitude
            GoogleMapsGroup(LocalContext.current)
            Spacer(modifier = Modifier.padding(spacerPadding))
        }

        item {
            // Integer variable recomposition
            TestLayout()
        }
    }
}

@Composable
fun YearProgress() {
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        var expanded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .padding(cardPadding)
                .clickable { expanded = !expanded }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Year Progress",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
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
                    Text(
                        text = "${calculateYearProgress() * 100}%"
                    )
                }
            }
        }
    }
}

fun calculateYearProgress(): Float {
    val currentDate = LocalDate.now()
    val startOfYear = LocalDate.of(currentDate.year, 1, 1)
    val endOfYear = LocalDate.of(currentDate.year, 12, 31)

    val totalDaysInYear = startOfYear.until(endOfYear, ChronoUnit.DAYS)
    val daysPassed = startOfYear.until(currentDate, ChronoUnit.DAYS)

    return daysPassed.toFloat() / totalDaysInYear.toFloat()
}

@Composable
fun ClipboardGroup(context: Context) {
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        var expanded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .padding(cardPadding)
                .clickable { expanded = !expanded }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
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
                }
            }
        }
    }
}

fun onClearClipboardButton(context: Context) {
    ClipboardUtils(context).clearClipboard()
    showToastAndRecordLog(context, "clipboard cleared")
}

@Composable
fun SystemSettingsGroup(context: Context) {
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        var expanded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .padding(cardPadding)
                .clickable { expanded = !expanded }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Android System Settings",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Button(
                        onClick = {
                            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = "Open display settings")
                    }
                    Button(
                        onClick = {
                            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = "Open locale settings")
                    }
                    Button(
                        onClick = {
                            val intent = Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = "Open default apps settings")
                    }
                    Button(
                        onClick = {
                            val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = "Open bluetooth settings")
                    }
                    Button(
                        onClick = {
                            val contentResolver: ContentResolver = context.contentResolver
                            val isAutoTime = Settings.Global.getInt(
                                contentResolver,
                                Settings.Global.AUTO_TIME,
                                0
                            )
                            showToast(
                                context,
                                if (isAutoTime == 1) "set time automatically is on" else "set time automatically is off"
                            )
                        }
                    ) {
                        Text(text = "Check is \"set time automatically\" on")
                    }
                    Button(
                        onClick = {
                            val intent = Intent(Settings.ACTION_DATE_SETTINGS)
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            context.startActivity(intent)
                        }
                    ) {
                        Text(text = "Open date settings")
                    }
                }
            }
        }
    }
}

@Composable
fun UnicodeGroup(context: Context) {
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        var expanded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .padding(cardPadding)
                .clickable { expanded = !expanded }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Unicode",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                var unicode by remember { mutableStateOf("") }
                var character by remember { mutableStateOf("") }

                Column {
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Text(
                        text = "Enter the last four digits of each Unicode like \"00610062\" (drop \\u)"
                    )
                    Row {
                        OutlinedTextField(
                            value = unicode,
                            onValueChange = { unicode = it },
                            label = { Text("Unicode") },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = spacerPadding)
                        )
                        OutlinedTextField(
                            value = character,
                            onValueChange = { character = it },
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
                            onClickConvertButton(context, unicode)
                        }
                    ) {
                        Text(text = "Convert")
                    }
                }
            }
        }
    }
}

fun onClickConvertButton(context: Context, unicode: String) {
    if (unicode.isEmpty()) return
    try {
        val result = convertUnicodeToCharacter(unicode)
        showToast(context, "$result copied")
    } catch (e: Exception) {
        showToast(context, (if (e.message != null) e.message else "Unknown error occurred")!!)
    }
}

@Composable
fun GoogleMapsGroup(context: Context) {
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        var expanded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .padding(cardPadding)
                .clickable { expanded = !expanded }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Google Maps",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                var latitude by remember { mutableStateOf("") }
                var longitude by remember { mutableStateOf("") }

                Column {
                    Spacer(
                        modifier = Modifier.padding(spacerPadding)
                    )
                    Row {
                        OutlinedTextField(
                            value = latitude,
                            onValueChange = { latitude = it },
                            label = { Text("Latitude") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(end = spacerPadding)
                        )
                        OutlinedTextField(
                            value = longitude,
                            onValueChange = { longitude = it },
                            label = { Text("Longitude") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
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

fun onClickOpenGoogleMapsButton(context: Context, latitude: String, longitude: String) {
    openGoogleMaps(context, latitude.ifEmpty { "0" }, longitude.ifEmpty { "0" })
}

@Composable
fun TestLayout() {
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    var clicks by remember { mutableIntStateOf(0) }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        var expanded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .padding(cardPadding)
                .clickable { expanded = !expanded }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "test",
                style = MaterialTheme.typography.titleLarge
            )
            AnimatedVisibility(expanded) {
                Column {
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
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        var expanded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .padding(cardPadding)
                .clickable { expanded = !expanded }
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
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