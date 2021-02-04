package com.example.reservjava_app.fragment.d_bongsun;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;

public class MemberCancelFragment extends Fragment {

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        ViewGroup viewGroup = (ViewGroup) inflater.inflate  //viewGroup 정의
                (R.layout.fragment_member_cancel, container, false);

        //activity = (MainActivity) getActivity();

// 상단바 처리 ------------------------------------------------------------------------
        //backBtn = viewGroup.findViewById(R.id.backBtn); 왜 인지 모르겠지만 이렇게 하면 다운된다;;

        // 상단 바() 객체 목록
        //ImageView backBtn, searchBtn;
        //EditText addrSearch;

        //백 버튼 (홈으로 이동) <-- 이전화면 이동 으로 바꾸는게 좋을것 같음.
        viewGroup.findViewById(R.id.backQnABtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Home화면(fragment_home.xml)을 activity_main.container 에 띄운다.
                activity.onFragmentChange(1);
            }
        });

        //검색버튼 (Search로 이동)
        viewGroup.findViewById(R.id.searchBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //List화면(fragment_search.xml)을 activity_main.container 에 띄운다.
                activity.onFragmentChange(2);
            }
        });

// 회원탈퇴 화면 처리 ------------------------------------------------------------------------
        // 회원탈퇴 화면(fragment_member_cancel.xml) 객체 목록

        //TextView title;
        TextView textView1, textView2, textView3, textView4;
        Button submitBtn;

        // 회원탈퇴 버튼이 눌려지면,
        viewGroup.findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "회원이 탈퇴 하였습니다.", Toast.LENGTH_SHORT).show();
                /* 회원탈퇴 처리 - 시작 */

                /* 회원탈퇴  처리 - 끝 */

                activity.onFragmentChange(1);   //처리후 화면 전환
            }
        });

        return viewGroup;
    }

}