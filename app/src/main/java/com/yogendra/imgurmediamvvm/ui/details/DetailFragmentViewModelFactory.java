package com.yogendra.imgurmediamvvm.ui.details;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yogendra.imgurmediamvvm.db.AppDatabase;
import com.yogendra.imgurmediamvvm.repository.PostDetailsRepository;


public class DetailFragmentViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String imageId;

    DetailFragmentViewModelFactory(Application application, String imageId) {
        this.mApplication = application;
        this.imageId = imageId;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetailsViewModel.class)) {
            PostDetailsRepository repository = PostDetailsRepository.getInstance(mApplication);
            return (T) new DetailsViewModel(mApplication, imageId, repository);
        }
        throw new IllegalArgumentException("Cannot create Instance for this class");
    }
}
