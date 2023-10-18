package com.shtain.nba.presentation.player

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
class PlayerDetailsFragment : Fragment() {

    private val viewModel: PlayerDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.setPlayerId(arguments?.getLong("playerId"))
        return ComposeView(requireContext()).apply {
            setContent {
                PlayerDetailsScreen(
                    viewModel,
                    onBackClick = {
                        findNavController().popBackStack()
                    },
                    onTeamClick = { teamId ->
                        findNavController().navigate(
                            R.id.action_player_to_team,
                            args = bundleOf("teamId" to teamId)
                        )
                    },
                    onReloadClick = {
                        viewModel.reload()
                    })
            }
        }
    }
}