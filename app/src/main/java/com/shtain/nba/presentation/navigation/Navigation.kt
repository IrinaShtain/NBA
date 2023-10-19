package com.shtain.nba.presentation.navigation

import androidx.navigation.NavHostController
import com.shtain.nba.presentation.navigation.Destinations.PlayerDetails
import com.shtain.nba.presentation.navigation.Destinations.TeamDetails

object Destinations {
    const val PlayersList = "playersList"
    const val PlayerDetails = "playerDetails"
    const val TeamDetails = "teamDetails"

    object TeamDetailArgs {
        const val TeamId = "teamId"
    }
    object PlayerDetailArgs {
        const val PlayerId = "playerId"
    }
}

class Actions(navController: NavHostController) {
    val openTeamDetails: (Long) -> Unit = { teamId ->
        navController.navigate("$TeamDetails/$teamId")
    }
    val openPlayerDetails: (Long) -> Unit = { playerId ->
        navController.navigate("$PlayerDetails/$playerId")
    }
    val navigateBack: () -> Unit = {
        navController.popBackStack()
    }
}