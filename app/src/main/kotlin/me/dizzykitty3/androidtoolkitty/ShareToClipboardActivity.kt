package me.dizzykitty3.androidtoolkitty

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.foundation.util.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion

class ShareToClipboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            ClipboardUtil.copy(sharedText)
            if (!OsVersion.android13()) Toast.makeText(this, "copied", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}