package com.paligot.confily.networking.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.paligot.confily.navigation.ActionIds
import com.paligot.confily.navigation.TabActions
import com.paligot.confily.networking.panes.EmptyNetworkingContent
import com.paligot.confily.networking.ui.models.ExportNetworkingUi
import com.paligot.confily.resources.Resource
import com.paligot.confily.resources.screen_networking
import com.paligot.confily.resources.text_error
import com.paligot.confily.style.theme.Scaffold
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun NetworkingCompactVM(
    onCreateProfileClicked: () -> Unit,
    onContactScannerClicked: () -> Unit,
    onContactExportClicked: (ExportNetworkingUi) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NetworkingViewModel = koinViewModel()
) {
    val exportPath = viewModel.exportPath.collectAsState(null)
    LaunchedEffect(exportPath.value) {
        exportPath.value?.let(onContactExportClicked)
    }
    val title = stringResource(Resource.string.screen_networking)
    when (val uiState = viewModel.uiState.collectAsState().value) {
        is NetworkingUiState.Loading -> Scaffold(title = title, modifier = modifier) {
            EmptyNetworkingContent()
        }

        is NetworkingUiState.Success -> {
            val pagerState: PagerState =
                rememberPagerState(pageCount = { uiState.tabActionsUi.actions.count() })
            LaunchedEffect(pagerState.currentPage) {
                viewModel.innerScreenConfig(uiState.tabActionsUi.actions[pagerState.currentPage].route)
            }
            Scaffold(
                title = title,
                topActions = uiState.topActionsUi,
                tabActions = uiState.tabActionsUi,
                fabAction = uiState.fabAction,
                onActionClicked = {
                    when (it.id) {
                        ActionIds.EXPORT -> {
                            viewModel.exportNetworking()
                        }
                    }
                },
                onFabActionClicked = {
                    when (it.id) {
                        ActionIds.CREATE_PROFILE -> {
                            onCreateProfileClicked()
                        }

                        ActionIds.SCAN_CONTACTS -> {
                            onContactScannerClicked()
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
                        TabActions.myProfile.route -> MyProfileCompactVM(
                            onEditInformation = onCreateProfileClicked
                        )

                        TabActions.contacts.route -> ContactsCompactVM()

                        else -> TODO("Screen not implemented")
                    }
                }
            }
        }

        is NetworkingUiState.Failure -> Text(text = stringResource(Resource.string.text_error))
    }
}
