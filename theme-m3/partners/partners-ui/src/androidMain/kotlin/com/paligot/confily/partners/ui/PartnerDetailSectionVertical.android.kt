package com.paligot.confily.partners.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.paligot.confily.models.ui.PartnerItemUi
import com.paligot.confily.style.theme.Conferences4HallTheme

@Suppress("UnusedPrivateMember")
@Preview
@Composable
private fun PartnerDetailSectionVerticalPreview() {
    Conferences4HallTheme {
        PartnerDetailSectionVertical(
            partnerItemUi = PartnerItemUi.fake,
            onLinkClicked = {}
        )
    }
}