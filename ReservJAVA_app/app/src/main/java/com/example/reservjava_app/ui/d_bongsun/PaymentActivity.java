package com.example.reservjava_app.ui.d_bongsun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.reservjava_app.ATask.BookingView;
import com.example.reservjava_app.ATask.Payment;
import com.example.reservjava_app.DTO.BookingDTO;
import com.example.reservjava_app.ListActivity;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.fragment.ListFragment;
import com.example.reservjava_app.fragment.d_bongsun.QnAFragment;
import com.example.reservjava_app.ui.a_login_signup.JoinActivity;
import com.example.reservjava_app.ui.a_login_signup.LoginActivity;
import com.example.reservjava_app.ui.a_login_signup.QnAMainActivity;
import com.example.reservjava_app.ui.b_where.SearchActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import static com.example.reservjava_app.ui.a_login_signup.LoginActivity.loginDTO;

public class PaymentActivity extends AppCompatActivity {

    //HomeFragment homeFragment;    //MainActivity 이동 방식으로 변경
    ListFragment listFragment;
    QnAFragment qnAFragment;
    Toolbar toolbar;

    String state;
    String member_code;
    String booking_code;

    public static BookingDTO bookingDTO = null;

    private static String TAG = "main:PaymentActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        EditText addSearch = (EditText) findViewById(R.id.addrSearch);
        addSearch.setText("activity_payment.xml");

        //1. 액티비티 화면이 A, B, C 를 만들어야 한다면
        //  액티비티 화면을 이름만 주어서 만든다.

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);   //상단액션바를 툴바로 교체

        //햄버거, 액션바 내용 변경
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);   //햄버거 아이콘 변경
        //getSupportActionBar().setTitle("주소");   //상단액션바(default: app_name @res.values.strings.xml)

        //햄버거 버튼과 Navigation Drawer( 바로가기 메뉴)연결
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Navigation Drawer(바로가기 메뉴) 아이템 클릭 이벤트 처리
        NavigationView navigationView = findViewById(R.id.loginnavigation);
        if(loginDTO == null) {  //로그인 안했을 때
            navigationView.getMenu().findItem(R.id.nav_membershipbtn)
                    .setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_logout)
                    .setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_listchk)
                    .setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_loginbtn)
                    .setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_signupbtn)
                    .setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_qna)
                    .setVisible(true);
        } else {  //로그인 했을 때
            navigationView.getMenu().findItem(R.id.nav_membershipbtn)
                    .setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_logout)
                    .setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_listchk)
                    .setVisible(true);
            navigationView.getMenu().findItem(R.id.nav_loginbtn)
                    .setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_signupbtn)
                    .setVisible(false);
            navigationView.getMenu().findItem(R.id.nav_qna)
                    .setVisible(true);
        }

        //Navigation Drawer(바로가기 메뉴) 아이템 클릭 이벤트 처리 내용
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                //햄버거바 메뉴 누르면 이동
                if(id == R.id.nav_loginbtn){
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                }else if(id == R.id.nav_signupbtn){
                    Intent intent = new Intent(getApplicationContext(), JoinActivity.class);
                    startActivity(intent);
                }else if(id == R.id.nav_qna){
                    Intent intent = new Intent(getApplicationContext(), QnAMainActivity.class);
                    startActivity(intent);
                }
                return false;
            }



        });

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
        member_code = "101";//임시 테스트용
        booking_code = "177";//임시 테스트용

        //예약정보 화면출력
        BookingView bookingView = new BookingView(booking_code);
        try {
            //showBooking( bookingView.execute().get() );
            bookingDTO = bookingView.execute().get();
            Log.d(TAG, "readMessage: " + " : " + bookingDTO.getBooking_code() + " : " + bookingDTO.getBooking_kind() + " : " + bookingDTO.getBooking_member_code() + " : " + bookingDTO.getBooking_business_code() + " : " + bookingDTO.getBooking_product_code() + " : " + bookingDTO.getBooking_price() + " : " + bookingDTO.getBooking_price_deposit() + " : " + bookingDTO.getBooking_num() + " : " + bookingDTO.getBooking_date() + " : " + bookingDTO.getBooking_date_reservation() + " : " + bookingDTO.getBooking_etc() + " : " + bookingDTO.getBooking_appraisal_star() + " : " + bookingDTO.getBooking_appraisal());
            Log.d(TAG, "onCreate: bookingView.execute().get() 실행함.");

            showBooking(bookingDTO);
/*
            TextView business_name = (TextView) findViewById(R.id.business_name);
            business_name.setText( String.valueOf(bookingDTO.getBooking_business_code()) );
*/
        } catch (Exception e) {
            e.printStackTrace();
        }//try//catch

        //결재처리
        findViewById(R.id.submitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payment();
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

    private void showBooking(BookingDTO dto){
        ((TextView) findViewById(R.id.product_name))
                .setText( String.valueOf(dto.getBooking_product_name()) );
        ((TextView) findViewById(R.id.booking_date_reservation))
                .setText( String.valueOf(dto.getBooking_date_reservation()) );
        ((TextView) findViewById(R.id.booking_price))
                .setText( String.valueOf(dto.getBooking_price()) );
        ((TextView) findViewById(R.id.booking_price_total))
                .setText( String.valueOf(dto.getBooking_num()) );
        ((TextView) findViewById(R.id.business_name))
                .setText( String.valueOf(dto.getBooking_business_name()) );
        ((TextView) findViewById(R.id.booking_date))
                .setText( String.valueOf(dto.getBooking_date()) );
        ((TextView) findViewById(R.id.booking_etc))
                .setText( String.valueOf(dto.getBooking_etc()) );
    }

    private void payment(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("안내");
        builder.setMessage("결재 하시겠습니까?");
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setNegativeButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String message = "예. 버튼이 눌렸습니다!";
                Log.d(TAG, "showMessage().onClick: " + message);

                Payment payment = new Payment(booking_code);
                try {
                    state = payment.execute().get();
                    Log.d(TAG, "submitBtn:onClick: payment.execute().get() 실행함.");
                } catch (Exception e) {
                    e.printStackTrace();
                }//try//catch

                if(state.equals("1")){
                    Log.d(TAG, "submitBtn:onClick: 결재성공 !!!");

                    showAlert("결재 되었습니다.");
                    //finish();
                }else{
                    Log.d(TAG, "submitBtn:onClick: 결재실패 !!!");

                    showAlert("결재가 완료되지 않았습니다.");
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

}//class PaymentActivity