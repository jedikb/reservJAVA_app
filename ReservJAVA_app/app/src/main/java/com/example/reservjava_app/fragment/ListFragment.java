package com.example.reservjava_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;

public class ListFragment extends Fragment {

    MainActivity activity;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
            .inflate(R.layout.fragment_list, container, false);

        //뒤로 가기 버튼
        viewGroup.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(3);
            }
        });

        //로그인 이동(임시)
        viewGroup.findViewById(R.id.tologinBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity.getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        //예약 이동(임시)
        viewGroup.findViewById(R.id.moveBtn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(4);
            }
        });

        //회원탈퇴 이동(임시)
        viewGroup.findViewById(R.id.moveBtn3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(5);
            }
        });

        //결제 이동(임시)
        viewGroup.findViewById(R.id.moveBtn4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(6);
            }
        });

        //문의 이동(임시)
        viewGroup.findViewById(R.id.moveBtn5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(7);
            }
        });

        return viewGroup;



    }
}