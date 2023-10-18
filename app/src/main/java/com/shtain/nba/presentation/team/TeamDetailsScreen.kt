package com.shtain.nba.presentation.team

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.shtain.nba.R
import com.shtain.nba.data.models.Team
import com.shtain.nba.data.network.NetworkStatus
import com.shtain.nba.presentation.common.components.CircularProgress
import com.shtain.nba.presentation.common.components.DetailsInfoValue
import com.shtain.nba.presentation.common.components.Name
import com.shtain.nba.presentation.common.components.NbaMark
import com.shtain.nba.presentation.common.components.PlaceHolderState
import com.shtain.nba.presentation.common.components.ToolbarData


@Composable
fun TeamDetailsScreen(
    teamDetailsViewModel: TeamDetailsViewModel,
    onBackClick: () -> Unit,
    onReloadClick: () -> Unit
) {
    val state by teamDetailsViewModel.teamData.collectAsState(NetworkStatus.Loading)
    ToolbarData(R.string.players_team, onBackClick = onBackClick) { paddingValues ->
        when (state) {
            is NetworkStatus.Error -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    PlaceHolderState(
                        paddingValues,
                        R.string.no_server_connection,
                        R.string.check_server_connection,
                        onReloadClick
                    )
                }
            }

            NetworkStatus.Loading -> {
                CircularProgress(paddingValues)
            }

            NetworkStatus.NoInternet -> {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {
                    PlaceHolderState(
                        paddingValues,
                        R.string.no_internet_connection,
                        R.string.check_network_connection,
                        onReloadClick
                    )
                }
            }

            is NetworkStatus.Success -> {
                val data = (state as NetworkStatus.Success<Team>).data
                TeamContent(paddingValues, data)
            }
        }
    }
}

@Preview
@Composable
private fun TeamContentPreview() {
    MaterialTheme {
        TeamContent(
            PaddingValues(),
            Team(0L, "", "8", "", "", "team name", "")
        )
    }
}

@Composable
private fun TeamContent(
    paddingValues: PaddingValues,
    team: Team
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = colorResource(R.color.colorCardBackground),
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        ),
        border = BorderStroke(1.dp, Color.Black),
        modifier = Modifier
            .wrapContentHeight(Alignment.Top)
            .fillMaxWidth()
            .padding(paddingValues)
            .padding(12.dp),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
        ) {
            TeamDetails(
                Modifier
                    .weight(3f)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp),
                team
            )

            NbaMark(
                Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .wrapContentWidth()
                    .wrapContentHeight()
            )
        }
    }
}

@Composable
private fun TeamDetails(
    modifier: Modifier = Modifier,
    team: Team
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        Name(team.fullName)
        DetailsInfoValue(R.string.short_name, team.name)
        DetailsInfoValue(R.string.abbreviation, team.abbreviation)
        DetailsInfoValue(R.string.city, team.city)
        DetailsInfoValue(R.string.conference, team.conference)
        DetailsInfoValue(R.string.division, team.division)
    }
}