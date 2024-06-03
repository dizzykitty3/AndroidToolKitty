package me.dizzykitty3.androidtoolkitty.ui.view.settings_screen

import android.content.Context
import android.os.Build
import android.view.View
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.DataObject
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui.component.Bold
import me.dizzykitty3.androidtoolkitty.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.ui.component.CustomScreen
import me.dizzykitty3.androidtoolkitty.ui.component.CustomSwitchRow
import me.dizzykitty3.androidtoolkitty.ui.component.CustomTip
import me.dizzykitty3.androidtoolkitty.ui.component.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.component.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui.component.IconAndTextPadding
import me.dizzykitty3.androidtoolkitty.ui.component.SpacerPadding
import me.dizzykitty3.androidtoolkitty.ui.component.WarningAlertDialogButton
import me.dizzykitty3.androidtoolkitty.utils.CARD_3
import me.dizzykitty3.androidtoolkitty.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.utils.EDIT_HOME_SCREEN
import me.dizzykitty3.androidtoolkitty.utils.HttpUtil
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.PERMISSION_REQUEST_SCREEN
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.utils.ToastUtil
import me.dizzykitty3.androidtoolkitty.utils.URLUtil
import org.json.JSONObject
import java.util.Locale

@Composable
fun SettingsScreen(navController: NavHostController) {
    CustomScreen {
        val view = LocalView.current
        val debuggingOptions = SettingsSharedPref.debuggingOptions
        var tapCount by remember { mutableIntStateOf(0) }

        CustomCard(titleRes = R.string.settings) {
            AppearanceOptions()
            GroupDivider()
            GeneralOptions()
            GroupDivider()
            CustomizeOptions(navController = navController)
            GroupDivider()
            UserSyncSection()
            @Suppress("KotlinConstantConditions")
            AnimatedVisibility(
                visible = (debuggingOptions || (!debuggingOptions && tapCount >= 5)),
                enter = fadeIn(animationSpec = tween(durationMillis = 2000))
            ) {
                Column {
                    GroupDivider()
                    DebuggingOptions(navController)
                }
            }
        }

        CustomCard(titleRes = R.string.about) {
            GroupTitle(id = R.string.version)
            Row(
                modifier = Modifier.clickable {
                    if (!debuggingOptions) {
                        tapCount++
                        when (tapCount) {
                            3 -> SnackbarUtil.snackbar(view, R.string.two_more_times)
                            4 -> SnackbarUtil.snackbar(view, R.string.one_more_time)
                            5 -> {
                                SnackbarUtil.snackbar(view, R.string.now_a_developer)
                                SettingsSharedPref.debuggingOptions = true
                            }
                        }
                    } else {
                        SnackbarUtil.snackbar(view, R.string.already_developer)
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Outlined.Schedule,
                    contentDescription = stringResource(id = R.string.version)
                )
                IconAndTextPadding()
                Text(text = "${stringResource(R.string.version)} ${BuildConfig.VERSION_NAME}")
            }
            GroupDivider()
            Contributor()
            GroupDivider()
            SourceAndLicenses()
        }
    }
}

@Composable
private fun AppearanceOptions() {
    val view = LocalView.current
    val context = LocalContext.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var oneHandedMode by remember { mutableStateOf(settingsSharedPref.oneHandedMode) }
    var dynamicColor by remember { mutableStateOf(settingsSharedPref.dynamicColor) }
    var showDivider by remember { mutableStateOf(settingsSharedPref.showDivider) }
    var showEditVolumeOption by remember { mutableStateOf(settingsSharedPref.showEditVolumeOption) }
    val customVolume = settingsSharedPref.customVolume
    val primary = MaterialTheme.colorScheme.primary.toArgb()

    GroupTitle(id = R.string.appearance)

    if (OSVersion.android12()) {
        CustomSwitchRow(textRes = R.string.material_you_dynamic_color, checked = dynamicColor) {
            dynamicColor = it
            onClickDynamicColorButton(view, it, primary, context)
        }
    }

    CustomSwitchRow(textRes = R.string.one_handed_mode, checked = oneHandedMode) {
        oneHandedMode = it
        settingsSharedPref.oneHandedMode = it
    }

    CustomSwitchRow(textRes = R.string.show_divider, checked = showDivider) {
        showDivider = it
        settingsSharedPref.showDivider = it
    }

    if (customVolume > 0) CustomSwitchRow(
        textRes = R.string.show_edit_volume_option,
        checked = showEditVolumeOption
    ) {
        showEditVolumeOption = it
        settingsSharedPref.showEditVolumeOption = it
    }
}

@Composable
private fun GeneralOptions() {
    val view = LocalView.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var autoClearClipboard by remember { mutableStateOf(settingsSharedPref.autoClearClipboard) }
    var showClipboardCard by remember { mutableStateOf(settingsSharedPref.getCardShowedState(CARD_3)) }
    var volumeSlideSteps by remember { mutableStateOf(settingsSharedPref.sliderIncrement5Percent) }
    var collapseKeyboard by remember { mutableStateOf(settingsSharedPref.collapseKeyboard) }
    var showSnackbarToConfirm by remember { mutableStateOf(settingsSharedPref.showSnackbar) }
    val primary = MaterialTheme.colorScheme.primary.toArgb()

    GroupTitle(R.string.general)

    CustomSwitchRow(
        textRes = R.string.clear_clipboard_on_launch,
        checked = autoClearClipboard
    ) {
        autoClearClipboard = it
        // Automatically hide Clipboard Card when turning on Clear on Launch feature.
        if (autoClearClipboard && showClipboardCard) {
            showClipboardCard = false
            settingsSharedPref.saveCardShowedState(CARD_3, false)
            SnackbarUtil.snackbar(
                view,
                messageRes = R.string.clipboard_card_hidden,
                buttonTextRes = R.string.undo,
                buttonColor = primary,
                buttonClickListener = {
                    settingsSharedPref.saveCardShowedState(CARD_3, true)
                }
            )
        }
        settingsSharedPref.autoClearClipboard = autoClearClipboard
    }

    CustomSwitchRow(textRes = R.string.set_slider_increment_5, checked = volumeSlideSteps) {
        volumeSlideSteps = it
        settingsSharedPref.sliderIncrement5Percent = it
    }

    CustomSwitchRow(
        textRes = R.string.collapse_keyboard_when_back_to_app,
        checked = collapseKeyboard
    ) {
        collapseKeyboard = it
        settingsSharedPref.collapseKeyboard = it
    }

    CustomSwitchRow(textRes = R.string.show_snackbar_to_confirm, checked = showSnackbarToConfirm) {
        showSnackbarToConfirm = it
        settingsSharedPref.showSnackbar = it
    }
}

@Composable
private fun CustomizeOptions(navController: NavHostController) {
    GroupTitle(R.string.customize)

    OutlinedButton(
        onClick = { navController.navigate(EDIT_HOME_SCREEN) }
    ) {
        Icon(
            imageVector = Icons.Outlined.Edit,
            contentDescription = stringResource(id = R.string.customize_my_home_page),
            modifier = Modifier.align(Alignment.CenterVertically)
        )
        SpacerPadding()
        Text(text = stringResource(R.string.customize_my_home_page))
    }
}

@Composable
private fun DebuggingOptions(navController: NavHostController) {
    val context = LocalContext.current
    val settingsSharedPref = remember { SettingsSharedPref }
    var showSpDialog by remember { mutableStateOf(false) }
    var uiTesting by remember { mutableStateOf(settingsSharedPref.uiTesting) }

    GroupTitle(R.string.debugging)

    Text(text = "Android ${Build.VERSION.RELEASE}, API ${Build.VERSION.SDK_INT}")
    Text(text = "Language =  ${Locale.getDefault()}")

    CustomSwitchRow(textRes = R.string.ui_testing, checked = uiTesting) {
        uiTesting = it
        settingsSharedPref.uiTesting = it
    }

    OutlinedButton(onClick = { navController.navigate(PERMISSION_REQUEST_SCREEN) }) {
        Text(text = stringResource(id = R.string.go_to_permission_request_screen))
    }

    OutlinedButton(onClick = { showSpDialog = true }) {
        Text(text = stringResource(id = R.string.check_sp_values))
    }

    val sharedPref = remember { SettingsSharedPref }

    if (showSpDialog) {
        AlertDialog(
            icon = { Icon(imageVector = Icons.Outlined.DataObject, contentDescription = null) },
            onDismissRequest = { showSpDialog = false },
            title = { Text(text = stringResource(id = R.string.sp_values)) },
            text = {
                Column {
                    CustomTip(id = R.string.under_development)
                    Text(text = "AUTO_CLEAR_CLIPBOARD = ${sharedPref.autoClearClipboard}")
                    Text(text = "SLIDER_INCREMENT_5_PERCENT = ${sharedPref.sliderIncrement5Percent}")
                    Text(text = "DYNAMIC_COLOR = ${sharedPref.dynamicColor}")
                    Text(text = "ONE_HANDED_MODE = ${sharedPref.oneHandedMode}")
                    Text(text = "HAVE_OPENED_SETTINGS_SCREEN = ${sharedPref.haveOpenedSettingsScreen}")
                    Text(text = "USING_CUSTOM_VOLUME_OPTION_LABEL = ${sharedPref.usingCustomVolumeOptionLabel}")
                    Text(text = "DEBUGGING_OPTIONS = ${sharedPref.debuggingOptions}")
                    Text(text = "WEBPAGE_CARD_SHOW_MORE = ${sharedPref.webpageCardShowMore}")
                    Text(text = "COLLAPSE_KEYBOARD = ${sharedPref.collapseKeyboard}")
                }
            },
            confirmButton = {
                Button(onClick = { showSpDialog = false }) {
                    Text(text = stringResource(id = android.R.string.ok))
                }
            }
        )
    }

    OutlinedButton(onClick = { IntentUtil.restartApp(context) }) {
        Text(text = stringResource(id = R.string.restart_app))
    }

    WarningAlertDialogButton(
        buttonText = stringResource(R.string.erase_all_app_data),
        dialogMessageTitle = stringResource(R.string.warning),
        dialogMessage = {
            Text(
                text = buildAnnotatedString {
                    append(stringResource(R.string.warning_erase_all_data_1))
                    append(" ")
                    Bold(R.string.warning_erase_all_data_2)
                    append("\n\n")
                    append(stringResource(R.string.warning_erase_all_data_3))
                }
            )
        },
        positiveButtonText = stringResource(R.string.erase_all_data),
        negativeButtonText = null,
        onClickAction = {
            settingsSharedPref.clearSettings()
            IntentUtil.finishApp(context)
        }
    )
}

private fun onClickDynamicColorButton(
    view: View,
    isDynamicColor: Boolean,
    color: Int,
    context: Context
) {
    val showSnackbarToConfirm = SettingsSharedPref.showSnackbar

    SettingsSharedPref.dynamicColor = isDynamicColor
    if (showSnackbarToConfirm) {
        SnackbarUtil.snackbar(
            view,
            messageRes = R.string.requires_restart_do_it_now,
            buttonTextRes = R.string.restart,
            buttonColor = color,
            buttonClickListener = { IntentUtil.restartApp(context) }
        )
    } else {
        IntentUtil.restartApp(context)
    }
}

@Composable
private fun Contributor() {
    GroupTitle(id = R.string.contributors)
    DeveloperProfileLink("dizzykitty3")
    SpacerPadding()
    DeveloperProfileLink("HongjieCN")
    SpacerPadding()
    GroupTitle(id = R.string.inspired_by)
    ThanksTo("tengusw/share_to_clipboard")
    SpacerPadding()
    ThanksTo("hashemi-hossein/memory-guardian")
}

@Composable
private fun DeveloperProfileLink(name: String) {
    Row {
        val context = LocalContext.current

        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = stringResource(id = R.string.developer_profile_link)
        )
        IconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                IntentUtil.openURL(
                    "${URLUtil.prefixOf(URLUtil.Platform.GITHUB)}$name",
                    context
                )
            }
        ) {
            Text(
                text = name,
                color = MaterialTheme.colorScheme.primary
            )

            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun ThanksTo(link: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        val sourceCodeURL = "https://github.com/$link"

        Row(modifier = Modifier.clickable {
            IntentUtil.openURL(sourceCodeURL, context)
        }) {
            Text(
                text = link,
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun SourceAndLicenses() {
    GroupTitle(id = R.string.source_and_licenses)
    GitHubRepoLink()
}

@Composable
private fun GitHubRepoLink() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        val sourceCodeURL = "https://github.com/dizzykitty3/AndroidToolKitty"

        Icon(
            imageVector = Icons.Outlined.Code,
            contentDescription = stringResource(id = R.string.developer_profile_link)
        )
        IconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                ToastUtil.toast(R.string.all_help_welcomed)
                IntentUtil.openURL(sourceCodeURL, context)
            }
        ) {
            Text(
                text = stringResource(R.string.source_code_on_github),
                color = MaterialTheme.colorScheme.primary
            )
            Icon(
                imageVector = Icons.Outlined.ArrowOutward,
                contentDescription = stringResource(id = R.string.developer_profile_link),
                modifier = Modifier.align(Alignment.CenterVertically),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun UserSyncSection() {
    GroupTitle(R.string.user_sync)

    var dialogState by remember { mutableStateOf<DialogState?>(null) }
    var token by remember { mutableStateOf(SettingsSharedPref.getToken()) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        OutlinedButton(
            onClick = {
                if (token.isBlank()) {
                    dialogState = DialogState.Login
                } else {
                    dialogState = DialogState.UserProfile
                }
            },
            enabled = !isLoading
        ) {
            Text(text = stringResource(id = R.string.user_profile)) // 用户头像按钮
        }

        OutlinedButton(
            onClick = {
                if (token.isBlank()) {
                    dialogState = DialogState.Login
                } else {
                    coroutineScope.launch {
                        isLoading = true
                        handleUploadSettings(
                            token = token,
                            settings = SettingsSharedPref.exportSettingsToJson(),
                            onFailure = {
                                isLoading = false
                            },
                            onSuccess = {
                                isLoading = false
                            }
                        )
                    }
                }
            },
            enabled = !isLoading
        ) {
            Text(text = stringResource(id = R.string.upload_settings))
        }

        OutlinedButton(
            onClick = {
                if (token.isBlank()) {
                    dialogState = DialogState.Login
                } else {
                    coroutineScope.launch {
                        isLoading = true
                        handleDownloadSettings(
                            token = token,
                            onSettingsReceived = {
                                SettingsSharedPref.importSettingsFromJson(it)
                            },
                            onFailure = {
                                isLoading = false
                            },
                            onSuccess = {
                                isLoading = false
                            }
                        )
                    }
                }
            },
            enabled = !isLoading
        ) {
            Text(text = stringResource(id = R.string.download_settings))
        }

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(top = 16.dp))
        }
    }

    when (dialogState) {
        DialogState.Login -> {
            UserLoginDialog(
                onDismiss = { dialogState = null },
                onRegisterClick = { dialogState = DialogState.Register },
                onLoginClick = { usernameForLogin, password ->
                    isLoading = true
                    coroutineScope.launch {
                        handleLogin(
                            usernameForLogin,
                            password,
                            onDismiss = { dialogState = null; isLoading = false },
                            onTokenReceived = { newToken ->
                                token = newToken
                                SettingsSharedPref.setToken(newToken)
                                dialogState = null
                                isLoading = false
                            },
                            onFailure = {
                                isLoading = false
                            }
                        )
                    }
                },
                isLoading = isLoading
            )
        }

        DialogState.Register -> {
            UserRegisterDialog(
                onDismiss = { dialogState = null },
                onLoginClick = { dialogState = DialogState.Login },
                onRegisterClick = { username, email, password ->
                    isLoading = true
                    coroutineScope.launch {
                        handleRegister(
                            username,
                            email,
                            password,
                            onDismiss = { dialogState = null; isLoading = false },
                            onTokenReceived = { newToken ->
                                token = newToken
                                SettingsSharedPref.setToken(newToken)
                                dialogState = null
                                isLoading = false
                            },
                            onFailure = {
                                isLoading = false
                            }
                        )
                    }
                },
                isLoading = isLoading
            )
        }

        DialogState.UserProfile -> {
            UserProfileDialog(
                token = token,
                onLogout = {
                    SettingsSharedPref.removePreference("token")
                    token = ""
                    dialogState = null
                    isLoading = false
                    ToastUtil.toast("Successfully logout!")
                },
                onDismiss = { dialogState = null }
            )
        }

        null -> {}
    }
}

enum class DialogState {
    Login, Register, UserProfile
}

@Composable
private fun UserLoginDialog(
    onDismiss: () -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: (String, String) -> Unit,
    isLoading: Boolean
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    CommonDialog(onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.register),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(onClick = onRegisterClick)
                    )
                }
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = stringResource(id = R.string.username)) })
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onLoginClick(username, password) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(id = R.string.login))
                }
            }
        }
    }
}

