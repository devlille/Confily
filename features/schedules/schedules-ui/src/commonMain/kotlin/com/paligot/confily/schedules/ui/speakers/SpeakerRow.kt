package com.paligot.confily.schedules.ui.speakers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.paligot.confily.schedules.ui.models.SpeakerItemUi
import com.paligot.confily.style.components.placeholder.placeholder
import com.paligot.confily.style.speakers.items.LargeSpeakerItem
import com.paligot.confily.style.theme.ConfilyTheme
import com.paligot.confily.style.theme.SpacingTokens
import com.paligot.confily.style.theme.toDp
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun SpeakerItemRow(
    speakers: ImmutableList<SpeakerItemUi>,
    modifier: Modifier = Modifier,
    maxItems: Int = 2,
    isLoading: Boolean = false,
    onSpeakerItemClick: (SpeakerItemUi) -> Unit
) {
    val horizontalSpacing = SpacingTokens.MediumSpacing.toDp()
    BoxWithConstraints(modifier = modifier.fillMaxWidth()) {
        val width = (this.maxWidth - (horizontalSpacing * (maxItems - 1))) / maxItems
        Row(horizontalArrangement = Arrangement.spacedBy(horizontalSpacing)) {
            speakers.forEach {
                LargeSpeakerItem(
                    name = it.displayName,
                    description = it.activity,
                    url = it.photoUrl,
                    modifier = Modifier
                        .width(width)
                        .placeholder(isLoading),
                    onClick = { onSpeakerItemClick(it) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun SpeakerItemRowPreview() {
    ConfilyTheme {
        SpeakerItemRow(
            speakers = persistentListOf(SpeakerItemUi.fake, SpeakerItemUi.fake),
            onSpeakerItemClick = {}
        )
    }
}
