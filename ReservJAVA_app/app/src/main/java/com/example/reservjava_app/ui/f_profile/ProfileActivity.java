package com.example.reservjava_app.ui.f_profile;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.reservjava_app.DTO.ReviewDTO;
import com.example.reservjava_app.R;
import com.google.android.material.tabs.TabLayout;

public class ProfileActivity extends AppCompatActivity {
  private static final String TAG = "main:ProfileActivity";
  public static ReviewDTO reviewSetItem = null;
  TabLayout tabs;
  Fragment selected = null;
  Profile_Myinfo_flag myinfo_flag;
  Profile_MyReview_flag myReview_flag;
  Profile_MyVisit_flag myVisit_flag;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_profile);
    tabs = findViewById(R.id.tabs);
    tabs.addTab(tabs.newTab().setText("내정보"));
    tabs.addTab(tabs.newTab().setText("내 리뷰"));
    tabs.addTab(tabs.newTab().setText("방문 매장 보기"));
    myinfo_flag = new Profile_Myinfo_flag();
    myReview_flag = new Profile_MyReview_flag();
    myVisit_flag = new Profile_MyVisit_flag();

    getSupportFragmentManager().beginTransaction()
            .replace(R.id.container, myinfo_flag).commit();
    tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
      @Override
      public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        Log.d(TAG, "선택된 탭 => " + position);

        if(position == 0){
          selected = myinfo_flag;
        }else if(position == 1){
          selected = myReview_flag;
        }else if(position == 2){
          selected = myVisit_flag;
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, selected).commit();
      }

      @Override
      public void onTabUnselected(TabLayout.Tab tab) {

      }

      @Override
      public void onTabReselected(TabLayout.Tab tab) {

      }
    });
    // 이름
   /* pro_tv_name = findViewById(R.id.pro_tv_name);
    pro_tv_name.setText("소중한 " + loginDTO.getMember_name() + "님");

    // 사진
    //만약 사진 필드가 비어 있다면... 디폴트 이미지
    faceImg = findViewById(R.id.faceImg);
    member_image = loginDTO.getMember_image();

    faceImg.setVisibility(View.VISIBLE);
    //선택된 이미지 보여주기(움직이는 그림도 됨)
    Glide.with(this).load(member_image)
        .error(R.drawable.user).into(faceImg);

    //리사클러 뷰 시작
    rAdapter = new MyReviewAdapter(this, reviewDTOS);
    vAdapter = new MyVisitAdapter(this, reviewDTOS);
    recyclerView = findViewById(R.id.recyclerView);

    LinearLayoutManager layoutManager = new LinearLayoutManager(this,
        RecyclerView.VERTICAL, false);
    recyclerView.setLayoutManager(layoutManager);

    recyclerView.setAdapter(vAdapter);




    profile_user_btn = findViewById(R.id.profile_user_btn) ;
    profile_detail_btn = findViewById(R.id.profile_detail_btn) ;
    profile_user_btn.setTag("N");
    profile_detail_btn.setTag("N");

    img1 =this.getResources().getDrawable( R.drawable.btn_plus );
    img2 =this.getResources().getDrawable( R.drawable.btn_plust );
    profile_user_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        if (profile_user_btn.getTag().toString().equals("N")){
          profile_user_btn.setTag("Y");
          Animation animation = new AlphaAnimation(0, 1);
          animation.setDuration(1000);
          profile_user_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img2, null);
          profile_detail_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);
          findViewById(R.id.profile_ln1).setVisibility(View.VISIBLE);
          findViewById(R.id.profile_ln2).setVisibility(View.GONE);
        }else{
          profile_user_btn.setTag("N");
          profile_user_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);
          findViewById(R.id.profile_ln1).setVisibility(View.GONE);
        }

      }
    });

    profile_detail_btn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        findViewById(R.id.profile_ln2).setVisibility(View.VISIBLE);

        if (profile_detail_btn.getTag().toString().equals("N")){
          profile_detail_btn.setTag("Y");
          Animation animation = new AlphaAnimation(0, 1);
          animation.setDuration(1000);
          profile_detail_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img2, null);
          profile_user_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);
          findViewById(R.id.profile_ln1).setVisibility(View.GONE);
          findViewById(R.id.profile_ln2).setVisibility(View.VISIBLE);
        }else{
          profile_detail_btn.setTag("N");
          profile_detail_btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);
          findViewById(R.id.profile_ln2).setVisibility(View.GONE);
        }
      }
    });

    //방문한! 리스트 보여줌
    findViewById(R.id.visitListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        recyclerView.setAdapter(vAdapter);
      }
    });

    //내가 작성한 리뷰들을 보여줌
    findViewById(R.id.reviewListBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        recyclerView.setAdapter(rAdapter);
      }
    });

    //프로필 수정 화면으로 이동
    findViewById(R.id.editProfileBtn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        //수정할 때는 아이디만 가지고 새로 불러오게 함
        Intent intent = new Intent(getApplicationContext(), ModProfileActivity.class);
        startActivity(intent);
        finish();
      }
    });

    //뒤로가기 버튼(메인화면으로 돌아감)
    findViewById(R.id.tv_back_profile).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        finish();
      }
    });*/

  }
}