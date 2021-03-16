package com.example.reservjava_app.ui.f_profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.reservjava_app.R;


public class Profile_Myinfo_flag extends Fragment {
    ViewGroup viewGroup_b;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.profile__myinfo, container, false);
        viewGroup_b = viewGroup;
        viewGroup.findViewById(R.id.editProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewGroup_b.getContext(), ModProfileActivity.class);
                startActivity(intent);
            }
        });
        return viewGroup;

    }
}