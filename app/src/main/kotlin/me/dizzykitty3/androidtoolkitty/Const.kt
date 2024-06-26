package me.dizzykitty3.androidtoolkitty

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager

const val SETTINGS_SCREEN = "SettingsScreen"
const val HOME_SCREEN = "HomeScreen"
const val EDIT_HOME_SCREEN = "EditHomeScreen"
const val PERMISSION_REQUEST_SCREEN = "PermissionRequestScreen"
const val QR_CODE_GENERATOR_SCREEN = "QrCodeGeneratorScreen"
const val DEBUGGING_SCREEN = "DebuggingScreen"
const val LICENSES_SCREEN = "LicensesScreen"

const val CARD_1 = "card_year_progress"
const val CARD_2 = "card_volume"
const val CARD_3 = "card_clipboard"
const val CARD_4 = "card_webpage"
const val CARD_5 = "card_sys_setting"
const val CARD_6 = "card_wheel_of_fortune"
const val CARD_7 = "card_bluetooth_device"
const val CARD_8 = "card_unicode"
const val CARD_9 = "card_app_market"
const val CARD_10 = "card_google_maps"
const val CARD_11 = "card_android_version"
const val CARD_12 = "card_font_weight"

const val SETTING_DISPLAY = "setting_display"
const val SETTING_AUTO_ROTATE = "setting_auto_rotate"
const val SETTING_BLUETOOTH = "setting_bluetooth"
const val SETTING_DEFAULT_APPS = "setting_default_apps"
const val SETTING_BATTERY_OPTIMIZATION = "setting_battery_optimization"
const val SETTING_CAPTIONING = "setting_captioning"
const val SETTING_USAGE_ACCESS = "setting_usage_access"
const val SETTING_OVERLAY = "setting_overlay"
const val SETTING_WRITE_SETTINGS = "setting_write_settings"
const val SETTING_LOCALE = "setting_locale"
const val SETTING_DATE = "setting_date"
const val SETTING_DEVELOPER = "setting_developer"
const val SETTING_ENABLE_BLUETOOTH = "setting_enable_bluetooth"
const val SETTING_WIFI = "setting_wifi"
const val SETTING_POWER_USAGE_SUMMARY = "setting_power_usage_summary"

@SuppressLint("InlinedApi")
const val BT_CONNECT = Manifest.permission.BLUETOOTH_CONNECT
const val BT = Manifest.permission.BLUETOOTH
const val BT_ADMIN = Manifest.permission.BLUETOOTH_ADMIN
const val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
const val COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
const val GRANTED = PackageManager.PERMISSION_GRANTED

const val PACKAGE = "package"
const val GOOGLE_MAPS = "com.google.android.apps.maps"
const val GOOGLE_PLAY = "com.android.vending"

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