package com.shtain.nba.presentation.player

import androidx.lifecycle.viewModelScope
import com.shtain.nba.data.models.Player
import com.shtain.nba.data.network.NetworkStatus
import com.shtain.nba.domain.player.PlayerRepository
import com.shtain.nba.presentation.common.base.ReloadableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerDetailsViewModel @Inject constructor(private val playerRepository : PlayerRepository) : ReloadableViewModel() {

    private val stateFlow = MutableStateFlow<Long?>(null)

    val playerData: Flow<NetworkStatus<Player>> = stateFlow
        .filterNotNull()
        .reloadWhenRefresh()
        .flatMapLatest {
            playerRepository.getPlayer(it)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(), NetworkStatus.Loading)

    fun setPlayerId(playerId: Long?) {
        stateFlow.value = playerId
    }
}