/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yogendra.imgurmediamvvm.ui.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.paging.PagingData
import com.yogendra.imgurmediamvvm.model.PostsData

import com.yogendra.imgurmediamvvm.repository.PostsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*

@ExperimentalCoroutinesApi
class SearchFragmentViewModel(
    private val repository: PostsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    companion object {
        const val KEY_SEARCHQUERY = "searchPosts"
        const val DEFAULT_SEARCHQUERY = ""
    }

    init {
        if (!savedStateHandle.contains(KEY_SEARCHQUERY)) {
            savedStateHandle.set(
                KEY_SEARCHQUERY,
                DEFAULT_SEARCHQUERY
            )
        }
    }

    private val clearListCh = BroadcastChannel<Unit>(Channel.CONFLATED)

    @OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
    val posts = flowOf(
        clearListCh.asFlow().map { PagingData.empty<PostsData>() },
        savedStateHandle.getLiveData<String>(KEY_SEARCHQUERY)
            .asFlow()
            .flatMapLatest { repository.postsFromSearchQuery(it, 10) }
    ).flattenMerge(2)

    fun shouldShowSearchPosts(
        searchq: String
    ) = savedStateHandle.get<String>(KEY_SEARCHQUERY) != searchq

    fun showSearchPosts(searchq: String) {
        if (!shouldShowSearchPosts(searchq)) return
        clearListCh.offer(Unit)
        savedStateHandle.set(KEY_SEARCHQUERY, searchq)
    }
}
