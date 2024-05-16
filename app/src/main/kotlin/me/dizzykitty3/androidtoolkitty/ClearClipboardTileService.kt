package me.dizzykitty3.androidtoolkitty

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.IBinder
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.foundation.util.OSVersion

class ClearClipboardTileService : TileService() {
    companion object {
        private const val TAG = "ClearClipboardTileService"
    }

    override fun onBind(intent: Intent?): IBinder? {
        return super.onBind(intent)
    }

    override fun onStartListening() {
        super.onStartListening()
        val cleanTitle = qsTile
        cleanTitle.label = getString(R.string.clear_clipboard)
        cleanTitle.state = Tile.STATE_INACTIVE
        cleanTitle.icon = Icon.createWithResource(this, R.drawable.ic_launcher_foreground)
        cleanTitle.updateTile()
    }

    @SuppressLint("NewApi", "StartActivityAndCollapseDeprecated")
    override fun onClick() {
        super.onClick()
        try {
            val intent = Intent(this@ClearClipboardTileService, ClearClipboardActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS

            if (OSVersion.android14()) {
                val pendingIntent = PendingIntent.getActivity(
                    this@ClearClipboardTileService,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
                startActivityAndCollapse(pendingIntent)
            } else {
                @Suppress("DEPRECATION")
                startActivityAndCollapse(intent)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show()
            Log.e(TAG, e.message ?: "unknown error")
        }
    }
}