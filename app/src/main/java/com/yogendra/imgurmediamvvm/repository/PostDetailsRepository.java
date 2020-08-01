package com.yogendra.imgurmediamvvm.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.yogendra.imgurmediamvvm.db.AppDatabase;
import com.yogendra.imgurmediamvvm.db.PostImagesDao;
import com.yogendra.imgurmediamvvm.model.PostImages;

import kotlinx.coroutines.GlobalScope;

public class PostDetailsRepository {

    private static volatile PostDetailsRepository repo_instance = null;
    private AppDatabase localdb;
    final static Object lock = new Object();


    static PostImagesDao postDao;

    private PostDetailsRepository(Application application) {
        this.localdb = AppDatabase.Companion.create(application);
        postDao = localdb.postImages();
    }

    public static PostDetailsRepository getInstance(Application application) {
        if (repo_instance == null) {
            synchronized (lock) {
                repo_instance = new PostDetailsRepository(application);
            }
        }
        return repo_instance;
    }

    public PostImages getPostDetails(String imageId) {
        return localdb.postImages().getPostImageByImageId(imageId);
    }

    public void setImageComment(String comment, String imageId) {

        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                int res= postDao.updatePostImageComment(comment, imageId);
            }
        };
        thread.start();
    }
}
/**
 * Repository module for handling data operations.
 */
