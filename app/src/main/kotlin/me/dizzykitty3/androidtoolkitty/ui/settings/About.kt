package me.dizzykitty3.androidtoolkitty.ui.settings

import android.view.HapticFeedbackConstants
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.BuildConfig
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.ui_components.Card
import me.dizzykitty3.androidtoolkitty.ui_components.GroupDivider
import me.dizzykitty3.androidtoolkitty.ui_components.GroupTitle
import me.dizzykitty3.androidtoolkitty.ui_components.IconAndTextPadding
import me.dizzykitty3.androidtoolkitty.ui_components.SpacerPadding
import me.dizzykitty3.androidtoolkitty.utils.IntentUtil
import me.dizzykitty3.androidtoolkitty.utils.URLUtil

@Composable
fun About() {
    Card(title = R.string.about) {
        GroupTitle(id = R.string.version)
        Row {
            Icon(
                imageVector = Icons.Outlined.Schedule,
                contentDescription = stringResource(id = R.string.version)
            )
            IconAndTextPadding()
            Text(text = "${stringResource(R.string.version)} ${BuildConfig.VERSION_NAME}")
        }
        GroupDivider()
        ContributorAndThanksTo()
    }
}

@Composable
private fun ContributorAndThanksTo() {
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
        val view = LocalView.current

        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = stringResource(id = R.string.developer_profile_link)
        )
        IconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                IntentUtil.openURL(
                    "${URLUtil.prefixOf(URLUtil.Platform.GITHUB)}$name",
                    view.context
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
        val view = LocalView.current
        val sourceCodeURL = "https://github.com/$link"

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .clickable {
                    view.performHapticFeedback(HapticFeedbackConstants.CONTEXT_CLICK)
                    IntentUtil.openURL(sourceCodeURL, view.context)
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