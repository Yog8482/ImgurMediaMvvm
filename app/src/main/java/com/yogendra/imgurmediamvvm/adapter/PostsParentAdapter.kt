package com.yogendra.imgurmediamvvm.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yogendra.imgurmediamvvm.databinding.ChildListItemBinding
import com.yogendra.imgurmediamvvm.databinding.ParentListItemBinding
import com.yogendra.imgurmediamvvm.model.PostsData

class PostsParentAdapter :
    PagingDataAdapter<PostsData, PostsParentAdapter.ParentsPostViewHolder>(POST_COMPARATOR) {
    private val viewPool = RecyclerView.RecycledViewPool()

    override fun onBindViewHolder(holder: ParentsPostViewHolder, position: Int) {


        getItem(position)?.let {
            holder.apply {
                holder.bind(it)
                holder.binding.childRecyclerView.setRecycledViewPool(viewPool)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentsPostViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val binding = ParentListItemBinding.inflate(inflater, parent, false)

        return ParentsPostViewHolder(binding)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<PostsData>() {
            override fun areContentsTheSame(oldItem: PostsData, newItem: PostsData): Boolean =
                oldItem == newItem

            override fun areItemsTheSame(oldItem: PostsData, newItem: PostsData): Boolean =
                oldItem.id == newItem.id

        }

    }

    class ParentsPostViewHolder(val binding: ParentListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(post: PostsData) {
            binding.apply {
                binding.posts = post

            }
            binding.executePendingBindings()

        }

        companion object : DiffUtil.ItemCallback<PostsData>() {
            override fun areItemsTheSame(oldItem: PostsData, newItem: PostsData): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: PostsData, newItem: PostsData): Boolean {
                return oldItem.id == newItem.id
            }
        }

    }
}