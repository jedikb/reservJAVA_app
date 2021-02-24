package com.example.reservjava_app.ui.d_bongsun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.reservjava_app.ATask.MemberCancel;
import com.example.reservjava_app.ListActivity;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.a_login_signup.QnAMainActivity;
import com.example.reservjava_app.ui.b_where.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MemberCancelActivity extends AppCompatActivity {

    //HomeFragment homeFragment;    //MainActivity 이동 방식으로 변경
    ListFragment listFragment;
    QnAFragment qnAFragment;
    Toolbar toolbar;
    ImageView backQnABtn3;

    String state;
    String member_code;

    private static String TAG = "main:MemberCancelActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_cancel);

        backQnABtn3 = findViewById(R.id.backQnABtn3);
        backQnABtn3.setOnClickListener(new View.OnClickListener() {
            //  백버튼 누르면 QnA메인 화면으로 돌아감
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MemberCancelActivity.this,QnAMainActivity.class);
                startActivity(intent);
            }
        });

        EditText addSearch = (EditText) findViewById(R.id.addrSearch);
        addSearch.setText("activity_member_cancel.xml");

        //1. 액티비티 화면이 A, B, C 를 만들어야 한다면
        //  액티비티 화면을 이름만 주어서 만든다.

        toolbar = findViewById(R.id.backJoinBtn);
        setSupportActionBar(toolbar);   //상단액션바를 툴바로 교체

        //하단바 처리
        //homeFragment = new HomeFragment();
        listFragment = new ListFragment();
        qnAFragment = new QnAFragment();

        //getSupportFragmentManager().beginTransaction()
        //        .replace(R.id.container, homeFragment).commit();    //기본 첫화면 띄우기
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //하단바 아이템 클릭 이벤트 처리
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent; //액티비티 콜을 위한 지역변수 선언
                switch (item.getItemId()){
                    case R.id.homeItem:
                        intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.searchItem:
                        intent = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(intent);
                        return true;

                    case R.id.listItem:
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.container, listFragment).commit();
                        return true;

                }//switch
                return false;
            }//onNavigationItemSelected()
        });

        // id = ((EditText) findViewById(R.id.addrSearch)).getText().toString();
        member_code = "200";//임시 테스트용

        //회원탈퇴처리
        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                memberCancel();
            }//onClick()
        });//submitBtn.setOnClickListener()

    }//onCreat()

    // 프래그먼트 이동 메소드
    public void onFragmentChange(int state){
        Intent intent; //액티비티 콜을 위한 지역변수 선언
        if (state == 1) {
            intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        } else if (state == 2) {
            intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        } else if (state == 3) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, listFragment).commit();
        } else if (state == 4) {    //테스트 페이지(임시) state = 4
            intent = new Intent(getApplicationContext(), ListActivity.class);
            startActivity(intent);
        } else if (state == 7) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, qnAFragment).commit();
        }
    }

    private void memberCancel(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("정말 탈퇴 하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "예. 버튼이 눌렸습니다!";
                Log.d(TAG, "showMessage().onClick: " + message);

                MemberCancel memberCancel = new MemberCancel(member_code);
                try {
                    state = memberCancel.execute().get();
                    Log.d(TAG, "submitBtn:onClick: memberCancel.execute().get() 실행함.");
                } catch (Exception e) {
                    e.printStackTrace();
                }//try//catch

                if(state.equals("1")){
                    Log.d(TAG, "submitBtn:onClick: 회원탈퇴성공 !!!");

                    showAlert("회원 탈퇴 되었습니다.");
                    //finish();
                }else{
                    Log.d(TAG, "submitBtn:onClick: 회원탈퇴실패 !!!");

                    showAlert("회원 탈퇴처리가 완료되지 않았습니다.");
                    //finish();
                }
            }
        });//builder.setNegativeButton()

        builder.setPositiveButton("아니오", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "아니오. 버튼이 눌렸습니다!";
                Log.d(TAG, "showMessage().onClick: " + message);
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });//builder.setPositiveButton()

        AlertDialog dialog = builder.create();
        dialog.show();

    }//memberCancel()

    private void showAlert(String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setTitle("확인");
        builder.setMessage( msg );
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null){
                    dialog.dismiss();
                }
                finish();
            }
        });//builder.setPositiveButton()

        AlertDialog dialog = builder.create();
        dialog.show();
    }//showAlert()

}//class MemberCancelActivity