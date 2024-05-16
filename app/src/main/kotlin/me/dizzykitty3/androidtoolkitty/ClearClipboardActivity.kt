package me.dizzykitty3.androidtoolkitty

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.foundation.util.OSVersion

class ClearClipboardActivity : Activity() {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        if (OSVersion.android9()) {
            clipboard.clearPrimaryClip()
        } else {
            val clip = ClipData.newPlainText("", "")
            clipboard.setPrimaryClip(clip)
        }
        Toast.makeText(this, getString(R.string.clipboard_cleared), Toast.LENGTH_SHORT).show()
        finish()
    }
}