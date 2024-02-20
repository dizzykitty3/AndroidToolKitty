package me.dizzykitty3.androidtoolkitty

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import me.dizzykitty3.androidtoolkitty.ui.theme.MyApplicationTheme

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
    Column {
        DialogContent()
        ButtonContent(LocalContext.current)
    }
}

@Composable
fun DialogContent() {
    var text by remember { mutableStateOf(TextFieldValue()) }

    Column(
        modifier = Modifier.padding(14.dp)
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Enter your text") }
        )

        Row(
            modifier = Modifier.padding(14.dp)
        ) {
            Button(onClick = { /* Confirm button logic here */ }) {
                Text("Confirm")
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(onClick = { /* Cancel button logic here */ }) {
                Text("Cancel")
            }
        }
    }
}

@Composable
fun ButtonContent(context: Context) {
    Button(
        onClick = {
            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            context.startActivity(intent)
        }, modifier = Modifier.padding(10.dp)
    ) {
        Text("Open Screen Timeout Settings")
    }
}
