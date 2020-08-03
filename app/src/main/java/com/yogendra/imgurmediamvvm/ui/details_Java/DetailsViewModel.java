package com.yogendra.imgurmediamvvm.ui.details_Java;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


public class DetailsViewModel extends AndroidViewModel {
    public DetailsViewModel(@NonNull Application application) {
        super(application);
    }
//    private DbDetailsRepository repository;
//    private String selectedImageId;
//
//    private MutableLiveData<PostImages> imageDetails = new MutableLiveData<>();
//
//    public DetailsViewModel(@NonNull Application application, String imageId, DbDetailsRepository postDetailsRepository) {
//        super(application);
//        this.repository = postDetailsRepository;
//        this.selectedImageId = imageId;
//        LiveData<PostImages> details = repository.loadData(selectedImageId);
//        if (details!=null){
//            imageDetails.postValue(details.getValue());
//        }
//
//    }
//
//
//
//    void updateComment(PostImages details) {
//        repository.update(details);
//    }
//
//   LiveData<Boolean> getUpdateSuccess() {
//        return repository.getUpdateSuccess();
//    }
//
//    MutableLiveData<PostImages> getImageDetails() {
//        return imageDetails;
//    }


}