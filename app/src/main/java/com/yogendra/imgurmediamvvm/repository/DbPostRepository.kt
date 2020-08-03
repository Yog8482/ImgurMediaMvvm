package com.yogendra.imgurmediamvvm.repository


import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.yogendra.imgurmediamvvm.api.PostMediaApi
import com.yogendra.imgurmediamvvm.db.AppDatabase
import com.yogendra.imgurmediamvvm.model.PostsData
import kotlinx.coroutines.flow.Flow

/**
 * Repository implementation that uses a database backed [androidx.paging.PagingSource] and
 * [androidx.paging.RemoteMediator] to load pages from network when there are no more items cached
 * in the database to load.
 */
class DbPostRepository(val db: AppDatabase, val postApi: PostMediaApi) : PostsRepository {

    override fun postsFromSearchQuery(searchQuery: String, pageSize: Int) = Pager(
        config = PagingConfig(pageSize),
//        pagingSourceFactory = { PageKeyedRemoteMediator(db, postApi, searchQuery) }
        remoteMediator = PageKeyedRemoteMediator(db, postApi, searchQuery)
    ) {
        db.posts().getPostsBySearchQuery(searchQuery)
    }.flow


}