package me.dizzykitty3.androidtoolkitty.ui.screens.settings

import android.annotation.SuppressLint
import android.content.Context
import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.domain.utils.IntentUtil.openURL
import me.dizzykitty3.androidtoolkitty.domain.utils.OSVersion
import me.dizzykitty3.androidtoolkitty.domain.utils.URLUtil
import me.dizzykitty3.androidtoolkitty.ui.components.Card
import me.dizzykitty3.androidtoolkitty.ui.components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui.components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui.components.IconAndTextPadding
import me.dizzykitty3.androidtoolkitty.ui.components.SpacerPadding

@Composable
fun About() {
    Card(title = R.string.about) {
        val context = LocalContext.current

        Row {
            Icon(
                imageVector = Icons.Outlined.Code,
                contentDescription = stringResource(id = R.string.version)
            )
            IconAndTextPadding()
            Text("${stringResource(R.string.version)} 1.0.${context.versionCode()}")
            if (BuildConfig.DEBUG) Text(".dev")
        }
        GroupDivider()
        ContributorAndThanksTo()
    }
}

// BuildConfig.VERSION_NAME may not have the updated value at compile time. (I guess)
@SuppressLint("NewApi")
private fun Context.versionCode(): String {
    return if (OSVersion.api28())
        this.packageManager.getPackageInfo(this.packageName, 0).longVersionCode.toString()
    else
        BuildConfig.VERSION_CODE.toString() // TODO check on simulator
}

@Composable
private fun ContributorAndThanksTo() {
    GroupTitle(title = R.string.contributors)
    DeveloperProfileLink("dizzykitty3")
    SpacerPadding()
    DeveloperProfileLink("HongjieCN")
    SpacerPadding()
    GroupDivider()
    GroupTitle(title = R.string.inspired_by)
    ThanksTo("tengusw/share_to_clipboard")
    SpacerPadding()
    ThanksTo("hashemi-hossein/memory-guardian")
    SpacerPadding()
    ThanksTo("AkaneFoundation/Omni")
    SpacerPadding()
    ThanksTo("DeweyReed/ClipboardCleaner")
    SpacerPadding()
    ThanksTo("mueller-ma/Coffee")
    SpacerPadding()
    ThanksTo("Kin69/EasyNotes")
}

@Composable
private fun DeveloperProfileLink(name: String) {
    Row {
        val view = LocalView.current

        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = stringResource(id = R.string.developer_profile_link)
        )
        IconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                view.context.openURL("${URLUtil.Platform.GITHUB.prefix}$name")
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
        val view = LocalView.current
        val sourceCodeURL = "https://github.com/$link"

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .clickable {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    view.context.openURL(sourceCodeURL)
                }
        ) {
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