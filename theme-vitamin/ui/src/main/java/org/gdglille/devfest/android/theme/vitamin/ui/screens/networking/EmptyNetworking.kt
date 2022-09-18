package org.gdglille.devfest.android.theme.vitamin.ui.screens.networking

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.decathlon.vitamin.compose.foundation.VitaminTheme
import org.gdglille.devfest.android.theme.vitamin.ui.R
import org.gdglille.devfest.android.theme.vitamin.ui.theme.Conferences4HallTheme

@Composable
fun EmptyNetworking(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 24.dp, horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Text(
            text = stringResource(R.string.text_empty_networking),
            style = VitaminTheme.typography.body2,
            color = VitaminTheme.colors.vtmnContentPrimary
        )
        Text(
            text = stringResource(R.string.text_here_we_go),
            style = VitaminTheme.typography.body2,
            color = VitaminTheme.colors.vtmnContentPrimary
        )
    }
}

@Preview
@Composable
fun EmptyNetworkingPreview() {
    Conferences4HallTheme {
        EmptyNetworking()
    }
}