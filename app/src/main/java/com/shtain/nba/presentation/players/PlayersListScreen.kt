package com.shtain.nba.presentation.players

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.shtain.nba.R
import com.shtain.nba.data.network.NetworkException
import com.shtain.nba.presentation.common.components.CircularProgress
import com.shtain.nba.presentation.common.components.DetailsInfoValue
import com.shtain.nba.presentation.common.components.ErrorStateHolder
import com.shtain.nba.presentation.common.components.Name
import com.shtain.nba.presentation.common.components.NbaMark
import com.shtain.nba.presentation.common.components.ToolbarData

@Composable
fun PlayersListScreen(
    viewModel: PlayersListViewModel = hiltViewModel(),
    onTeamClick: (Long) -> Unit,
    onPlayerClick: (Long) -> Unit
) {
    val context = LocalContext.current
    val pagingItems: LazyPagingItems<PlayerUiModel> =
        viewModel.playersItemsUiStates.collectAsLazyPagingItems()
    ToolbarData(R.string.players) { paddingValues ->
        LazyColumn(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            items(pagingItems.itemCount) { index ->
                PlayerContent(
                    pagingItems[index],
                    onPlayerClick,
                    onTeamClick
                )
            }
            pagingItems.apply {
                val isLoading = loadState.refresh is LoadState.Loading
                val isError =
                    loadState.refresh is LoadState.Error && loadState.prepend is LoadState.NotLoading
                val isAppendError =
                    loadState.append is LoadState.Error && pagingItems.itemCount != 0

                when {
                    isLoading && pagingItems.itemCount == 0 -> {
                        item { CircularProgress(paddingValues) }
                    }

                    isError -> {
                        item {
                            ErrorStateHolder(
                                paddingValues,
                                if ((loadState.refresh as LoadState.Error).error is NetworkException)
                                    R.string.no_internet_connection
                                else R.string.no_server_connection,
                                if ((loadState.refresh as LoadState.Error).error is NetworkException)
                                    R.string.check_network_connection
                                else R.string.check_server_connection
                            ) { viewModel.reload() }
                        }
                    }
                }

                if (isAppendError) {
                    Toast.makeText(context, context.getString(
                        if ((loadState.append as LoadState.Error).error is NetworkException)
                            R.string.no_internet_connection
                        else R.string.no_server_connection),
                        Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Composable
private fun PlayerContent(
    playerUiModel: PlayerUiModel?,
    onPlayerClick: (Long) -> Unit,
    onTeamClick: (Long) -> Unit
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
            .clickable {
                playerUiModel?.playerId?.let {
                    onPlayerClick(it)
                }
            }
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clip(RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(2.dp)
        ) {
            PlayerDetails(
                Modifier
                    .weight(3f)
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(12.dp),
                playerUiModel,
                onTeamClick
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
private fun PlayerDetails(
    modifier: Modifier = Modifier,
    playerUiModel: PlayerUiModel?,
    onTeamClick: (Long) -> Unit
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Name(playerUiModel?.fullName ?: "")
        DetailsInfoValue(R.string.team, playerUiModel?.teamName ?: "") {
            playerUiModel?.teamId?.let {
                onTeamClick(it)
            }
        }
    }
}