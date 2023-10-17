package com.shtain.nba.presentation.players.adapter

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.shtain.nba.databinding.ListItemPlayerBinding
import com.shtain.nba.presentation.common.viewbinding.inflateVB
import com.shtain.nba.presentation.players.PlayerUiModel
import javax.inject.Inject


class PlayerListAdapter @Inject constructor(private val listener: PlayerClickListener) :
    PagingDataAdapter<PlayerUiModel, RecyclerView.ViewHolder>(PaymentTemplateComparator) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        TemplateViewHolder(parent.inflateVB(ListItemPlayerBinding::inflate))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getItem(position)?.let { (holder as TemplateViewHolder).bind(it, listener) }
    }

    object PaymentTemplateComparator : DiffUtil.ItemCallback<PlayerUiModel>() {

        override fun areItemsTheSame(
            oldItem: PlayerUiModel,
            newItem: PlayerUiModel
        ): Boolean {
            return oldItem.playerId == newItem.playerId
        }

        override fun areContentsTheSame(
            oldItem: PlayerUiModel,
            newItem: PlayerUiModel
        ): Boolean {
            return oldItem == newItem
        }
    }
}

private class TemplateViewHolder(val binding: ListItemPlayerBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: PlayerUiModel, listener: PlayerClickListener) {
        binding.apply {
            nameTextView.text = data.fullName
            positionTextView.text = data.position
            teamTextView.text = data.teamName
            teamTextView.setOnClickListener {
                listener.onTeamDetails(data.teamId)
            }
            root.setOnClickListener {
                listener.onPlayerClick(data.playerId)
            }

        }
    }
}

interface PlayerClickListener {
    fun onTeamDetails(teamId: Long)
    fun onPlayerClick(playerId: Long)
}
