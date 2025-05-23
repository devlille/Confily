package com.paligot.confily.infos.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.paligot.confily.navigation.ActionIds
import com.paligot.confily.navigation.TabActions
import com.paligot.confily.resources.Resource
import com.paligot.confily.resources.screen_info
import com.paligot.confily.resources.text_error
import com.paligot.confily.style.theme.Scaffold
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun InfoCompactVM(
    onItineraryClicked: (lat: Double, lng: Double) -> Unit,
    onLinkClicked: (url: String?) -> Unit,
    onVersionClicked: () -> Unit,
    onTicketScannerClicked: () -> Unit,
    onDisconnectedClicked: () -> Unit,
    onReportByPhoneClicked: (String) -> Unit,
    onReportByEmailClicked: (String) -> Unit,
    onTeamMemberClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: InfoViewModel = koinViewModel()
) {
    val title = stringResource(Resource.string.screen_info)
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is InfoUiState.Loading -> Scaffold(title = title, modifier = modifier) {
            EventVM(
                onLinkClicked = onLinkClicked,
                onItineraryClicked = onItineraryClicked,
                onVersionClicked = onVersionClicked,
                modifier = Modifier.fillMaxSize()
            )
        }

        is InfoUiState.Success -> {
            val pagerState = rememberPagerState(
                pageCount = { uiState.tabActionsUi.actions.count() }
            )
            LaunchedEffect(pagerState.currentPage) {
                viewModel.innerScreenConfig(uiState.tabActionsUi.actions[pagerState.currentPage].route)
            }
            Scaffold(
                title = title,
                modifier = modifier,
                topActions = uiState.topActionsUi,
                tabActions = uiState.tabActionsUi,
                fabAction = uiState.fabAction,
                onActionClicked = {
                    when (it.id) {
                        ActionIds.DISCONNECT -> {
                            viewModel.disconnect()
                            onDisconnectedClicked()
                        }
                    }
                },
                onFabActionClicked = {
                    when (it.id) {
                        ActionIds.SCAN_TICKET -> {
                            onTicketScannerClicked()
                        }

                        else -> TODO("Fab not implemented")
                    }
                },
                pagerState = pagerState
            ) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.padding(it)
                ) { page ->
                    when (uiState.tabActionsUi.actions[page].route) {
                        TabActions.event.route -> EventVM(
                            onLinkClicked = onLinkClicked,
                            onItineraryClicked = onItineraryClicked,
                            onVersionClicked = onVersionClicked,
                            modifier = Modifier.fillMaxSize()
                        )

                        TabActions.maps.route -> MapItemListVM(
                            modifier = Modifier.fillMaxSize()
                        )

                        TabActions.menus.route -> MenusVM(
                            modifier = Modifier.fillMaxSize()
                        )

                        TabActions.qanda.route -> QAndAListVM(
                            onLinkClicked = onLinkClicked,
                            modifier = Modifier.fillMaxSize()
                        )

                        TabActions.coc.route -> CoCVM(
                            onReportByPhoneClicked = onReportByPhoneClicked,
                            onReportByEmailClicked = onReportByEmailClicked,
                            modifier = Modifier.fillMaxSize()
                        )

                        TabActions.teamMembers.route -> TeamMemberListVM(
                            onTeamMemberClicked = onTeamMemberClicked,
                            modifier = Modifier.fillMaxSize()
                        )

                        else -> TODO("Screen not implemented")
                    }
                }
            }
        }

        is InfoUiState.Failure -> Text(text = stringResource(Resource.string.text_error))
    }
}
