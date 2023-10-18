package com.shtain.nba.presentation.player

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shtain.nba.R
import com.shtain.nba.data.network.NetworkStatus
import com.shtain.nba.databinding.FragmentPlayersDetailsBinding
import com.shtain.nba.presentation.common.extensions.toGone
import com.shtain.nba.presentation.common.extensions.toVisible
import com.shtain.nba.presentation.common.viewbinding.ViewBindingHolder
import com.shtain.nba.presentation.common.viewbinding.ViewBindingHolderImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayerDetailsFragment : Fragment(),
    ViewBindingHolder<FragmentPlayersDetailsBinding> by ViewBindingHolderImpl() {

    private val viewModel: PlayerDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentPlayersDetailsBinding.inflate(inflater, container, false), this) {

        viewModel.setPlayerId(arguments?.getLong("playerId"))

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.reload()
        }

        toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.playerData
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { status ->
                    when (status) {
                        NetworkStatus.Loading -> {
                            binding.apply {
                                progressBar.toVisible()
                                cardContent.toGone()
                            }
                        }

                        is NetworkStatus.Error -> {
                            binding.apply {
                                swipeRefreshLayout.isRefreshing = false
                                progressBar.toGone()
                                cardContent.toGone()
                                placeHolder.apply {
                                    toVisible()
                                    setTitle(getString(R.string.no_server_connection))
                                    setDescription(status.message)
                                }
                            }
                        }

                        NetworkStatus.NoInternet -> {
                            binding.apply {
                                swipeRefreshLayout.isRefreshing = false
                                progressBar.toGone()
                                cardContent.toGone()
                                placeHolder.apply {
                                    toVisible()
                                    setTitle(getString(R.string.no_internet_connection))
                                    setDescription(getString(R.string.check_network_connection))
                                }
                            }
                        }

                        is NetworkStatus.Success -> {
                            binding.apply {
                                swipeRefreshLayout.isRefreshing = false
                                cardContent.toVisible()
                                progressBar.toGone()
                                placeHolder.toGone()
                                nameTextView.text = status.data.fullName
                                positionTextView.text = status.data.position
                                teamTextView.text = status.data.team.fullName
                                heightTextView.text =
                                    getString(R.string.inches, status.data.heightInches.toString())
                                weightTextView.text =
                                    getString(R.string.pounds, status.data.weightPounds.toString())
                                teamTextView.setOnClickListener {
                                    findNavController().navigate(
                                        R.id.action_player_to_team,
                                        args = bundleOf("teamId" to status.data.team.id)
                                    )
                                }
                            }
                        }
                    }

                }
        }
    }
}