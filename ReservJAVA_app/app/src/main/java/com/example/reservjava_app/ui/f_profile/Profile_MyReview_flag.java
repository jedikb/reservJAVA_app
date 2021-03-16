package com.example.reservjava_app.ui.f_profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.Common.CommonMethod;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.MyReviewAdapter;

import java.util.ArrayList;

import static com.example.reservjava_app.ATask.MyReview.reviewDTOS;

public class Profile_MyReview_flag extends Fragment {
    MyReviewAdapter rAdapter;
    RecyclerView recyclerView;

    ProfileActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (ProfileActivity) getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.profile__my_review_flag, container, false);
       // activity = ProfileActivity.getActivity();
        CommonMethod.selectData(activity);

        rAdapter = new MyReviewAdapter(viewGroup.getContext(), reviewDTOS);
        recyclerView = viewGroup.findViewById(R.id.review_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewGroup.getContext(),
                RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(rAdapter);


        return viewGroup;
    }


}