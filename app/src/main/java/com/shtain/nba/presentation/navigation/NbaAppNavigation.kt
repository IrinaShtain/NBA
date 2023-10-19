package com.shtain.nba.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.shtain.nba.presentation.NbaTheme
import com.shtain.nba.presentation.navigation.Destinations.PlayerDetails
import com.shtain.nba.presentation.navigation.Destinations.PlayerDetailArgs.PlayerId
import com.shtain.nba.presentation.navigation.Destinations.PlayersList
import com.shtain.nba.presentation.navigation.Destinations.TeamDetails
import com.shtain.nba.presentation.navigation.Destinations.TeamDetailArgs.TeamId
import com.shtain.nba.presentation.player.PlayerDetailsScreen
import com.shtain.nba.presentation.players.PlayersListScreen
import com.shtain.nba.presentation.team.TeamDetailsScreen

@Composable
fun NbaAppNavigation() {
    val navController = rememberNavController()
    val actions = remember(navController) { Actions(navController) }
    NbaTheme {
        NavHost(navController = navController, startDestination = PlayersList) {
            composable(PlayersList) {
                PlayersListScreen(
                    onTeamClick = actions.openTeamDetails,
                    onPlayerClick = actions.openPlayerDetails
                )
            }
            composable(
                "$PlayerDetails/{$PlayerId}",
                arguments = listOf(navArgument(PlayerId) { type = NavType.LongType })
            ) { backStackEntry ->
                PlayerDetailsScreen(
                    playerId = backStackEntry.arguments?.getLong(PlayerId) ?: 0L,
                    onBackClick = actions.navigateBack,
                    onTeamClick = actions.openTeamDetails
                )
            }
            composable(
                "$TeamDetails/{$TeamId}",
                arguments = listOf(navArgument(TeamId) { type = NavType.LongType })
            ) { backStackEntry ->
                TeamDetailsScreen(
                    teamId = backStackEntry.arguments?.getLong(TeamId) ?: 0L,
                    onBackClick = actions.navigateBack
                )
            }
        }
    }
}