@Composable
private fun UserRegisterDialog(
    onDismiss: () -> Unit,
    onLoginClick: () -> Unit,
    onRegisterClick: (String, String, String) -> Unit,
    isLoading: Boolean
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    CommonDialog(onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(id = R.string.login),
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.clickable(onClick = onLoginClick)
                    )
                }
                TextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text(text = stringResource(id = R.string.username)) })
                TextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text(text = stringResource(id = R.string.email)) })
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text(text = stringResource(id = R.string.password)) },
                    visualTransformation = PasswordVisualTransformation()
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { onRegisterClick(username, email, password) },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(text = stringResource(id = R.string.register))
                }
            }
        }
    }
}

@Composable
fun UserProfileDialog(
    token: String,
    onLogout: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = "User Profile") },
        text = {
            Column {
                Text(
                    text = "Token: $token",
                    modifier = Modifier.clickable {
                        ClipboardUtil.copy(token)
                        ToastUtil.toast("Token copied to clipboard")
                    }
                )
            }
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = { onLogout() }) {
                    Text("Logout")
                }
                Button(onClick = { onDismiss() }) {
                    Text("Close")
                }
            }
        }
    )
}

@Composable
private fun CommonDialog(onDismiss: () -> Unit, content: @Composable () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            contentColor = contentColorFor(backgroundColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.padding(16.dp)
        ) {
            content()
        }
    }
}

