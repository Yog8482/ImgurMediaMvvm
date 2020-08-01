package com.yogendra.imgurmediamvvm.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogendra.imgurmediamvvm.model.PostsData

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(posts: List<PostsData>)

    @Query("SELECT * FROM Posts where searchquery = :query")
    fun getPostsBySearchQuery(query: String): PagingSource<Int, PostsData>

//    @Query("SELECT * FROM Posts WHERE id = :postid")
//    suspend fun getPostImagesByPostId(postid: String): PagingSource<Int, PostsData>
}