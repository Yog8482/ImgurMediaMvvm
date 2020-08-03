package com.yogendra.imgurmediamvvm.db

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.yogendra.imgurmediamvvm.data.Converters
import com.yogendra.imgurmediamvvm.model.PostImages
import com.yogendra.imgurmediamvvm.model.PostsData
import com.yogendra.imgurmediamvvm.utils.DATABASE_NAME


@Database(
    entities = [PostsData::class, PostImages::class],
    version = 2 ,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun posts(): PostDao

    abstract fun postImages(): PostImagesDao

    companion object getDB {
        fun create(context: Context): AppDatabase {
            val databaseBuilder =
                Room.databaseBuilder(context, AppDatabase::class.java, DATABASE_NAME)

            return databaseBuilder
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build()
        }
    }
}