package me.dizzykitty3.androidtoolkitty.foundation.ui.component

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource

@Composable
fun CustomAlertDialogButton(
    buttonText: String,
    dialogMessageTitle: String,
    dialogMessage: @Composable () -> Unit,
    positiveButtonText: String?,
    negativeButtonText: String?,
    onClickAction: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        ),
        onClick = { showDialog = true }
    ) {
        Text(
            text = buttonText,
            color = MaterialTheme.colorScheme.onError
        )
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = dialogMessageTitle) },
            text = { dialogMessage() },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog = false
                        onClickAction()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = positiveButtonText ?: stringResource(android.R.string.ok),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showDialog = false }
                ) {
                    Text(text = negativeButtonText ?: stringResource(android.R.string.cancel))
                }
            }
        )
    }
}