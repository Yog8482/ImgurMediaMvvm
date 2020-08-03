package com.yogendra.imgurmediamvvm

import android.app.Application
import com.yogendra.imgurmediamvvm.api.PostMediaApi
import com.yogendra.imgurmediamvvm.db.AppDatabase
import com.yogendra.imgurmediamvvm.repository.DbDetailsRepository
import com.yogendra.imgurmediamvvm.repository.DbDetailsRepository_Kotlin
import com.yogendra.imgurmediamvvm.repository.DbPostRepository
import com.yogendra.imgurmediamvvm.repository.PostsRepository

/**
 * Super simplified service locator implementation to allow us to replace default implementations
 * for testing.
 */
interface ServiceLocator {
    companion object {
        private val LOCK = Any()
        private var instance: ServiceLocator? = null
        fun instance(context: Application): ServiceLocator {
            synchronized(LOCK) {
                if (instance == null) {
                    instance = DefaultServiceLocator(
                        app = context
                    )
                }
                return instance!!
            }
        }

    }

    fun getRepository(): PostsRepository
    fun getdetailsRepository(): DbDetailsRepository_Kotlin

    fun getApi(): PostMediaApi
}

/**
 * default implementation of ServiceLocator that uses production endpoints.
 */
open class DefaultServiceLocator(val app: Application) : ServiceLocator {
    private val db by lazy {
        AppDatabase.create(app)
    }

    private val postapi by lazy {
        PostMediaApi.create()
    }

    override fun getRepository(): PostsRepository {
        return DbPostRepository(
            db = db,
            postApi = getApi()
        )
    }

    override fun getdetailsRepository(): DbDetailsRepository_Kotlin {
        return DbDetailsRepository_Kotlin(
            application = app
        )
    }

    override fun getApi(): PostMediaApi = postapi
}