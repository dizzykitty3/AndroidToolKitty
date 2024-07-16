package me.dizzykitty3.androidtoolkitty.ui.screens.settings

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Build
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.android.gms.location.LocationServices
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.data.QR_CODE_GENERATOR_SCREEN
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.domain.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.restartApp
import me.dizzykitty3.androidtoolkitty.domain.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.domain.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.domain.utils.PermissionUtil.noLocationPermission
import me.dizzykitty3.androidtoolkitty.domain.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.domain.utils.StringUtil
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.ui.components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.components.Screen
import me.dizzykitty3.androidtoolkitty.ui.components.SpacerPadding
import me.dizzykitty3.androidtoolkitty.ui.components.WIPTip
import me.dizzykitty3.androidtoolkitty.ui.viewmodel.SettingsViewModel
import timber.log.Timber

@SuppressLint("MissingPermission")
@Composable
fun Debugging(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var devMode by remember { mutableStateOf(settingsSharedPref.devMode) }
    var showLocationDialog by remember { mutableStateOf(false) }
    var fadeAnimation by remember { mutableStateOf(settingsViewModel.settings.value.fadeAnimation) }

    Screen {
        Card(title = R.string.debugging, icon = Icons.Outlined.Terminal) {
            Row(Modifier.fillMaxWidth()) {
                Column(Modifier.weight(0.4f)) {
                    HorizontalScrollableText(stringResource(R.string.manufacturer))
                    HorizontalScrollableText(stringResource(R.string.device))
                    HorizontalScrollableText(stringResource(R.string.os_version))
                    HorizontalScrollableText(stringResource(R.string.locale))
                    HorizontalScrollableText(stringResource(R.string.app_version))
                }
                Column(Modifier.weight(0.6f)) {
                    HorizontalScrollableText(Build.MANUFACTURER)
                    HorizontalScrollableText("${Build.MODEL} (${Build.DEVICE})")
                    HorizontalScrollableText("Android ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})")
                    HorizontalScrollableText(StringUtil.sysLocale)
                    Row {
                        Text("1.0.${view.context.versionCode()}")
                        if (BuildConfig.DEBUG) Text(".dev")
                    }
                }
            }

            GroupDivider()

            CustomSwitchRow(text = R.string.dev_mode, checked = devMode) {
                view.hapticFeedback()
                devMode = it
                settingsSharedPref.devMode = it
            }

            CustomSwitchRow(text = R.string.fade_animation, checked = fadeAnimation) {
                view.hapticFeedback()
                fadeAnimation = it
                settingsViewModel.update(settingsViewModel.settings.value.copy(fadeAnimation = it))
            }

            GroupDivider()

            OutlinedButton(onClick = {
                view.hapticFeedback()
                if (view.context.noLocationPermission()) {
                    navController.navigate(PERMISSION_REQUEST_SCREEN)
                    return@OutlinedButton
                }
                showLocationDialog = true
            }) { Text(stringResource(R.string.auto_set_volume)) }

            if (showLocationDialog) {
                val fusedLocationClient =
                    LocationServices.getFusedLocationProviderClient(view.context)
                Timber.d("fusedLocationClient = $fusedLocationClient")
                var mLocation: Location? = null
                var mLoadingComplete by remember { mutableStateOf(false) }
                var mLatitude by remember { mutableDoubleStateOf(0.0) }
                var mLongitude by remember { mutableDoubleStateOf(0.0) }
                fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        mLocation = location
                        mLatitude = location.latitude
                        mLongitude = location.longitude
                        Timber.d("latitude = ${location.latitude}")
                        Timber.d("longitude = ${location.longitude}")
                        mLoadingComplete = true
                    } else {
                        Timber.w("location == null")
                        view.showSnackbar("get location error")
                        mLoadingComplete = true
                    }
                }
                AlertDialog(
                    onDismissRequest = { showLocationDialog = false },
                    icon = { Icon(Icons.Outlined.WbSunny, null) },
                    title = { Text(stringResource(R.string.auto_set_volume)) },
                    text = {
                        Column {
                            WIPTip()
                            Row {
                                Column(modifier = Modifier.weight(0.5f)) {
                                    Text(text = "8:00 AM - 5:59 PM")
                                    Text(text = "6:00 PM - 10:59 PM")
                                    Text(text = "11:00 PM - 5:59 AM")
                                    Text(text = "6:00 PM - 7:59 AM")
                                }
                                Column(modifier = Modifier.weight(0.5f)) {
                                    Text(text = "mute")
                                    Text(text = "40%/60%")
                                    Text(text = "25%")
                                    Text(text = "40%/60%")
                                }
                            }
                            Text("set volume automatically (check location)")
                            Text("current location (places where you want to turn on phone volume)")
                            if (mLoadingComplete) {
                                if (mLocation != null) {
                                    Text("latitude = $mLatitude")
                                    Text("longitude = $mLongitude")
                                } else {
                                    Text("get location error")
                                }
                            } else {
                                CircularProgressIndicator()
                            }
                        }
                    },
                    confirmButton = {
                        Row {
                            Button(
                                enabled = (mLoadingComplete && (mLocation != null)), onClick = {
                                    view.hapticFeedback()
                                    showLocationDialog = false
                                    if (view.context.noBluetoothPermission()) {
                                        navController.navigate(PERMISSION_REQUEST_SCREEN)
                                        return@Button
                                    }
                                    saveLocationToStorage(mLatitude, mLongitude)
                                    SettingsSharedPref.autoSetMediaVolume = 40
                                }, elevation = ButtonDefaults.buttonElevation(1.dp)
                            ) { Text("40%") }
                        }
                        Row {
                            Button(
                                enabled = (mLoadingComplete && (mLocation != null)), onClick = {
                                    view.hapticFeedback()
                                    showLocationDialog = false
                                    if (view.context.noBluetoothPermission()) {
                                        navController.navigate(PERMISSION_REQUEST_SCREEN)
                                        return@Button
                                    }
                                    saveLocationToStorage(mLatitude, mLongitude)
                                    SettingsSharedPref.autoSetMediaVolume = 60
                                }, elevation = ButtonDefaults.buttonElevation(1.dp)
                            ) { Text("60%") }
                        }
                    },
                    dismissButton = {
                        TextButton({
                            view.hapticFeedback()
                            showLocationDialog = false
                            SettingsSharedPref.autoSetMediaVolume = -1
                        }) { Text(stringResource(R.string.turn_off)) }
                    })
            }

            OutlinedButton(onClick = {
                view.hapticFeedback()
                navController.navigate(PERMISSION_REQUEST_SCREEN)
            }) {
                Text(text = stringResource(id = R.string.go_to_permission_request_screen))
            }

            OutlinedButton(
                onClick = {
                    view.hapticFeedback()
                    navController.navigate(QR_CODE_GENERATOR_SCREEN)
                }
            ) {
                Text(stringResource(R.string.qr_code_generator))
            }
        }

        TextButton(onClick = {
            view.hapticFeedback()
            view.context.openAppDetailSettings()
        }) {
            Icon(
                imageVector = Icons.Outlined.SettingsApplications,
                contentDescription = null
            )
            SpacerPadding()
            Text(text = stringResource(id = R.string.open_app_detail_settings))
        }

        TextButton(onClick = {
            view.hapticFeedback()
            view.context.restartApp()
        }) {
            Icon(
                imageVector = Icons.Outlined.Refresh,
                contentDescription = null
            )
            SpacerPadding()
            Text(text = stringResource(id = R.string.restart_app))
        }
    }
}

@Composable
private fun HorizontalScrollableText(text: String) {
    Box(Modifier.horizontalScroll(rememberScrollState())) {
        Text(text)
    }
}

private fun saveLocationToStorage(latitude: Double, longitude: Double) {
    Timber.d("saveLocationToStorage")
    Timber.d("latitude = $latitude (double)")
    Timber.d("save latitude = ${latitude.toFloat()} (float)")
    Timber.d("longitude = $longitude (double)")
    Timber.d("save longitude = ${longitude.toFloat()} (float)")
    SettingsSharedPref.savedLatitude = latitude.toFloat()
    SettingsSharedPref.savedLongitude = longitude.toFloat()
}

// BuildConfig.VERSION_NAME may not have the updated value at compile time. (I guess)
@SuppressLint("NewApi")
private fun Context.versionCode(): String {
    return if (OSVersion.api28())
        this.packageManager.getPackageInfo(this.packageName, 0).longVersionCode.toString()
    else
        BuildConfig.VERSION_CODE.toString() // TODO check on simulator
}