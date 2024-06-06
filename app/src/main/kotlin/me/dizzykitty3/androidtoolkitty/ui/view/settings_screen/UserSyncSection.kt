package me.dizzykitty3.androidtoolkitty.ui.view.settings_screen

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.data.sharedpreferences.SettingsSharedPref
import me.dizzykitty3.androidtoolkitty.ui.component.GroupTitle
import me.dizzykitty3.androidtoolkitty.utils.ClipboardUtil
import me.dizzykitty3.androidtoolkitty.utils.HttpUtil
import me.dizzykitty3.androidtoolkitty.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.utils.SnackbarUtil
import me.dizzykitty3.androidtoolkitty.utils.StringUtil
import me.dizzykitty3.androidtoolkitty.utils.ToastUtil
import org.json.JSONObject

@Composable
fun UserSyncSection() {
    GroupTitle(R.string.user_sync)

    var dialogState by remember { mutableStateOf<DialogState?>(null) }
    var token by remember { mutableStateOf(SettingsSharedPref.token) }
    var isLoading by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val view = LocalView.current
    val success = stringResource(id = R.string.success)

    OutlinedButton(
        onClick = {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            dialogState = if (token.isBlank()) {
                DialogState.Login
            } else {
                DialogState.UserProfile
            }
        },
        enabled = !isLoading
    ) {
        Text(text = stringResource(id = R.string.user_profile))
    }

    OutlinedButton(
        onClick = {
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            if (token.isBlank()) {
                dialogState = DialogState.Login
            } else {
                coroutineScope.launch {
                    isLoading = true
                    handleUploadSettings(
                        token = token,
                        settings = SettingsSharedPref.exportSettingsToJson(),
                        onFailure = {
                            val message = view.context.getString(getErrorStringResourceId(it))
                            isLoading = false
                            SnackbarUtil.snackbar(view, message)
                        },
                        onSuccess = {
                            SnackbarUtil.snackbar(view, success)
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
            view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
            if (token.isBlank()) {
                dialogState = DialogState.Login
            } else {
                coroutineScope.launch {
                    isLoading = true
                    handleDownloadSettings(
                        token = token,
                        onSettingsReceived = {
                            SnackbarUtil.snackbar(view, success)
                            SettingsSharedPref.importSettingsFromJson(it)
                        },
                        onFailure = {
                            val message = view.context.getString(getErrorStringResourceId(it))
                            isLoading = false
                            SnackbarUtil.snackbar(view, message)
                        },
                        onSuccess = {
                            isLoading = false
                            SnackbarUtil.snackbar(view, R.string.success)
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

    when (dialogState) {
        DialogState.Login -> {
            UserLoginDialog(
                onDismiss = { dialogState = null },
                onRegisterClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    dialogState = DialogState.Register
                },
                onLoginClick = { usernameForLogin, password ->
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    isLoading = true
                    coroutineScope.launch {
                        handleLogin(
                            usernameForLogin,
                            password,
                            onDismiss = { dialogState = null; isLoading = false },
                            onTokenReceived = { newToken ->
                                token = newToken
                                SettingsSharedPref.token = newToken
                                dialogState = null
                                isLoading = false
                            },
                            onSuccess = {
                                SnackbarUtil.snackbar(view, success)
                            },
                            onFailure = {
                                val message = view.context.getString(getErrorStringResourceId(it))
                                isLoading = false
                                ToastUtil.toast(message)
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
                onLoginClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    dialogState = DialogState.Login
                },
                onRegisterClick = { username, email, password ->
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    isLoading = true
                    coroutineScope.launch {
                        handleRegister(
                            username,
                            email,
                            password,
                            onDismiss = { dialogState = null; isLoading = false },
                            onTokenReceived = { newToken ->
                                token = newToken
                                SettingsSharedPref.token = newToken
                                dialogState = null
                                isLoading = false
                            },
                            onSuccess = {
                                SnackbarUtil.snackbar(view, success)
                            },
                            onFailure = {
                                val message = view.context.getString(getErrorStringResourceId(it))
                                isLoading = false
                                ToastUtil.toast(message)
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
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    SettingsSharedPref.removePreference("token")
                    token = ""
                    dialogState = null
                    isLoading = false
                    SnackbarUtil.snackbar(view, success)
                },
                onDismiss = { dialogState = null }
            )
        }

        null -> {}
    }
}

private fun getErrorStringResourceId(code: Int): Int {
    return when (code) {
        401 -> R.string.error_login_expired
        1001 -> R.string.error_account_locked
        1002 -> R.string.error_invalid_credentials
        1003 -> R.string.error_username_taken
        2001 -> R.string.error_settings_not_found
        else -> R.string.error_unknown
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
    var usernameError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val usernameRegex = Regex("^[a-zA-Z0-9_]*$")
    val passwordRegex = Regex("^[^\\s]{6,}$")
    val view = LocalView.current

    CommonDialog(onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
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
            OutlinedTextField(
                value = username,
                onValueChange = {
                    val trimmedInput = it.filter { char -> char.isLetterOrDigit() || char == '_' }
                    username = trimmedInput
                },
                label = { Text(text = stringResource(id = R.string.username)) },
                isError = usernameError != null
            )
            if (usernameError != null) {
                Text(
                    text = usernameError ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
            OutlinedTextField(
                value = password,
                onValueChange = { password = StringUtil.dropSpaces(it) },
                label = { Text(text = stringResource(id = R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError != null
            )
            if (passwordError != null) {
                Text(
                    text = passwordError ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = !isLoading,
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    usernameError =
                        if (!username.matches(usernameRegex)) view.context.getString(R.string.error_invalid_username) else null
                    passwordError =
                        if (!password.matches(passwordRegex)) view.context.getString(R.string.error_invalid_password) else null
                    if (usernameError == null && passwordError == null) {
                        onLoginClick(username, password)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.login))
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
    var usernameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    val usernameRegex = Regex("^[a-zA-Z0-9_]*$")
    val emailRegex = Regex("^[A-Za-z0-9+_.-]+@(.+)\$")
    val passwordRegex = Regex("^[^\\s]{6,}$")
    val view = LocalView.current

    CommonDialog(onDismiss) {
        Column(modifier = Modifier.padding(16.dp)) {
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
            OutlinedTextField(
                value = username,
                onValueChange = {
                    val trimmedInput = it.filter { char -> char.isLetterOrDigit() || char == '_' }
                    username = trimmedInput
                },
                label = { Text(text = stringResource(id = R.string.username)) },
                isError = usernameError != null
            )
            if (usernameError != null) {
                Text(
                    text = usernameError ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
            OutlinedTextField(
                value = email,
                onValueChange = { email = StringUtil.dropSpaces(it) },
                label = { Text(text = stringResource(id = R.string.email)) },
                isError = emailError != null
            )
            if (emailError != null) {
                Text(
                    text = emailError ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
            OutlinedTextField(
                value = password,
                onValueChange = { password = StringUtil.dropSpaces(it) },
                label = { Text(text = stringResource(id = R.string.password)) },
                visualTransformation = PasswordVisualTransformation(),
                isError = passwordError != null
            )
            if (passwordError != null) {
                Text(
                    text = passwordError ?: "",
                    color = MaterialTheme.colorScheme.error
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                enabled = !isLoading,
                onClick = {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    usernameError =
                        if (!username.matches(usernameRegex)) view.context.getString(R.string.error_invalid_username) else null
                    emailError =
                        if (!email.matches(emailRegex)) view.context.getString(R.string.error_invalid_email) else null
                    passwordError =
                        if (!password.matches(passwordRegex)) view.context.getString(R.string.error_invalid_password) else null
                    if (usernameError == null && emailError == null && passwordError == null) {
                        onRegisterClick(username, email, password)
                    }
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(text = stringResource(id = R.string.register))
            }
        }
    }
}

@Composable
private fun UserProfileDialog(
    token: String,
    onLogout: () -> Unit,
    onDismiss: () -> Unit
) {
    val view = LocalView.current

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text(text = stringResource(id = R.string.user_profile)) },
        text = {
            Text(
                text = "Token: $token (tap to copy)",
                modifier = Modifier.clickable {
                    view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                    ClipboardUtil.copy(token)
                    if (!OSVersion.android13()) {
                        ToastUtil.toast(R.string.copied)
                    }
                }
            )
        },
        confirmButton = {
            Button(onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                onDismiss()
            }) {
                Text(stringResource(id = android.R.string.ok))
            }
        },
        dismissButton = {
            TextButton(onClick = {
                view.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_TAP)
                onLogout()
            }) {
                Text("Logout")
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

private suspend fun handleUploadSettings(
    token: String,
    settings: String,
    onFailure: (Int) -> Unit,
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

private suspend fun handleDownloadSettings(
    token: String,
    onSettingsReceived: (String) -> Unit,
    onFailure: (Int) -> Unit,
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

private suspend fun handleLogin(
    username: String,
    password: String,
    onDismiss: () -> Unit,
    onTokenReceived: (String) -> Unit,
    onSuccess: () -> Unit,
    onFailure: (Int) -> Unit
) {
    handleRequest(
        url = "https://api.yanqishui.work/toolkitten/account/login",
        body = mapOf("username" to username, "password" to password),
        onDismiss = onDismiss,
        onDataReceived = onTokenReceived,
        onSuccess = onSuccess,
        onFailure = onFailure,
        requestType = HttpRequestType.POST
    )
}

private suspend fun handleRegister(
    username: String,
    email: String,
    password: String,
    onDismiss: () -> Unit,
    onTokenReceived: (String) -> Unit,
    onSuccess: () -> Unit,
    onFailure: (Int) -> Unit

) {
    handleRequest(
        url = "https://api.yanqishui.work/toolkitten/account/register",
        body = mapOf("username" to username, "email" to email, "password" to password),
        onDismiss = onDismiss,
        onDataReceived = onTokenReceived,
        onSuccess = onSuccess,
        onFailure = onFailure,
        requestType = HttpRequestType.POST
    )
}

enum class HttpRequestType {
    GET, POST, PUT, DELETE
}

private suspend fun handleRequest(
    requestType: HttpRequestType,
    url: String,
    body: Map<String, String> = emptyMap(),
    headers: Map<String, String> = emptyMap(),
    onDismiss: () -> Unit = {},
    onDataReceived: (String) -> Unit = {},
    onFailure: (Int) -> Unit,
    onSuccess: () -> Unit
) {
    try {
        val response: HttpResponse = when (requestType) {
            HttpRequestType.GET -> HttpUtil.get(url, body, headers)
            HttpRequestType.POST -> HttpUtil.post(url, body, headers)
            HttpRequestType.PUT -> HttpUtil.put(url, body, headers)
            HttpRequestType.DELETE -> HttpUtil.delete(url, body, headers)
        }

        when (response.status) {
            HttpStatusCode.OK -> {
                val responseBody = response.bodyAsText()
                onDataReceived(responseBody)
                onSuccess()
                onDismiss()
            }

            HttpStatusCode.Unauthorized -> {
                onDismiss()
                onFailure(response.status.value)
            }

            else -> {
                val errorBody = response.bodyAsText()
                val jsonObj = JSONObject(errorBody)
                val errorCode = jsonObj.getInt("code")
                onFailure(errorCode)
            }
        }
    } catch (_: Exception) {
        onFailure(9999)
    }
}