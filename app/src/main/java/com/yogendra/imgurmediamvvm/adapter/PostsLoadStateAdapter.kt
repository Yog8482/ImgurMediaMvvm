package com.yogendra.imgurmediamvvm.adapter

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.yogendra.imgurmediamvvm.adapter.common.NetworkStateItemViewHolder

class PostsLoadStateAdapter(
    private val adapter: PostsParentAdapter
) : LoadStateAdapter<NetworkStateItemViewHolder>() {


    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) {
        holder.bindTo(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateItemViewHolder {
        return NetworkStateItemViewHolder(parent) { adapter.retry() }
    }

}