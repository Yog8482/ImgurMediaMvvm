package com.yogendra.imgurmediamvvm.db

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.yogendra.imgurmediamvvm.model.PostImages

@Dao
interface PostImagesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(images: List<PostImages>)

    @Query("SELECT * FROM Post_Images WHERE image_id = :imageid")
    fun getPostImageByImageId(imageid: String): PostImages

    @Query("UPDATE Post_Images SET local_comment = :comment WHERE image_id = :imageid")
     fun updatePostImageComment(comment: String, imageid: String):Int

    @Query("SELECT local_comment FROM Post_Images WHERE image_id = :imageid")
     fun getPostImageCommentByimageId(imageid: String): String
}