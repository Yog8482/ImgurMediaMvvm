package com.yogendra.imgurmediamvvm.ui.details_kotlin

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yogendra.imgurmediamvvm.ServiceLocator.Companion.instance


class DetailsKotlinfragmentFactory internal constructor(
    private val mApplication: Application,
    private val imageId: String
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetailsKotlinViewModel::class.java)) {
            val repository =
                instance(mApplication)
                    .getdetailsRepository()

            return DetailsKotlinViewModel(imageId, repository) as T
        }
        throw IllegalArgumentException("Cannot create Instance for this class")
    }

}