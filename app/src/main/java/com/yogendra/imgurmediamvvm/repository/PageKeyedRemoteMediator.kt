package com.yogendra.imgurmediamvvm.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.yogendra.imgurmediamvvm.api.PostMediaApi
import com.yogendra.imgurmediamvvm.db.AppDatabase
import com.yogendra.imgurmediamvvm.db.PostDao
import com.yogendra.imgurmediamvvm.db.PostImagesDao
import com.yogendra.imgurmediamvvm.model.PostsData
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception
import java.lang.NullPointerException

@OptIn(ExperimentalPagingApi::class)
class PageKeyedRemoteMediator(
    private val db: AppDatabase,
    private val postsApi: PostMediaApi,
    private val searchquery: String
) : RemoteMediator<Int, PostsData>() {
    private val postDao: PostDao = db.posts()
    private val postsImagesDao: PostImagesDao = db.postImages()

    companion object {
        var startPage = 1
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostsData>
    ): MediatorResult {
        try {


            val loadKey = when (loadType) {
                LoadType.REFRESH -> startPage
                // In this example, we never need to prepend, since REFRESH will always load the
                // first page in the list. Immediately return, reporting end of pagination.
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                // Query remoteKeyDao for the next RemoteKey.
                LoadType.APPEND -> {
                    startPage +=1
                    val remoteKey = startPage
                    // We must explicitly check if the page key is `null` when appending,
                    // since `null` is only valid for initial load. If we receive `null`
                    // for APPEND, that means we have reached the end of pagination and
                    // there are no more items to load.
                    if (remoteKey == 10) {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }

                    remoteKey
                }
            }

            val data = postsApi.getPosts(
                page = loadKey,
                query = searchquery
            ).data


            db.withTransaction {
                data.forEach {
                    try {
                        it.searchquery = searchquery

                        it.images?.let { postImage ->
                            if (postImage.isNotEmpty())
                                postsImagesDao.insertAll(postImage)
                        }

                    } catch (e: NullPointerException) {
                        e.printStackTrace()
                    } catch (ex: Exception) {
                        ex.printStackTrace()
                    }
                }

                postDao.insertAll(data)

            }

            return MediatorResult.Success(endOfPaginationReached = data.isEmpty())
        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}
