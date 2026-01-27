package me.dizzykitty3.androidtoolkitty.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import me.dizzykitty3.androidtoolkitty.ToolKitty.Companion.appContext
import timber.log.Timber

@Serializable
data class WheelOfFortuneItems(val items: List<String>)

object SettingsSharedPref {
    private const val PREF_NAME = "Settings"
    private const val USING_CUSTOM_VOLUME_OPTION_LABEL = "using_custom_volume_option_label"
    private const val HAVE_TAPPED_ADD_BUTTON = "have_tapped_add_button"
    private const val LOG_OUTPUTS = "log_outputs"
    private const val CUSTOM_VOLUME = "custom_volume"
    private const val TOP_PADDING_DP = "top_padding_dp"
    private const val BOTTOM_PADDING_DP = "bottom_padding_dp"
    private const val VOLUME_OPTION_LABEL = "volume_option_label"
    private const val TYPING_CONTENTS = "typing_contents"
    private const val LATITUDE = "latitude"
    private const val LONGITUDE = "longitude"
    private const val WHEEL_OF_FORTUNE_ITEMS = "wheel_of_fortune_items"

    private val sharedPrefs: SharedPreferences
        get() = appContext.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    private inline fun <reified T> getPreference(key: String, defaultValue: T): T {
        return when (T::class) {
            Boolean::class -> sharedPrefs.getBoolean(key, defaultValue as Boolean) as T
            Int::class -> sharedPrefs.getInt(key, defaultValue as Int) as T
            Float::class -> sharedPrefs.getFloat(key, defaultValue as Float) as T
            String::class -> sharedPrefs.getString(key, defaultValue as String) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    private inline fun <reified T> setPreference(key: String, value: T) {
        Timber.d("set preference: $key = $value")
        with(sharedPrefs.edit()) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Float -> putFloat(key, value)
                is String -> putString(key, value)
                else -> throw IllegalArgumentException("Unsupported type")
            }
            apply()
        }
    }

    val addedCustomVolume: Boolean
        get() = customVolume > 0

    var usingCustomVolumeOptionLabel: Boolean
        get() = getPreference(USING_CUSTOM_VOLUME_OPTION_LABEL, false)
        set(value) = setPreference(USING_CUSTOM_VOLUME_OPTION_LABEL, value)

    var haveTappedAddButton: Boolean
        get() = getPreference(HAVE_TAPPED_ADD_BUTTON, false)
        set(value) = setPreference(HAVE_TAPPED_ADD_BUTTON, value)

    var logOutputs: Boolean
        get() = getPreference(LOG_OUTPUTS, false)
        set(value) = setPreference(LOG_OUTPUTS, value)

    fun getShownState(card: String): Boolean {
        return sharedPrefs.getBoolean(card, true)
    }

    fun saveShownState(card: String, isShown: Boolean) {
        Timber.d("$card is shown = $isShown")
        with(sharedPrefs.edit()) {
            putBoolean(card, isShown)
            apply()
        }
    }

    var customVolume: Int
        get() = getPreference(CUSTOM_VOLUME, Int.MIN_VALUE)
        set(value) = setPreference(CUSTOM_VOLUME, value)

    var topPaddingDp: Float
        get() = getPreference(TOP_PADDING_DP, 0F)
        set(value) = setPreference(TOP_PADDING_DP, value)

    var bottomPaddingDp: Float
        get() = getPreference(BOTTOM_PADDING_DP, 0F)
        set(value) = setPreference(BOTTOM_PADDING_DP, value)

    var customVolumeOptionLabel: String?
        get() = getPreference(VOLUME_OPTION_LABEL, "")
        set(value) = setPreference(VOLUME_OPTION_LABEL, value)

    var typingContents: String
        get() = getPreference(TYPING_CONTENTS, "")
        set(value) = setPreference(TYPING_CONTENTS, value)

    var latitude: String
        get() = getPreference(LATITUDE, "")
        set(value) = setPreference(LATITUDE, value)

    var longitude: String
        get() = getPreference(LONGITUDE, "")
        set(value) = setPreference(LONGITUDE, value)

    fun getWheelOfFortuneItems(): List<String>? {
        val itemsJson = sharedPrefs.getString(WHEEL_OF_FORTUNE_ITEMS, null) ?: return null
        return try {
            val items: WheelOfFortuneItems = Json.decodeFromString(itemsJson)
            items.items
        } catch (e: Exception) {
            Timber.e(e)
            null
        }
    }

    fun setWheelOfFortuneItems(items: List<String>) {
        val itemsJson = Json.encodeToString(WheelOfFortuneItems(items))
        Timber.d("wheel of fortune items = $itemsJson")
        setPreference(WHEEL_OF_FORTUNE_ITEMS, itemsJson)
    }
}