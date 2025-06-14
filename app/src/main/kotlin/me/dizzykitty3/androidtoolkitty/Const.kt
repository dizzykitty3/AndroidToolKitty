package me.dizzykitty3.androidtoolkitty

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager

// App screens
const val SCR_SETTINGS = "SettingsScreen"
const val SCR_HOME = "HomeScreen"
const val SCR_EDIT_HOME = "EditHomeScreen"
const val SCR_PERMISSION_REQUEST = "PermissionRequestScreen"
const val SCR_LICENSES = "LicensesScreen"
const val SCR_SEARCH = "SearchScreen"
const val SCR_CODES_OF_CHARACTERS = "CodesOfCharactersScreen"
const val SCR_ANDROID_VERSION_HISTORY = "AndroidVersionHistoryScreen"
const val SCR_FONT_WEIGHT_TEST = "FontWeightTestScreen"
const val SCR_SYS_SETTINGS = "SysSettingsScreen"
const val SCR_VOLUME = "VolumeScreen"
const val SCR_COMPOSE_CATALOG = "ComposeCatalogScreen"
const val SCR_WHEEL_OF_FORTUNE = "WheelOfFortuneScreen"
const val SCR_BT_TYPES = "BTTypesScreen"
const val SCR_PIN_OPTIONS = "PinOptionsScreen"

// App cards
const val CARD_1 = "card_year_progress"
const val CARD_2 = "card_volume"
const val CARD_3 = "card_clipboard"
const val CARD_4 = "card_webpage"
const val CARD_5 = "card_sys_setting"
const val CARD_6 = "card_wheel_of_fortune"
const val CARD_7 = "card_bluetooth_device"
const val CARD_8 = "card_unicode"
const val CARD_9 = "card_google_maps"
const val CARD_10 = "card_android_version"
const val CARD_11 = "card_font_weight"
const val CARD_12 = "card_compose_catalog"

// Android intents
const val S_DISPLAY = "setting_display"
const val S_AUTO_ROTATE = "setting_auto_rotate"
const val S_BLUETOOTH = "setting_bluetooth"
const val S_DEFAULT_APPS = "setting_default_apps"
const val S_BATTERY_OPTIMIZATION = "setting_battery_optimization"
const val S_CAPTIONING = "setting_captioning"
const val S_USAGE_ACCESS = "setting_usage_access"
const val S_OVERLAY = "setting_overlay"
const val S_WRITE_SETTINGS = "setting_write_settings"
const val S_LOCALE = "setting_locale"
const val S_DATE = "setting_date"
const val S_DEVELOPER = "setting_developer"
const val S_ENABLE_BLUETOOTH = "setting_enable_bluetooth"
const val S_WIFI = "setting_wifi"
const val S_POWER_USAGE_SUMMARY = "setting_power_usage_summary"
const val S_ACCESSIBILITY = "setting_accessibility"
const val S_NOTIFICATION_LISTENER = "setting_notification_listener"
const val S_NOTIFICATION_POLICY_ACCESS = "setting_notification_policy_access"

// Android permissions
@SuppressLint("InlinedApi")
const val BT_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
const val BT = Manifest.permission.BLUETOOTH
const val BT_ADMIN = Manifest.permission.BLUETOOTH_ADMIN
const val GRANTED = PackageManager.PERMISSION_GRANTED

// Android package names
const val PACKAGE = "package"
const val GOOGLE_MAPS = "com.google.android.apps.maps"
const val GOOGLE_PLAY = "com.android.vending"

// URL
const val HTTPS = "https://"
const val BG = ".bg"
const val CN = ".cn"
const val CO_AR = ".co.ar"
const val CO_JP = ".co.jp"
const val CO_UK = ".co.uk"
const val COM = ".com"
const val COM_CN = ".com.cn"
const val EE = ".ee"
const val IR = ".ir"
const val JP = ".jp"
const val LA = ".la"
const val NET = ".net"
const val ME = ".me"
const val MX = ".mx"
const val NZ = ".nz"
const val ORG = ".org"
const val RU = ".ru"
const val SO = ".so"
const val TO = ".to"
const val TV = ".tv"
const val US = ".us"
const val WIKI = ".wiki"
const val SOURCE_CODE_URL = "https://github.com/dizzykitty3/AndroidToolKitty"
const val WHAT_IS_PACKAGE_NAME_URL = "https://support.google.com/admob/answer/9972781"