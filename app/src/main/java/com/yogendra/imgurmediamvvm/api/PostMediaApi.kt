package com.yogendra.imgurmediamvvm.api

import android.util.Log
import com.yogendra.imgurmediamvvm.BuildConfig
import com.yogendra.imgurmediamvvm.interceptor.AuthInterceptor
import com.yogendra.imgurmediamvvm.interceptor.NetworkConnectionInterceptor
import com.yogendra.imgurmediamvvm.model.PostsData
import com.yogendra.imgurmediamvvm.model.Response
import com.yogendra.imgurmediamvvm.utils.BASE_URL
import com.yogendra.imgurmediamvvm.utils.DEFAULT_URL_PARAMETRES_ARTICLE
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API communication setup
 */
interface PostMediaApi {

    @GET("$DEFAULT_URL_PARAMETRES_ARTICLE{page}")
    suspend fun getPosts(
        @Path("page") page: Int,
        @Query("q") query: String): Response

    companion object {
        fun create(): PostMediaApi {
            val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { Log.d("API", it) })
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addNetworkInterceptor(NetworkConnectionInterceptor())
                .addInterceptor(logger)
                .addInterceptor(AuthInterceptor(BuildConfig.API_TOKEN))
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(PostMediaApi::class.java)
        }
    }
}