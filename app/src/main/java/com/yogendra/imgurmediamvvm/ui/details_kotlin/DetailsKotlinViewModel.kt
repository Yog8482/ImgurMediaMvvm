package com.yogendra.imgurmediamvvm.ui.details_kotlin

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yogendra.imgurmediamvvm.model.PostImages
import com.yogendra.imgurmediamvvm.repository.DbDetailsRepository_Kotlin
import kotlinx.coroutines.coroutineScope

class DetailsKotlinViewModel internal constructor(
    val imageid: String,
    val repository: DbDetailsRepository_Kotlin
) : ViewModel() {
    val mData: LiveData<PostImages> = repository.loadData(imageid)

     val updateSuccess: MutableLiveData<Int> = MutableLiveData()

    fun updateData(postImages: PostImages){
        val res = repository.update(postImages)
        return updateSuccess.postValue(res)
    }

}

