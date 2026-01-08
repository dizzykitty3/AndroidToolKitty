package me.dizzykitty3.androidtoolkitty.home

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalView
import me.dizzykitty3.androidtoolkitty.uicomponents.BaseCard
import me.dizzykitty3.androidtoolkitty.utils.NotificationUtil

@Composable
fun Test() {
    BaseCard(title = "test") {
        val view = LocalView.current
        Button(
            onClick = { NotificationUtil.createNotificationChannel(view.context) }
        ) {
            Text("create notification channel")
        }
        Button(onClick = { NotificationUtil.sendNotification() }) { Text("post notification") }
    }
}