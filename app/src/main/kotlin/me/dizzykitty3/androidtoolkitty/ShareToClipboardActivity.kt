package me.dizzykitty3.androidtoolkitty

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.foundation.util.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OSVersion
import timber.log.Timber

class ShareToClipboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        val intent = intent
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            Timber.i("onCreate sharedText non null")
            ClipboardUtil.copy(sharedText)
            if (!OSVersion.android13()) Toast.makeText(this, "copied", Toast.LENGTH_SHORT).show()
        }
        finish()
    }
}