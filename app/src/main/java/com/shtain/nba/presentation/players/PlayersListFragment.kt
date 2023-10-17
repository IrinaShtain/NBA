package com.shtain.nba.presentation.players

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.shtain.nba.R
import com.shtain.nba.databinding.FragmentPlayersBinding
import com.shtain.nba.data.network.NetworkException
import com.shtain.nba.presentation.common.extensions.isVisible
import com.shtain.nba.presentation.common.extensions.visibleOrGoneIf
import com.shtain.nba.presentation.common.viewbinding.ViewBindingHolder
import com.shtain.nba.presentation.common.viewbinding.ViewBindingHolderImpl
import com.shtain.nba.presentation.players.adapter.FooterLoadStateAdapter
import com.shtain.nba.presentation.players.adapter.PlayerClickListener
import com.shtain.nba.presentation.players.adapter.PlayerListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PlayersListFragment : Fragment(),
    ViewBindingHolder<FragmentPlayersBinding> by ViewBindingHolderImpl() {

    private val viewModel: PlayersListViewModel by viewModels()

    private lateinit var playerListAdapter: PlayerListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = initBinding(FragmentPlayersBinding.inflate(inflater, container, false), this) {

        playerListAdapter = PlayerListAdapter(object : PlayerClickListener {
            override fun onTeamDetails(teamId: Long) {
                findNavController().navigate(R.id.action_list_to_team,
                    args = bundleOf("teamId" to teamId))
            }

            override fun onPlayerClick(playerId: Long) {
                findNavController().navigate(resId = R.id.action_list_to_player,
                args = bundleOf("playerId" to playerId))
            }
        })

        recyclerView.apply {
            adapter = playerListAdapter.apply {
                withLoadStateFooter(FooterLoadStateAdapter())
                addLoadStateListener { state: CombinedLoadStates ->
                    val isLoading = state.refresh is LoadState.Loading
                    val isError = state.refresh is LoadState.Error && state.prepend is LoadState.NotLoading
                    val isAppendError = state.append is LoadState.Error && playerListAdapter.itemCount !=0
                    binding.apply {
                        progressBar.visibleOrGoneIf(isLoading && playerListAdapter.itemCount == 0)
                        placeHolder.visibleOrGoneIf(isError)
                        if (swipeRefreshLayout.isRefreshing && !isLoading)
                           swipeRefreshLayout.isRefreshing = false
                        recyclerView.visibleOrGoneIf(!isError && !progressBar.isVisible())
                        if (isAppendError) {
                            Toast.makeText(requireContext(), getString(
                                if ((state.append as LoadState.Error).error is NetworkException)
                                R.string.no_internet_connection
                            else R.string.no_server_connection),
                                Toast.LENGTH_LONG).show()
                        }
                        if (isError){
                            placeHolder.apply {
                                setTitle(getString(if ((state.refresh as LoadState.Error).error is NetworkException)
                                    R.string.no_internet_connection
                                else R.string.no_server_connection))
                                setDescription(getString(if ((state.refresh as LoadState.Error).error is NetworkException)
                                    R.string.check_network_connection
                                else R.string.check_server_connection))
                            }
                        }
                    }
                }
            }
            layoutManager =   LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        }

        placeHolder.setReloadClickListener {
            viewModel.reload()
        }

        swipeRefreshLayout.setOnRefreshListener {
            viewModel.reload()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.playersItemsUiStates
                .flowWithLifecycle(viewLifecycleOwner.lifecycle, Lifecycle.State.STARTED)
                .collect {
                    playerListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
                }
        }
    }
}