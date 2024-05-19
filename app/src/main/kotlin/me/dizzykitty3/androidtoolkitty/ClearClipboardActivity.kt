package me.dizzykitty3.androidtoolkitty

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.foundation.util.ClipboardUtil
import timber.log.Timber

class ClearClipboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
        val cleared = ClipboardUtil.check()
        if (cleared)
            Toast.makeText(this, getString(R.string.clipboard_cleared), Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, getString(R.string.clipboard_is_empty), Toast.LENGTH_SHORT).show()
        finish()
    }
}