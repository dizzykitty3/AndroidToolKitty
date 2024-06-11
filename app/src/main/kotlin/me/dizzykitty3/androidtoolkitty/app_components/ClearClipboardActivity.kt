package me.dizzykitty3.androidtoolkitty.app_components

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.utils.ClipboardUtil
import timber.log.Timber

class ClearClipboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("onCreate")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        Timber.d("onWindowFocusChanged")
        if (hasFocus) {
            Timber.d("hasFocus")
            val cleared = ClipboardUtil.check()
            if (cleared) {
                Timber.i("clipboard cleared")
                Toast.makeText(this, getString(R.string.clipboard_cleared), Toast.LENGTH_SHORT)
                    .show()
            } else {
                Timber.i("clipboard is empty")
                Toast.makeText(this, getString(R.string.clipboard_is_empty), Toast.LENGTH_SHORT)
                    .show()
            }
            finish()
        }
    }
}