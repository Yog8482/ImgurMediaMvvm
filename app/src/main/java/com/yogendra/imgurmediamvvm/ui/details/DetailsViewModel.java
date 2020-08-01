package com.yogendra.imgurmediamvvm.ui.details;

import android.app.Activity;
import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yogendra.imgurmediamvvm.db.AppDatabase;
import com.yogendra.imgurmediamvvm.db.PostImagesDao;
import com.yogendra.imgurmediamvvm.model.PostImages;
import com.yogendra.imgurmediamvvm.repository.PostDetailsRepository;

import java.lang.ref.WeakReference;

public class DetailsViewModel extends AndroidViewModel {
    private PostDetailsRepository repository;
    private String selectedImageId;
    private MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>();

    private MutableLiveData<PostImages> imageDetails = new MutableLiveData<>();

    public DetailsViewModel(@NonNull Application application, String imageId, PostDetailsRepository postDetailsRepository) {
        super(application);
        this.repository = postDetailsRepository;
        this.selectedImageId = imageId;
//        PostImages details = repository.getPostDetails(selectedImageId);
//        imageDetails.postValue(details);

        new AgentAsyncTask(repository).execute();

    }

    void updateComment(String comment) {

        repository.setImageComment(comment, selectedImageId);
        updateSuccess.postValue(true);

//        if (repository.setImageComment(comment, selectedImageId) > 0) {
//            updateSuccess.postValue(true);
//        } else {
//            updateSuccess.postValue(false);
//        }
    }

    MutableLiveData<Boolean> getUpdateSuccess() {
        return updateSuccess;
    }

    MutableLiveData<PostImages> getImageDetails() {
        return imageDetails;
    }

    private class AgentAsyncTask extends AsyncTask<Void, Void, Integer> {

        //Prevent leak
        private WeakReference<Fragment> weakFrgament;
        private PostDetailsRepository repo;


        public AgentAsyncTask(PostDetailsRepository repository) {
//            weakFrgament = new WeakReference<>(activity);
            this.repo = repository;

        }

        @Override
        protected Integer doInBackground(Void... params) {
            PostImages details = repo.getPostDetails(selectedImageId);
            imageDetails.postValue(details);
            return 1;
        }

        @Override
        protected void onPostExecute(Integer agentsCount) {
//            Fragment fragment = weakFrgament.get();
//            if(fragment == null) {
//                return;
//            }

//            if (agentsCount > 0) {
//               updateSuccess.postValue(true);
//            } else {
//                Toast.makeText(activity, "Agent does not exist! Hurray :)", Toast.LENGTH_LONG).show();
//                activity.onBackPressed();
//            }
        }
    }
}