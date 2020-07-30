package com.yogendra.imgurmediamvvm.ui.details;

import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavArgs;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.yogendra.imgurmediamvvm.R;

public class DetailsFragment extends Fragment implements View.OnClickListener {

    private DetailsViewModel mViewModel;

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    EditText editTextTextMultiLine;
    Button submitbtn;
    Context context;

    private String imageID = null, title = "Details";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        context = getActivity();
        View view = inflater.inflate(R.layout.details_fragment, container, false);
        editTextTextMultiLine = view.findViewById(R.id.editTextTextMultiLine);
        submitbtn = view.findViewById(R.id.submit_button);
        submitbtn.setOnClickListener(this::onClick);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DetailsViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        title = DetailsFragmentArgs.fromBundle(getArguments()).getTitle();
        imageID = DetailsFragmentArgs.fromBundle(getArguments()).getImageId();

    }

    public void clearFocus() {
        editTextTextMultiLine.clearFocus();
        submitbtn.requestFocus();
    }

    @Override
    public void onClick(View v) {
        clearFocus();
    }
}