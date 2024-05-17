package me.dizzykitty3.androidtoolkitty

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.foundation.util.ClipboardUtil
import timber.log.Timber

class ClearClipboardActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")
        val cleared = ClipboardUtil.check()
        if (cleared)
            Toast.makeText(this, getString(R.string.clipboard_cleared), Toast.LENGTH_SHORT).show()
        else
            Toast.makeText(this, getString(R.string.clipboard_is_empty), Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun log(message: String, level: String? = null) {
        when (level) {
            "wtf" -> Timber.wtf(message)
            "e" -> Timber.e(message)
            "w" -> Timber.w(message)
            "i" -> Timber.i(message)
            "v" -> Timber.v(message)
            else -> Timber.d(message)
        }
    }
}