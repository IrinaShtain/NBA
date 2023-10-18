package com.shtain.nba.presentation.team

import com.shtain.nba.domain.team.TeamRepository
import com.shtain.nba.presentation.common.base.ReloadableViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel @Inject constructor(private val teamRepository: TeamRepository) :
    ReloadableViewModel() {

    private val stateFlow = MutableStateFlow<Long?>(null)

    val teamData = stateFlow
        .filterNotNull()
        .reloadWhenRefresh()
        .flatMapLatest {
            teamRepository.getTeam(it)
        }

    fun setTeamId(teamId: Long?) {
        stateFlow.value = teamId
    }
}