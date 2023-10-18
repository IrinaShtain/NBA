package com.shtain.nba.presentation.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TeamDetailsFragment : Fragment() {

    private val viewModel: TeamDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.setTeamId(arguments?.getLong("teamId"))
        return ComposeView(requireContext()).apply {
            setContent {
                TeamDetailsScreen(
                    viewModel,
                    onBackClick = {
                        findNavController().popBackStack()
                    },
                    onReloadClick = {
                        viewModel.reload()
                    })
            }
        }
    }
}