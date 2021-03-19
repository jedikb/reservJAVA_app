package com.example.reservjava_app.ui.f_profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.MyReviewAdapter;
import com.example.reservjava_app.adapter.MyVisitAdapter;

import java.util.ArrayList;

import static com.example.reservjava_app.ATask.MyReview.reviewDTOS;

public class Profile_MyVisit_flag extends Fragment {

    MyReviewAdapter rAdapter;
    MyVisitAdapter vAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater
                .inflate(R.layout.profile__my_visit_flag, container, false);

        ArrayList<ReviewDTO> RevList = new ArrayList<>();
        // 이런 거 없애야 함.. 매번 입력하면 시간이 느려짐
        for (ReviewDTO dto : reviewDTOS) {
            if(dto.getBooking_kind() == 4){
                RevList.add(dto);
            }
        }
        vAdapter = new MyVisitAdapter(viewGroup.getContext(),RevList,this);
        recyclerView = viewGroup.findViewById(R.id.visit_recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(viewGroup.getContext(),
                RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(vAdapter);

        return viewGroup;
    }

    public void Refresh() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

}