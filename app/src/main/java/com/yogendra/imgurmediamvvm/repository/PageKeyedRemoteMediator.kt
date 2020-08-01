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


    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PostsData>
    ): MediatorResult {
        try {

            val data = postsApi.getPosts(
                page = 1,
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
