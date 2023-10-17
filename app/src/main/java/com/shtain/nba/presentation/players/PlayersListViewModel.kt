package com.shtain.nba.presentation.players

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.shtain.nba.data.repostories.players.PlayersRepository
import com.shtain.nba.presentation.common.base.ReloadableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayersListViewModel @Inject constructor(playerRepository: PlayersRepository) : ReloadableViewModel() {

    val playersItemsUiStates = refreshTrigger.flatMapLatest {
        playerRepository.getPlayers()
            .map { pagingData ->
                pagingData.map { player -> PlayerUiModel(player) }
            }
            .cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PagingData.empty())
}