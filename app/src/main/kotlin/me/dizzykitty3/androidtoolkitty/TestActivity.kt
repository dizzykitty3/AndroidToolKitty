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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import me.dizzykitty3.androidtoolkitty.ui.theme.MyApplicationTheme

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyApplicationTheme {
                Column {
                    MyLayout()
                }
            }
        }
    }
}

@Composable
fun MyLayout() {
    val cardPadding = dimensionResource(R.dimen.compose_padding_card)
    val spacerPadding = dimensionResource(R.dimen.compose_padding_spacer)

    Column(
        modifier = Modifier.padding(cardPadding)
    ) {
        // Clear clipboard
        ClipboardGroup(LocalContext.current)
        Spacer(modifier = Modifier.padding(spacerPadding))

        // Open certain system setting pages
        SystemSettingsGroup(LocalContext.current)
        Spacer(modifier = Modifier.padding(spacerPadding))

        // Convert unicode to characters and vise versa
        UnicodeGroup()
        Spacer(modifier = Modifier.padding(spacerPadding))

        // Opens Google Maps with the specified latitude and longitude
        GoogleMapsGroup()
        Spacer(modifier = Modifier.padding(spacerPadding))

        // Integer variable recomposition
        TestLayout()
    }
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
                            ClipboardUtils(context).clearClipboard()
                            Utils.showToastAndRecordLog(context, "clipboard cleared")
                        }
                    ) {
                        Text(text = "Clear clipboard")
                    }
                }
            }
        }
    }
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
                            Utils.showToast(
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
fun UnicodeGroup() {
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
fun GoogleMapsGroup() {
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