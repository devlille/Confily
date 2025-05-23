package com.paligot.confily.schedules.panes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.paligot.confily.resources.Resource
import com.paligot.confily.resources.screen_schedule_detail_event_session
import com.paligot.confily.schedules.ui.models.EventSessionUi
import com.paligot.confily.schedules.ui.schedule.EventSessionSection
import com.paligot.confily.schedules.ui.schedule.SessionAbstract
import com.paligot.confily.style.events.cards.AddressCard
import com.paligot.confily.style.theme.SpacingTokens
import com.paligot.confily.style.theme.appbars.TopAppBar
import com.paligot.confily.style.theme.toDp
import org.jetbrains.compose.resources.stringResource

@ExperimentalMaterial3Api
@Composable
fun EventSessionDetailPane(
    session: EventSessionUi,
    onItineraryClicked: (lat: Double, lng: Double) -> Unit,
    onBackClicked: () -> Unit,
    modifier: Modifier = Modifier,
    state: LazyListState = rememberLazyListState()
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = stringResource(Resource.string.screen_schedule_detail_event_session),
                navigationIcon = { Back(onClick = onBackClicked) },
                scrollBehavior = scrollBehavior
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = SpacingTokens.LargeSpacing.toDp()),
                contentPadding = it,
                verticalArrangement = Arrangement.spacedBy(SpacingTokens.LargeSpacing.toDp()),
                state = state
            ) {
                item {
                    Spacer(modifier = Modifier.height(SpacingTokens.LargeSpacing.toDp()))
                    EventSessionSection(session = session)
                }
                item {
                    SessionAbstract(abstract = session.description)
                }
                session.addressUi?.let {
                    item {
                        AddressCard(
                            formattedAddress = it.formattedAddress,
                            onItineraryClicked = {
                                onItineraryClicked(it.latitude, it.longitude)
                            },
                            hasGpsLocation = true
                        )
                    }
                }
                item {
                    Spacer(modifier = Modifier.height(SpacingTokens.ExtraLargeSpacing.toDp()))
                }
            }
        }
    )
}
