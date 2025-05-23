package com.paligot.confily.events.panes

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.unit.dp
import com.paligot.confily.events.semantics.EventListSemantics
import com.paligot.confily.events.ui.EventItem
import com.paligot.confily.events.ui.models.EventItemListUi
import com.paligot.confily.navigation.TabActions
import com.paligot.confily.resources.Resource
import com.paligot.confily.resources.screen_events
import com.paligot.confily.style.theme.ConfilyTheme
import com.paligot.confily.style.theme.Scaffold
import com.paligot.confily.style.theme.actions.TabActionsUi
import kotlinx.collections.immutable.persistentListOf
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun EventListPane(
    events: EventItemListUi,
    onEventClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false
) {
    val tabActions = remember {
        TabActionsUi(
            actions = persistentListOf(
                TabActions.futureEvents,
                TabActions.pastEvents
            )
        )
    }
    val pagerState = rememberPagerState(pageCount = { tabActions.actions.count() })
    Scaffold(
        title = stringResource(Resource.string.screen_events),
        tabActions = tabActions,
        pagerState = pagerState,
        modifier = modifier
    ) { padding ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.semantics { testTag = EventListSemantics.pager }
        ) { page ->
            val items = if (page == 0) events.future else events.past
            LazyColumn(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .semantics { testTag = EventListSemantics.list },
                contentPadding = PaddingValues(vertical = 24.dp)
            ) {
                items(items, key = { it.id }) { item ->
                    EventItem(
                        item = item,
                        isLoading = isLoading,
                        onClick = onEventClicked,
                        modifier = Modifier.animateItem(fadeInSpec = null, fadeOutSpec = null)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EventListPanePreview() {
    ConfilyTheme {
        EventListPane(events = EventItemListUi.fake, onEventClicked = {})
    }
}
