package me.dizzykitty3.androidtoolkitty.settings

import android.annotation.SuppressLint
import android.location.Location
import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material.icons.outlined.SettingsApplications
import androidx.compose.material.icons.outlined.Terminal
import androidx.compose.material.icons.outlined.WbSunny
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.SCR_PERMISSION_REQUEST
import me.dizzykitty3.androidtoolkitty.SCR_QR_CODE_GENERATOR
import me.dizzykitty3.androidtoolkitty.datastore.SettingsViewModel
import me.dizzykitty3.androidtoolkitty.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.uicomponents.Card
import me.dizzykitty3.androidtoolkitty.uicomponents.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.uicomponents.GroupDivider
import me.dizzykitty3.androidtoolkitty.uicomponents.Screen
import me.dizzykitty3.androidtoolkitty.uicomponents.ScrollableText
import me.dizzykitty3.androidtoolkitty.uicomponents.SpacerPadding
import me.dizzykitty3.androidtoolkitty.uicomponents.WIPTip
import me.dizzykitty3.androidtoolkitty.utils.HapticUtil.hapticFeedback
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.openAppDetailSettings
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil.restartApp
import me.dizzykitty3.androidtoolkitty.utils.LocationUtil
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noBluetoothPermission
import me.dizzykitty3.androidtoolkitty.utils.PermissionUtil.noLocationPermission
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil.showSnackbar
import me.dizzykitty3.androidtoolkitty.utils.StringUtil
import me.dizzykitty3.androidtoolkitty.utils.StringUtil.versionName
import timber.log.Timber

@Composable
fun DebuggingScreen(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    Screen {
        Debugging(settingsViewModel, navController)
    }
}

@SuppressLint("MissingPermission")
@Composable
fun Debugging(settingsViewModel: SettingsViewModel, navController: NavHostController) {
    val view = LocalView.current
    var devMode by remember { mutableStateOf(settingsViewModel.settings.value.devMode) }
    var showLocationDialog by remember { mutableStateOf(false) }

    Card(R.string.debugging, Icons.Outlined.Terminal) {
        Row(Modifier.fillMaxWidth()) {
            Column(Modifier.weight(0.4f)) {
                ScrollableText(stringResource(R.string.manufacturer))
                ScrollableText(stringResource(R.string.device))
                ScrollableText(stringResource(R.string.os_version))
                ScrollableText(stringResource(R.string.locale))
                ScrollableText(stringResource(R.string.app_version))
            }
            Column(Modifier.weight(0.6f)) {
                ScrollableText(Build.MANUFACTURER)
                ScrollableText("${Build.MODEL} (${Build.DEVICE})")
                ScrollableText("Android ${Build.VERSION.RELEASE} (${Build.VERSION.SDK_INT})")
                ScrollableText(StringUtil.sysLocale)
                ScrollableText(view.context.versionName)
            }
        }

        GroupDivider()

        CustomSwitchRow(R.string.dev_mode, devMode) {
            view.hapticFeedback()
            devMode = it
            settingsViewModel.update(settingsViewModel.settings.value.copy(devMode = it))
        }

        OutlinedButton({
            view.hapticFeedback()
            if (view.context.noLocationPermission()) {
                navController.navigate(SCR_PERMISSION_REQUEST)
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
                            Column(Modifier.weight(0.5f)) {
                                Text("8:00 AM - 5:59 PM")
                                Text("6:00 PM - 10:59 PM")
                                Text("11:00 PM - 5:59 AM")
                                Text("6:00 PM - 7:59 AM")
                            }
                            Column(Modifier.weight(0.5f)) {
                                Text("mute")
                                Text("40%/60%")
                                Text("25%")
                                Text("40%/60%")
                            }
                        }
                        SpacerPadding()
                        Text("set volume automatically (check location)")
                        SpacerPadding()
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
                                    navController.navigate(SCR_PERMISSION_REQUEST)
                                    return@Button
                                }
                                LocationUtil.saveLocationToStorage(mLatitude, mLongitude)
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
                                    navController.navigate(SCR_PERMISSION_REQUEST)
                                    return@Button
                                }
                                LocationUtil.saveLocationToStorage(mLatitude, mLongitude)
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

        OutlinedButton({
            view.hapticFeedback()
            navController.navigate(SCR_PERMISSION_REQUEST)
        }) {
            Text(stringResource(R.string.go_to_permission_request_screen))
        }

        OutlinedButton({
            view.hapticFeedback()
            navController.navigate(SCR_QR_CODE_GENERATOR)
        }) {
            Text(stringResource(R.string.qr_code_generator))
        }
    }

    TextButton({
        view.hapticFeedback()
        view.context.openAppDetailSettings()
    }) {
        Icon(
            imageVector = Icons.Outlined.SettingsApplications,
            contentDescription = null
        )
        SpacerPadding()
        Text(stringResource(R.string.open_app_detail_settings))
        Icon(
            imageVector = Icons.Outlined.ArrowOutward,
            contentDescription = stringResource(R.string.open_app_detail_settings),
            tint = MaterialTheme.colorScheme.primary
        )
    }

    TextButton({
        view.hapticFeedback()
        view.context.restartApp()
    }) {
        Icon(
            imageVector = Icons.Outlined.Refresh,
            contentDescription = null
        )
        SpacerPadding()
        Text(stringResource(R.string.restart_app))
    }
}