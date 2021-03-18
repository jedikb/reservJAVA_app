package com.example.reservjava_app.ui.f_profile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

import static com.example.reservjava_app.Common.CommonMethod.loginDTO;


public class Profile_Myinfo_flag extends Fragment {
    ViewGroup viewGroup_b;
    TextView pro_tv_name;
    ImageView faceImg;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.profile__myinfo, container, false);
        viewGroup_b = viewGroup;
        //activity = (ProfileActivity) getActivity();
        faceImg = viewGroup.findViewById(R.id.faceImg);
        Glide.with(this).load(loginDTO.getMember_image()).error(R.drawable.user).into(faceImg);
        pro_tv_name = viewGroup.findViewById(R.id.pro_tv_name);
        pro_tv_name.setText("소중한 " + loginDTO.getMember_nick()  + "님");

        viewGroup.findViewById(R.id.editProfileBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(viewGroup_b.getContext(), ModProfileActivity.class);
                startActivity(intent);
            }
        });
        return viewGroup;
    }

    @Override
    public void onResume() {
        super.onResume();
        Glide.with(this).load(loginDTO.getMember_image()).error(R.drawable.user).into(faceImg);
        pro_tv_name.setText("소중한 " + loginDTO.getMember_nick()  + "님");
    }
}