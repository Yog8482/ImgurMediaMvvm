package com.yogendra.imgurmediamvvm.ui.details;


import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.yogendra.imgurmediamvvm.R;
import com.yogendra.imgurmediamvvm.databinding.DetailsFragmentBinding;
import com.yogendra.imgurmediamvvm.model.PostImages;
import com.yogendra.imgurmediamvvm.utils.custom.MultilineSnackbar;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    private DetailsViewModel mViewModel;
    Context context;

    private String imageID, title;
    private DetailsFragmentBinding binding = null;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        context = getActivity();
        binding = DetailsFragmentBinding.inflate(inflater, container, false);

        binding.submitButton.setOnClickListener(this::onClick);

        Application application = requireActivity().getApplication();

        title = DetailsFragmentArgs.fromBundle(getArguments()).getTitle();
        imageID = DetailsFragmentArgs.fromBundle(getArguments()).getImageId();

        DetailFragmentViewModelFactory factory = new DetailFragmentViewModelFactory(application, imageID);
        mViewModel = new ViewModelProvider(this, factory).get(DetailsViewModel.class);


        mViewModel.getImageDetails().observe(getViewLifecycleOwner(), new imageDetailsObserver());
        mViewModel.getUpdateSuccess().observe(getViewLifecycleOwner(), new updateSuccessObserver());

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public void clearFocus() {
        binding.editTextMultiLine.clearFocus();
        binding.submitButton.requestFocus();
    }

    @Override
    public void onClick(View v) {
        String comment = binding.editTextMultiLine.getText().toString();
        mViewModel.updateComment(comment);
        clearFocus();
    }


    private class updateSuccessObserver implements Observer<Boolean> {
        @Override
        public void onChanged(@Nullable Boolean isupdated) {
            String message;
            if (isupdated == null) {
                return;
            }
            if (isupdated)
                message = context.getResources().getString(R.string.update_success);
            else
                message = context.getResources().getString(R.string.update_failed);

            new MultilineSnackbar(binding.getRoot(), message).show();

        }
    }


    private class imageDetailsObserver implements Observer<PostImages> {
        @Override
        public void onChanged(PostImages imageDetails) {
            if (imageDetails == null || imageDetails.getLink().isEmpty()) {
                new MultilineSnackbar(binding.getRoot(), context.getResources().getString(R.string.no_details)).show();
                return;
            }

            binding.setData(imageDetails);

        }
    }
}