suspend fun handleUploadSettings(
    token: String,
    settings: String,
    onFailure: () -> Unit,
    onSuccess: () -> Unit
) {
    handleRequest(
        url = "https://api.yanqishui.work/toolkitten/data/user-settings",
        headers = mapOf("Authorization" to token),
        body = mapOf("settings" to settings),
        onFailure = onFailure,
        onSuccess = onSuccess,
        requestType = HttpRequestType.PUT
    )
}

suspend fun handleDownloadSettings(
    token: String,
    onSettingsReceived: (String) -> Unit,
    onFailure: () -> Unit,
    onSuccess: () -> Unit
) {
    handleRequest(
        url = "https://api.yanqishui.work/toolkitten/data/user-settings",
        headers = mapOf("Authorization" to token),
        onFailure = onFailure,
        onSuccess = onSuccess,
        onDataReceived = onSettingsReceived,
        requestType = HttpRequestType.GET
    )
}

suspend fun handleLogin(
    username: String,
    password: String,
    onDismiss: () -> Unit,
    onTokenReceived: (String) -> Unit,
    onFailure: () -> Unit
) {
    handleRequest(
        url = "https://api.yanqishui.work/toolkitten/account/login",
        body = mapOf("username" to username, "password" to password),
        onDismiss = onDismiss,
        onDataReceived = onTokenReceived,
        onFailure = onFailure,
        requestType = HttpRequestType.POST
    )
}

