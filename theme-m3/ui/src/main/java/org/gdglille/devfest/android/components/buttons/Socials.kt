package org.gdglille.devfest.android.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Github
import compose.icons.fontawesomeicons.brands.LinkedinIn
import compose.icons.fontawesomeicons.brands.Twitter
import org.gdglille.devfest.android.theme.Conferences4HallTheme
import org.gdglille.devfest.android.theme.m3.ui.R
import org.gdglille.devfest.models.EventUi
import org.gdglille.devfest.models.SpeakerUi

object Socials {
    @Composable
    fun Twitter(
        text: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        IconButton(
            imageVector = FontAwesomeIcons.Brands.Twitter,
            contentDescription = stringResource(id = R.string.semantic_twitter, text),
            modifier = modifier,
            onClick = onClick
        )
    }

    @Composable
    fun GitHub(
        text: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        IconButton(
            imageVector = FontAwesomeIcons.Brands.Github,
            contentDescription = stringResource(id = R.string.semantic_github, text),
            modifier = modifier,
            onClick = onClick
        )
    }

    @Composable
    fun LinkedIn(
        text: String,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        IconButton(
            imageVector = FontAwesomeIcons.Brands.LinkedinIn,
            contentDescription = stringResource(id = R.string.semantic_linkedin, text),
            modifier = modifier,
            onClick = onClick
        )
    }
}

@Preview
@Composable
fun SocialItemPreview() {
    Conferences4HallTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Socials.Twitter(
                text = SpeakerUi.fake.twitter!!,
                onClick = {}
            )
            Socials.GitHub(
                text = SpeakerUi.fake.github!!,
                onClick = {}
            )
            Socials.LinkedIn(
                text = EventUi.fake.eventInfo.linkedin!!,
                onClick = {}
            )
        }
    }
}