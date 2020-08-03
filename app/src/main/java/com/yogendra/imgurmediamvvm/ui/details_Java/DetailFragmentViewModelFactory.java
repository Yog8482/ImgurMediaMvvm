package com.yogendra.imgurmediamvvm.ui.details_Java;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.yogendra.imgurmediamvvm.ServiceLocator;
import com.yogendra.imgurmediamvvm.repository.DbDetailsRepository;
import com.yogendra.imgurmediamvvm.repository.DbDetailsRepository_Kotlin;
import com.yogendra.imgurmediamvvm.ui.details_kotlin.DetailsKotlinViewModel;


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

            DbDetailsRepository_Kotlin repository= ServiceLocator.Companion.instance(mApplication).getdetailsRepository();

//            PostDetailsRepository repository = PostDetailsRepository.getInstance(mApplication,imageId);
            return (T) new DetailsKotlinViewModel(imageId, repository);
        }
        throw new IllegalArgumentException("Cannot create Instance for this class");
    }
}
