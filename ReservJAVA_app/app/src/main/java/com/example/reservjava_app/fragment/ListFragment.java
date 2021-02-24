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
import com.example.reservjava_app.ui.d_bongsun.BookingViewActivity;
import com.example.reservjava_app.ui.d_bongsun.MemberCancelActivity;
import com.example.reservjava_app.ui.d_bongsun.PaymentActivity;

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

/*
        //뒤로 가기 버튼
        viewGroup.findViewById(R.id.backBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(3);
            }
        });
*/


        return viewGroup;



    }
}