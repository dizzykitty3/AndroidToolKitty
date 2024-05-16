package me.dizzykitty3.androidtoolkitty

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import me.dizzykitty3.androidtoolkitty.foundation.util.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OSVersion

class ClearClipboardActivity : Activity() {
    companion object {
        private const val TAG = "ClearClipboardActivity"
    }

    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        log("onCreate")
        if (OSVersion.android9()) {
            ClipboardUtil.clear()
        } else {
            ClipboardUtil.copy("")
        }
        Toast.makeText(this, getString(R.string.clipboard_cleared), Toast.LENGTH_SHORT).show()
        finish()
    }

    private fun log(message: String) = Log.d(TAG, message)
}