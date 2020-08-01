package com.yogendra.imgurmediamvvm.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.yogendra.imgurmediamvvm.adapter.common.CustomViewHolder
import com.yogendra.imgurmediamvvm.databinding.ChildListItemBinding
import com.yogendra.imgurmediamvvm.model.PostImages
import com.yogendra.imgurmediamvvm.ui.search.SearchFragmentDirections

class PostsChildAdapter : ListAdapter<PostImages, CustomViewHolder>(Companion) {
    companion object : DiffUtil.ItemCallback<PostImages>() {
        override fun areItemsTheSame(oldItem: PostImages, newItem: PostImages): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: PostImages, newItem: PostImages): Boolean {
            return oldItem.image_id == newItem.image_id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ChildListItemBinding.inflate(inflater, parent, false)

        return CustomViewHolder(
            binding
        )
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val currentImage = getItem(position)
        val itemBinding = holder.binding as ChildListItemBinding

        itemBinding.clickListener = View.OnClickListener {
            navigateToDetails(
                itemBinding.root,
                currentImage.image_id,
                currentImage.title
            )
        }
        itemBinding.image = currentImage
        itemBinding.executePendingBindings()
    }

}

fun navigateToDetails(view: View, postImageId: String, title: String?) {
    val directions =
        SearchFragmentDirections.actionNavigationListToNavigationListItem(
            postImageId,
            title ?: "Details"
        )

    view.findNavController().navigate(
        directions
    )
}