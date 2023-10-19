package com.shtain.nba.presentation.players

import com.shtain.nba.data.models.Player


data class PlayerUiModel constructor(
    val playerId: Long,
    val fullName: String,
    val position: String,
    val teamName: String,
    val teamId: Long,
) {
    constructor(player: Player) : this(
        player.id,
        player.fullName,
        player.position,
        player.team.name,
        player.team.id
    )
}