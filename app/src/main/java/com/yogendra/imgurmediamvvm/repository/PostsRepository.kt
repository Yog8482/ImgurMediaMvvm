package com.yogendra.imgurmediamvvm.repository

import androidx.paging.PagingData
import com.yogendra.imgurmediamvvm.model.PostsData
import kotlinx.coroutines.flow.Flow

interface PostsRepository{
    fun postsFromSearchQuery(searchQuery: String, pageSize: Int): Flow<PagingData<PostsData>>

}