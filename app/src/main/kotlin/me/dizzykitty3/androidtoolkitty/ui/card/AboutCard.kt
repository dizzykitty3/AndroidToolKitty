package me.dizzykitty3.androidtoolkitty.ui.card

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.ArrowOutward
import androidx.compose.material.icons.outlined.Code
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import me.dizzykitty3.androidtoolkitty.R
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomCard
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomIconAndTextPadding
import me.dizzykitty3.androidtoolkitty.foundation.ui.component.CustomSpacerPadding
import me.dizzykitty3.androidtoolkitty.foundation.utils.TIntent
import me.dizzykitty3.androidtoolkitty.foundation.utils.TToast
import me.dizzykitty3.androidtoolkitty.foundation.utils.TUrl

@Composable
fun AboutCard() {
    CustomCard(
        title = R.string.about
    ) {
        DeveloperProfileLink("dizzykitty3")
        DeveloperProfileLink("HongjieCN")
        GitHubRepoLink()
        CustomSpacerPadding()
        VersionNumber()
    }
}

@Composable
private fun DeveloperProfileLink(
    name: String
) {
    Row {
        Icon(
            imageVector = Icons.Outlined.AccountCircle,
            contentDescription = null
        )
        CustomIconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                TIntent.openUrl("${TUrl.profilePrefix(TUrl.Platform.GITHUB)}$name")
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
    CustomSpacerPadding()
}

@Composable
private fun GitHubRepoLink() {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        val context = LocalContext.current
        val sourceCodeUrl = "https://github.com/dizzykitty3/android_toolkitty"

        Icon(
            imageVector = Icons.Outlined.Code,
            contentDescription = null
        )
        CustomIconAndTextPadding()
        Row(
            modifier = Modifier.clickable {
                TToast.toast(context.getString(R.string.all_help_welcomed))
                TIntent.openUrl(sourceCodeUrl)
            }
        ) {
            Text(
                text = stringResource(R.string.source_code_on_github),
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
private fun VersionNumber() {
    Row {
        Icon(
            imageVector = Icons.Outlined.Schedule,
            contentDescription = null
        )
        CustomIconAndTextPadding()
        Text(text = "${stringResource(R.string.version)} ${stringResource(R.string.version_number)}")
    }
}