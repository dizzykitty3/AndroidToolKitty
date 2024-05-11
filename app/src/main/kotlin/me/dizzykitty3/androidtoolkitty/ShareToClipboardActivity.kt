package me.dizzykitty3.androidtoolkitty

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.dizzykitty3.androidtoolkitty.foundation.util.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.foundation.util.OsVersion

class ShareToClipboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_share_to_clipboard)

        // TODO Remove activity switching transition animation

        val intent = intent
        val sharedText = intent.getStringExtra(Intent.EXTRA_TEXT)
        if (sharedText != null) {
            ClipboardUtil.copy(sharedText)
            if (!OsVersion.android13()) {
                Toast.makeText(this, "copied", Toast.LENGTH_SHORT).show()
            }
        }
        finish()
    }
}