package com.shtain.nba.presentation.player

import com.shtain.nba.data.repostories.player.PlayerRepository
import com.shtain.nba.presentation.common.base.ReloadableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class PlayerDetailsViewModel @Inject constructor(private val playerRepository : PlayerRepository) : ReloadableViewModel() {

    private val stateFlow = MutableStateFlow<Long?>(null)

   val playerData = stateFlow
       .filterNotNull()
       .reloadWhenRefresh()
       .flatMapLatest {
       playerRepository.getPlayer(it)
   }

    fun setPlayerId(playerId: Long?) {
        stateFlow.value = playerId
    }
}