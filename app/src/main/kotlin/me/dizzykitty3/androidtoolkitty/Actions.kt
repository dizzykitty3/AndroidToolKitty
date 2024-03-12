package me.dizzykitty3.androidtoolkitty

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.MutableState
import me.dizzykitty3.androidtoolkitty.util.ClipboardUtils
import me.dizzykitty3.androidtoolkitty.util.IntentUtils
import me.dizzykitty3.androidtoolkitty.util.StringUtils
import me.dizzykitty3.androidtoolkitty.util.ToastUtils
import me.dizzykitty3.androidtoolkitty.util.UrlUtils

object Actions {
    private const val HTTPS = "https://"
    private const val GOOGLE_PLAY_STORE = "com.android.vending"
    private lateinit var applicationContext: Context

    @JvmStatic
    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    @Suppress("SpellCheckingInspection")
    @JvmStatic
    fun debugLog(logEvent: String) = Log.d("me.dizzykitty3.androidtoolkitty", logEvent)

    @JvmStatic
    fun onClearClipboardButton() {
        ClipboardUtils(applicationContext).clearClipboard()
        ToastUtils(applicationContext).showToastAndRecordLog(applicationContext.getString(R.string.clipboard_cleared))
    }

    @JvmStatic
    fun onClickVisitUrlButton(userInputUrl: String) {
        if (userInputUrl.isBlank()) return
        IntentUtils(applicationContext).openUrl(
            UrlUtils.processUrl(
                StringUtils.dropSpaces(
                    userInputUrl
                )
            )
        )
        debugLog("onClickVisitButton")
    }

    @JvmStatic
    fun onVisitProfileButton(username: String, platformIndex: Int) {
        if (username.isBlank()) return
        val platform = UrlUtils.Platform.entries.getOrNull(platformIndex) ?: return
        if (platform == UrlUtils.Platform.PLATFORM_NOT_ADDED_YET) {
            ToastUtils(applicationContext).showToastAndRecordLog(
                "${applicationContext.getString(R.string.platform)}: \"$username\" ${
                    applicationContext.getString(R.string.uploaded)
                }"
            )
            return
        }
        val prefix = platform.prefix
        if (prefix.isEmpty()) {
            ToastUtils(applicationContext).showToastAndRecordLog(
                "${applicationContext.getString(R.string.platform)}: \"$username\" ${
                    applicationContext.getString(
                        R.string.uploaded
                    )
                }"
            )
            return
        }
        IntentUtils(applicationContext).openUrl("$HTTPS$prefix${StringUtils.dropSpaces(username)}")
        debugLog("onVisitProfile")
    }

    @JvmStatic
    fun onClickCheckSetTimeAutomaticallyButton() {
        val contentResolver: ContentResolver = applicationContext.contentResolver
        val isAutoTime = Settings.Global.getInt(contentResolver, Settings.Global.AUTO_TIME, 0)
        ToastUtils(applicationContext).showToast(
            if (isAutoTime == 1)
                applicationContext.getString(R.string.set_time_automatically_is_on)
            else
                applicationContext.getString(R.string.set_time_automatically_is_off)
        )
        debugLog("onClickCheckSetTimeAutomaticallyButton")
    }

    @JvmStatic
    fun onClickConvertButton(unicode: String, characterField: MutableState<String>) {
        if (unicode.isBlank()) return
        try {
            val result = StringUtils.convertUnicodeToCharacter(unicode)
            characterField.value = result
            ClipboardUtils(applicationContext).copyTextToClipboard(result)
        } catch (e: Exception) {
            ToastUtils(applicationContext).showToast(e.message?.ifBlank { "Unknown error occurred" })
        }
        debugLog("onClickConvertButton")
    }

    @JvmStatic
    fun onClickOpenGoogleMapsButton(latitude: String, longitude: String) {
        if (latitude.isBlank()) return
        if (longitude.isBlank()) return
        IntentUtils(applicationContext).openGoogleMaps(latitude, longitude)
        debugLog("onClickOpenGoogleMapsButton")
    }

    @JvmStatic
    fun openAppOnPlayStore(packageName: String) {
        if (packageName.isBlank()) return
        val playStoreUri: Uri = if (packageName.contains(".")) {
            Uri.parse("market://details?id=${StringUtils.dropSpaces(packageName)}")
        } else {
            Uri.parse("market://search?q=${packageName.trim()}")
        }
        val playStoreIntent = Intent(Intent.ACTION_VIEW, playStoreUri)
        playStoreIntent.setPackage(GOOGLE_PLAY_STORE)
        playStoreIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        IntentUtils(applicationContext).startActivity(playStoreIntent)
        debugLog("openCertainAppOnPlayStore")
    }
}