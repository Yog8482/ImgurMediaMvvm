package com.yogendra.imgurmediamvvm.repository

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.yogendra.imgurmediamvvm.db.AppDatabase.getDB.create
import com.yogendra.imgurmediamvvm.db.PostImagesDao
import com.yogendra.imgurmediamvvm.model.PostImages

class DbDetailsRepository_Kotlin constructor(application: Application) {

    val db = create(application)
    private var mDao: PostImagesDao = db.postImages()


     fun loadData(imageID: String): LiveData<PostImages> {
        return mDao.getPostImageByImageId(imageID)
    }


    fun update(data: PostImages): Int {
       return mDao.update(data)
    }


}