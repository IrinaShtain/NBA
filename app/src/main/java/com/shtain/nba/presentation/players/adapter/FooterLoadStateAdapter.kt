package com.shtain.nba.presentation.players.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.shtain.nba.databinding.ListItemFootLoaderBinding
import com.shtain.nba.presentation.common.extensions.visibleOrGoneIf
import com.shtain.nba.presentation.common.viewbinding.inflateVB


class FooterLoadStateAdapter : LoadStateAdapter<FooterViewHolder>() {
    override fun onBindViewHolder(holder: FooterViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): FooterViewHolder {
        return FooterViewHolder(parent.inflateVB(ListItemFootLoaderBinding::inflate))
    }
}


class FooterViewHolder(
    private val binding: ListItemFootLoaderBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.footerProgressBar.visibleOrGoneIf(loadState is LoadState.Loading)
    }
}