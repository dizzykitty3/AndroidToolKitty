package me.dizzykitty3.androidtoolkitty

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
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
        ClipboardGroup(LocalContext.current)
        SystemSettingsGroup(LocalContext.current)
    }
}

@Composable
fun ClipboardGroup(context: Context) {
    Text(
        text = "Clipboard"
    )
    Button(
        onClick = {
            ClipboardUtils(context).clearClipboard()
            Utils.showToastAndRecordLog(context, "clipboard cleared")
        }
    ) {
        Text("Clear clipboard")
    }
}


@Composable
fun SystemSettingsGroup(context: Context) {
    Text(
        text = "System Settings"
    )
    Button(
        onClick = {
            val intent = Intent(Settings.ACTION_DISPLAY_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    ) {
        Text("Open display settings")
    }
    Button(
        onClick = {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    ) {
        Text("Open locale settings")
    }
    Button(
        onClick = {
            val intent = Intent(Settings.ACTION_MANAGE_DEFAULT_APPS_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    ) {
        Text("Open default apps settings")
    }
    Button(
        onClick = {
            val intent = Intent(Settings.ACTION_BLUETOOTH_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    ) {
        Text("Open bluetooth settings")
    }
    Button(
        onClick = {
            val contentResolver: ContentResolver = context.contentResolver
            val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
            if (isAutoTime == 1) {
                // Auto time is enabled
                // Do something here
                Utils.showToast(context, "set time automatically is on")
            } else {
                // Auto time is disabled
                // Do something else here
                Utils.showToast(context, "set time automatically is off")
            }
        }
    ) {
        Text("Check is \"set time automatically\" on")
    }
    Button(
        onClick = {
            val intent = Intent(Settings.ACTION_DATE_SETTINGS)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    ) {
        Text("Open date settings")
    }

}