suspend fun handleRegister(
    username: String,
    email: String,
    password: String,
    onDismiss: () -> Unit,
    onTokenReceived: (String) -> Unit,
    onFailure: () -> Unit
) {
    handleRequest(
        url = "https://api.yanqishui.work/toolkitten/account/register",
        body = mapOf("username" to username, "email" to email, "password" to password),
        onDismiss = onDismiss,
        onDataReceived = onTokenReceived,
        onFailure = onFailure,
        requestType = HttpRequestType.POST
    )
}

enum class HttpRequestType {
    GET, POST, PUT, DELETE
}

suspend fun handleRequest(
    requestType: HttpRequestType,
    url: String,
    body: Map<String, String> = emptyMap(),
    headers: Map<String, String> = emptyMap(),
    onDismiss: () -> Unit = {},
    onDataReceived: (String) -> Unit = {},
    onFailure: () -> Unit,
    onSuccess: () -> Unit = {}
) {
    val response: HttpResponse = when (requestType) {
        HttpRequestType.GET -> HttpUtil.get(url, body, headers)
        HttpRequestType.POST -> HttpUtil.post(url, body, headers)
        HttpRequestType.PUT -> HttpUtil.put(url, body, headers)
        HttpRequestType.DELETE -> HttpUtil.delete(url, body, headers)
    }

    if (response.status == HttpStatusCode.OK) {
        val responseBody = response.bodyAsText()
        onDataReceived(responseBody)
        ToastUtil.toast("Operation successful")
        onDismiss()
        onSuccess()
    } else {
        val errorBody = response.bodyAsText()
        val jsonObj = JSONObject(errorBody)
        val errorMessage = jsonObj.getString("message")
        ToastUtil.toast(errorMessage)
        onFailure()
    }
}
