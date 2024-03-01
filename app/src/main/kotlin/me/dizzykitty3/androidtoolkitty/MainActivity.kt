package me.dizzykitty3.androidtoolkitty

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import me.dizzykitty3.androidtoolkitty.Utils.debugLog
import me.dizzykitty3.androidtoolkitty.databinding.ActivityMainBinding
import java.util.Objects

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var clipboardUtils: ClipboardUtils? = null
    private var isAutoClearClipboard = false
    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        initCore()
        sharedPreferencesValues
        initClipboardGroup()
        initTestGroup()
    }

    private fun initCore() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding!!.lifecycleOwner = this
        sharedPreferences = getSharedPreferences("Android ToolKitty", MODE_PRIVATE)
        editor = sharedPreferences!!.edit()
        clipboardUtils = ClipboardUtils(this)
    }

    private val sharedPreferencesValues: Unit
        get() {
            isAutoClearClipboard = sharedPreferences!!.getBoolean(KEY_AUTO_CLEAR_CLIPBOARD, false)
        }

    private fun initClipboardGroup() {
        binding!!.autoClearClipboardSettingSwitch.isChecked = isAutoClearClipboard
        binding!!.autoClearClipboardSettingSwitch.setOnCheckedChangeListener { _, isChecked: Boolean ->
            isAutoClearClipboard = isChecked
            editor!!.putBoolean(KEY_AUTO_CLEAR_CLIPBOARD, isChecked)
            editor!!.apply()
            debugLog(if (isChecked) "auto clear clipboard is now: on" else "auto clear clipboard is now: off")
        }
    }

    private fun initTestGroup() {
        binding!!.testButton.setOnClickListener { _: View? ->
            val intent = Intent(this, TestActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        sharedPreferencesValues
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus && isAutoClearClipboard) {
            clipboardUtils!!.clearClipboard()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (Objects.nonNull(binding)) {
            binding!!.unbind()
        }
    }

    companion object {
        private const val KEY_AUTO_CLEAR_CLIPBOARD = "key_auto_clear_clipboard"
    }
}