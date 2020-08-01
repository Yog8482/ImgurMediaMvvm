package com.yogendra.imgurmediamvvm.binding

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.yogendra.imgurmediamvvm.adapter.PostsChildAdapter
import com.yogendra.imgurmediamvvm.model.PostImages
import com.yogendra.imgurmediamvvm.utils.GlideApp

/**
 * Data Binding adapters specific to the app.
 */

@BindingAdapter("isGone")
fun showHide(view: View, hide: Boolean) {
    view.visibility = if (hide) View.GONE else View.VISIBLE
}


@BindingAdapter("imageFromUrl")
fun bindImageFromUrl(view: ImageView, imageUrl: String?) {
    val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    if (!imageUrl.isNullOrEmpty()) {
        GlideApp.with(view.context)
            .load(imageUrl)
            .apply(requestOptions)
            .into(view)
    }
}


@BindingAdapter(value = ["setPostImages"])
fun RecyclerView.setRowImages(postImages: List<PostImages>?) {
    if (postImages != null) {
        val childAdapter = PostsChildAdapter()
        childAdapter.submitList(postImages)
        adapter = childAdapter
    }
}




