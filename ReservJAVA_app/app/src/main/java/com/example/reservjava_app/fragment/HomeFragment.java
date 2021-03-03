package com.example.reservjava_app.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.ImageSliderAdapter;
import com.example.reservjava_app.ui.b_where.WhereListActivity;

import static com.example.reservjava_app.Common.CommonMethod.busiList;

import java.util.ArrayList;

import com.readystatesoftware.viewbadger.BadgeView;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ArrayList<BusinessDTO> searchBusiList;
    int[] count_list = new int[12];
    BadgeView[] badge = new BadgeView[count_list.length];
    ImageView imageButton1, imageButton2, imageButton3, imageButton4,
            imageButton5, imageButton6, imageButton7, imageButton8,
            imageButton9, imageButton10, imageButton11, imageButton12;
    Context context ;
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private int[] images = new int[] {
            R.drawable.banner_img1,
            R.drawable.banner_img2,
            R.drawable.banner_img3,
            R.drawable.banner_img4
    };



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        context = rootView.getContext();

        imageButton1 = rootView.findViewById(R.id.imageButton1);
        imageButton2 = rootView.findViewById(R.id.imageButton2);
        imageButton3 = rootView.findViewById(R.id.imageButton3);
        imageButton4 = rootView.findViewById(R.id.imageButton4);
        imageButton5 = rootView.findViewById(R.id.imageButton5);
        imageButton6 = rootView.findViewById(R.id.imageButton6);
        imageButton7 = rootView.findViewById(R.id.imageButton7);
        imageButton8 = rootView.findViewById(R.id.imageButton8);
        imageButton9 = rootView.findViewById(R.id.imageButton9);
        imageButton10 = rootView.findViewById(R.id.imageButton10);
        imageButton11= rootView.findViewById(R.id.imageButton11);
        imageButton12 = rootView.findViewById(R.id.imageButton12);
        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
        imageButton3.setOnClickListener(this);
        imageButton4.setOnClickListener(this);
        imageButton5.setOnClickListener(this);
        imageButton6.setOnClickListener(this);
        imageButton7.setOnClickListener(this);
        imageButton8.setOnClickListener(this);
        imageButton9.setOnClickListener(this);
        imageButton10.setOnClickListener(this);
        imageButton11.setOnClickListener(this);
        imageButton12.setOnClickListener(this);

        SetImageButton();

        sliderViewPager = rootView.findViewById(R.id.sliderViewPager);
        layoutIndicator = rootView.findViewById(R.id.layoutIndicators);
        sliderViewPager.setOffscreenPageLimit(1);
        sliderViewPager.setAdapter(new ImageSliderAdapter(context, images));

        sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
            }
        });

        setupIndicators(images.length);

        //수지 인스타로 넘어가는 부분
        return rootView;
    }

    private void setupIndicators(int count ) {
        ImageView[] indicators = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        params.setMargins(16, 8, 16, 8);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(context);
            indicators[i].setImageDrawable(ContextCompat.getDrawable(context,
                    R.drawable.bg_indicator_inactive));
            indicators[i].setLayoutParams(params);
            layoutIndicator.addView(indicators[i]);
        }
        setCurrentIndicator(0 );
    }

    private void setCurrentIndicator(int position) {
        int childCount = layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        context,
                        R.drawable.bg_indicator_inactive
                ));
            }
        }
    }

    @Override
    public void onClick(View v) {
        searchBusiList = new ArrayList<>();
        Intent intent = new Intent(getActivity(), WhereListActivity.class);
        switch (v.getId()) {
            case R.id.imageButton1:
                EqulsBusList("병원");
                break;
            case R.id.imageButton2:
                EqulsBusList("음식점");
                break;
            case R.id.imageButton3:
                EqulsBusList("숙박");
                break;
            case R.id.imageButton4:
                EqulsBusList("미용");
                break;
            case R.id.imageButton5:
                EqulsBusList("카센터");
                break;
            case R.id.imageButton6:
                EqulsBusList("놀이공원");
                break;
            case R.id.imageButton7:
                EqulsBusList("박물관");
                break;
            case R.id.imageButton8:
                EqulsBusList("항공권");
                break;



        }
        if (searchBusiList.size() < 1){
            Toast.makeText(this.getContext(), "상품 준비중 입니다.", Toast.LENGTH_LONG).show();
        }else{
        intent.putExtra("searchBusiList", searchBusiList);
        startActivity(intent);
        }
    }


    public void SetImageButton() {
        badge[0] = new BadgeView(this.getContext(), imageButton1);
        badge[1] = new BadgeView(this.getContext(), imageButton2);
        badge[2] = new BadgeView(this.getContext(), imageButton3);
        badge[3] = new BadgeView(this.getContext(), imageButton4);
        badge[4] = new BadgeView(this.getContext(), imageButton5);
        badge[5] = new BadgeView(this.getContext(), imageButton6);
        badge[6] = new BadgeView(this.getContext(), imageButton7);
        badge[7] = new BadgeView(this.getContext(), imageButton8);
        badge[8] = new BadgeView(this.getContext(), imageButton9);
        badge[9] = new BadgeView(this.getContext(), imageButton10);
        badge[10] = new BadgeView(this.getContext(), imageButton11);
        badge[11] = new BadgeView(this.getContext(), imageButton12);
        for (BusinessDTO dto : busiList) {
            if (dto.getBusiness_hashtag().indexOf("병원") > -1) {
                count_list[0]++;
            } else if (dto.getBusiness_hashtag().indexOf("음식점") > -1) {
                count_list[1]++;
            } else if (dto.getBusiness_hashtag().indexOf("숙박") > -1) {
                count_list[2]++;
            } else if (dto.getBusiness_hashtag().indexOf("미용") > -1) {
                count_list[3]++;
            } else if (dto.getBusiness_hashtag().indexOf("카센터") > -1) {
                count_list[4]++;
            } else if (dto.getBusiness_hashtag().indexOf("놀이공원") > -1) {
                count_list[5]++;
            } else if (dto.getBusiness_hashtag().indexOf("박물관") > -1) {
                count_list[6]++;
            } else if (dto.getBusiness_hashtag().indexOf("항공권") > -1) {
                count_list[7]++;
            }else if (dto.getBusiness_hashtag().indexOf("카페") > -1) {
                count_list[8]++;
            }else if (dto.getBusiness_hashtag().indexOf("노래방") > -1) {
                count_list[9]++;
            }else if (dto.getBusiness_hashtag().indexOf("당구") > -1) {
                count_list[10]++;
            }else if (dto.getBusiness_hashtag().indexOf("PC") > -1) {
                count_list[11]++;
            }
        }
        for (int i = 0; i < count_list.length; i++) {
            if (count_list[i] < 1) {
                badge[i].setText("준비중..");
                badge[i].setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                badge[i].setBackgroundColor(Color.parseColor("#80808080"));
            } else {
                badge[i].setText(count_list[i] + "");
            }
            badge[i].show();
        }
    }

    public void EqulsBusList(String category){
        for (BusinessDTO dto : busiList) {
            if (dto.getBusiness_hashtag().indexOf(category) > -1) {
                searchBusiList.add(dto);
            }
        }
    }
}