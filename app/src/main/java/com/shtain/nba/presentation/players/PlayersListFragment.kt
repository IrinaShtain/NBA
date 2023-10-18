package com.shtain.nba.presentation.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.shtain.nba.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PlayersListFragment : Fragment() {

    private val viewModel: PlayersListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                PlayersListScreen(
                    viewModel,
                    onTeamClick = { teamId ->
                        findNavController().navigate(
                            R.id.action_list_to_team,
                            args = bundleOf("teamId" to teamId)
                        )
                    },
                    onPlayerClick = { playerId ->
                        findNavController().navigate(
                            R.id.action_list_to_player,
                            args = bundleOf("playerId" to playerId)
                        )
                    },
                    onReloadClick = {
                        viewModel.reload()
                    })
            }
        }
    }
}