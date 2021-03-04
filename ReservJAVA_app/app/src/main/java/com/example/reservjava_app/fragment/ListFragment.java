package com.example.reservjava_app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.Common.CommonMethod;
import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.ReserveListAdapter;

import java.util.ArrayList;

import static com.example.reservjava_app.ATask.MyReview.reviewDTOS;
import static com.example.reservjava_app.Common.CommonMethod.currentAddress;

public class ListFragment extends Fragment {

    MainActivity activity;
    RecyclerView recyclerView;
    ReserveListAdapter vAdapter;
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
        CommonMethod.selectData(activity);
        viewGroup.findViewById(R.id.btn_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommonMethod.selectData(activity);
                vAdapter.notifyDataSetChanged();

            }
        });
        ArrayList<ReviewDTO> RevList = new ArrayList<>();
        for (ReviewDTO dto: reviewDTOS ) {
            if(dto.getBooking_kind() == 2){
                RevList.add(dto);
            }
        }
        vAdapter = new ReserveListAdapter(viewGroup.getContext(), RevList);
        recyclerView = viewGroup.findViewById(R.id.rc_frg_list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewGroup.getContext(),
                RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(vAdapter);
        return viewGroup;



    }
}