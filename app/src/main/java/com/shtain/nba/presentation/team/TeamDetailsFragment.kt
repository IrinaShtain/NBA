package com.shtain.nba.presentation.team

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.shtain.nba.R
import com.shtain.nba.databinding.FragmentTeamDetailsBinding
import com.shtain.nba.data.network.NetworkStatus
import com.shtain.nba.presentation.common.extensions.toGone
import com.shtain.nba.presentation.common.extensions.toVisible
import com.shtain.nba.presentation.common.viewbinding.ViewBindingHolder
import com.shtain.nba.presentation.common.viewbinding.ViewBindingHolderImpl
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamDetailsFragment  : Fragment(),
    ViewBindingHolder<FragmentTeamDetailsBinding> by ViewBindingHolderImpl() {

    private val viewModel: TeamDetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentTeamDetailsBinding.inflate(inflater, container, false), this) {
        viewModel.setTeamId(arguments?.getLong("teamId"))

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
            viewModel.teamData
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect { status ->
                    when(status){
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
                                    setDescription(getString(R.string.check_server_connection))
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
                                abbreviationTextView.text = status.data.abbreviation
                                cityTextView.text = status.data.city
                                conferenceTextView.text = status.data.conference
                                divisionTextView.text = status.data.division
                                shortNameTextView.text = status.data.name
                            }
                        }
                    }

                }
        }
    }
}