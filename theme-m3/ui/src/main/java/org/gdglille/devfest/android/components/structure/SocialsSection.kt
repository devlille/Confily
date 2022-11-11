package org.gdglille.devfest.android.components.structure

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.halilibo.richtext.markdown.Markdown
import com.halilibo.richtext.ui.RichText
import com.halilibo.richtext.ui.RichTextThemeIntegration
import org.gdglille.devfest.android.components.buttons.Socials
import org.gdglille.devfest.android.theme.Conferences4HallTheme
import org.gdglille.devfest.android.theme.placeholder
import org.gdglille.devfest.models.EventUi

@Composable
fun SocialsSection(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    detailed: String? = null,
    isLoading: Boolean = false,
    twitterUrl: String? = null,
    githubUrl: String? = null,
    linkedinUrl: String? = null,
    onLinkClicked: (url: String) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.placeholder(visible = isLoading)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.placeholder(visible = isLoading)
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (twitterUrl != null || githubUrl != null || linkedinUrl != null) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                twitterUrl?.let {
                    Socials.Twitter(
                        text = it,
                        onClick = { twitterUrl.let(onLinkClicked) },
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                }
                githubUrl?.let {
                    Socials.GitHub(
                        text = it,
                        onClick = { githubUrl.let(onLinkClicked) },
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                }
                linkedinUrl?.let {
                    Socials.LinkedIn(
                        text = it,
                        onClick = { linkedinUrl.let(onLinkClicked) },
                        modifier = Modifier.placeholder(visible = isLoading)
                    )
                }
            }
        }
        detailed?.let {
            Spacer(modifier = Modifier.height(12.dp))
            RichTextThemeIntegration(
                textStyle = { MaterialTheme.typography.bodyMedium },
                ProvideTextStyle = null,
                contentColor = { MaterialTheme.colorScheme.onSurface },
                ProvideContentColor = null,
            ) {
                RichText(
                    modifier = Modifier.placeholder(visible = isLoading)
                ) {
                    Markdown(detailed)
                }
            }
        }
    }
}

@Preview
@Composable
internal fun SocialsSectionPreview() {
    Conferences4HallTheme {
        SocialsSection(
            title = EventUi.fake.eventInfo.name,
            subtitle = EventUi.fake.eventInfo.date,
            twitterUrl = EventUi.fake.eventInfo.twitterUrl,
            githubUrl = EventUi.fake.eventInfo.twitterUrl,
            linkedinUrl = EventUi.fake.eventInfo.twitterUrl,
            onLinkClicked = {}
        )
    }
}