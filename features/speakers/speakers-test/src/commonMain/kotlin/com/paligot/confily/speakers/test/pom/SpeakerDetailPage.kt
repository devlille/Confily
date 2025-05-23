package com.paligot.confily.speakers.test.pom

import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyAncestor
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasContentDescription
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.paligot.confily.speakers.semantics.SpeakersSemantics

class SpeakerDetailPage(private val composeTestRule: ComposeTestRule) {
    private val backIcon = composeTestRule.onNodeWithContentDescription("Back")

    private fun findSpeakerName(name: String): SemanticsNodeInteraction = composeTestRule.onNode(
        hasText(name) and hasAnyAncestor(hasTestTag(SpeakersSemantics.pageDetail))
    )

    private fun findSpeakerCompany(company: String): SemanticsNodeInteraction =
        composeTestRule.onNodeWithText(company)

    private fun findSpeakerBio(bio: String): SemanticsNodeInteraction =
        composeTestRule.onNodeWithText(bio)

    private fun findSpeakerTalk(
        title: String,
        speakers: List<String>,
        room: String,
        time: Int,
        category: String
    ): SemanticsNodeInteraction {
        val description = "$title with ${speakers.joinToString(", ")} as speaker " +
            "in $room room during $time minutes in category $category "
        return composeTestRule.onNode(
            hasContentDescription(description) and hasClickAction()
        )
    }

    fun assertSpeakerNameIsDisplayed(name: String) {
        findSpeakerName(name).assertIsDisplayed()
    }

    fun assertSpeakerCompanyIsDisplayed(company: String) {
        findSpeakerCompany(company).assertIsDisplayed()
    }

    fun assertSpeakerBioIsDisplayed(bio: String) {
        findSpeakerBio(bio).assertIsDisplayed()
    }

    fun assertSpeakerTalkIsDisplayed(
        title: String,
        speakers: List<String>,
        room: String,
        time: Int,
        category: String
    ) {
        findSpeakerTalk(title, speakers, room, time, category).assertIsDisplayed()
    }

    fun backToSpeakersScreen() {
        backIcon.performClick()
    }
}
