package com.yogendra.imgurmediamvvm.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.yogendra.imgurmediamvvm.db.AppDatabase;
import com.yogendra.imgurmediamvvm.db.PostImagesDao;
import com.yogendra.imgurmediamvvm.model.PostImages;

public class DbDetailsRepository {

    private MediatorLiveData<PostImages> mLoadDataLive = new MediatorLiveData<>();

    private PostImagesDao mDao;
    private static MutableLiveData<Boolean> updateSuccess = new MutableLiveData();

    public DbDetailsRepository(Application application, String imageid) {
        AppDatabase db = AppDatabase.getDB.create(application);
        mDao = db.postImages();
    }

//    public LiveData<PostImages> loadData(String imageID) {
//      return mDao.getPostImageByImageId(imageID);
//    }

    public LiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
    }

    public void update(PostImages data) {
        new updatecommentAsyncTask(mDao).execute(data);
    }


    /**
     * Updates a word in the database.
     */
    private static class updatecommentAsyncTask extends AsyncTask<PostImages, Void, Void> {
        private PostImagesDao mAsyncTaskDao;

        updatecommentAsyncTask(PostImagesDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final PostImages... params) {
            if (mAsyncTaskDao.update(params[0]) > 0)
                updateSuccess.postValue(true);
            else
                updateSuccess.postValue(false);
            return null;
        }
    }
}
