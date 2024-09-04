package com.paligot.confily.speakers.test.robot

import androidx.compose.ui.test.junit4.ComposeTestRule
import com.paligot.confily.core.test.patterns.navigation.RobotNavigator
import com.paligot.confily.core.test.patterns.navigation.navigateTo
import com.paligot.confily.speakers.test.pom.SpeakerDetailPOM
import com.paligot.confily.speakers.test.scopes.SpeakerDetailRobotScope
import com.paligot.confily.speakers.test.scopes.SpeakersGridRobotScope

class SpeakerDetailRobot(
    private val navigator: RobotNavigator,
    composeTestRule: ComposeTestRule
) : SpeakerDetailRobotScope {
    private val speakerDetailPom = SpeakerDetailPOM(composeTestRule)

    override fun assertSpeakerInfoAreDisplayed(name: String, company: String?, bio: String) {
        speakerDetailPom.apply {
            assertSpeakerNameIsDisplayed(name)
            if (company != null) {
                assertSpeakerCompanyIsDisplayed(company)
            }
            assertSpeakerBioIsDisplayed(bio)
        }
    }

    override fun assertSpeakerTalkIsDisplayed(
        title: String,
        speakers: List<String>,
        room: String,
        time: Int,
        category: String
    ) {
        speakerDetailPom.assertSpeakerTalkIsDisplayed(title, speakers, room, time, category)
    }

    override fun backToSpeakersScreen(block: SpeakersGridRobotScope.() -> Unit): SpeakersGridRobotScope {
        speakerDetailPom.backToSpeakersScreen()
        return navigator.navigateTo<SpeakersGridRobotScope>().apply(block)
    }
}