package com.example.reservjava_app.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.reservjava_app.ATask.CategorySelect;
import com.example.reservjava_app.ATask.SearchBusiness;
import com.example.reservjava_app.DTO.BusinessDTO;
import com.example.reservjava_app.DTO.CategoryDTO;
import com.example.reservjava_app.DTO.CategoryImageDTO;
import com.example.reservjava_app.DTO.Category_SubDTO;
import com.example.reservjava_app.DTO.SampleModel;
import com.example.reservjava_app.DTO.ViewDTO;
import com.example.reservjava_app.Listner.OnGroupClickListener;
import com.example.reservjava_app.Listner.OnTextClickListener;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.ExpandableListAdapter;
import com.example.reservjava_app.adapter.ImageSliderAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.example.reservjava_app.ui.b_where.WhereListActivity;
import com.readystatesoftware.viewbadger.BadgeView;

import static com.example.reservjava_app.Common.CommonMethod.busiList;

public class HomeFragment extends Fragment implements View.OnClickListener {
    ArrayList<BusinessDTO> searchBusiList;
    public ArrayList<CategoryImageDTO> imageDTOS;

    Context context;
    private ViewPager2 sliderViewPager;
    private LinearLayout layoutIndicator;
    private int[] images = new int[]{
            R.drawable.banner_img4,
            R.drawable.banner_img2,
            R.drawable.banner_img3,
            R.drawable.banner_img1
    };
    public int lastClickedPosition = 0;


    private ExpandableListAdapter adpt;
    private ExpandableListView lstView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_home, container, false);
        context = rootView.getContext();

        lstView = rootView.findViewById(R.id.expList);
        lstView.setGroupIndicator(null);
        loadData();

        adpt.setOnClickListener(new OnTextClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context, v.getTag().toString() + "", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), WhereListActivity.class);
                searchBusiList = new ArrayList<>();
                if (v.getTag() == null){
                    Toast.makeText(context, "상품 준비중 입니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                int category_code = Integer.parseInt(  v.getTag()+"");
                EqulsBusList(category_code);
                if (searchBusiList.size() < 1) {
                    Toast.makeText(context, "상품 준비중 입니다.", Toast.LENGTH_LONG).show();
                } else {
                    intent.putExtra("searchBusiList", searchBusiList);
                    startActivity(intent);
                }
            }
        });

        lstView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView expandableListView, View view, int i, long l) {
                Boolean isExpand = (!expandableListView.isGroupExpanded(i));
                expandableListView.collapseGroup(lastClickedPosition);
                if (isExpand) {
                    expandableListView.expandGroup(i);
                }
                setListViewHeightBasedOnChildren(lstView);
                lastClickedPosition = i;
                return true;
            }
        });

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

        return rootView;
    }

    private void setupIndicators(int count) {
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
        setCurrentIndicator(0);
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
      /*  searchBusiList = new ArrayList<>();
        Intent intent = new Intent(getActivity(), WhereListActivity.class);
        EqulsBusList(1);
        if (searchBusiList.size() < 1) {
            Toast.makeText(this.getContext(), "상품 준비중 입니다.", Toast.LENGTH_LONG).show();
        } else {
            intent.putExtra("searchBusiList", searchBusiList);
            startActivity(intent);
        }*/
    }

    public void EqulsBusList(int category) {
        for (BusinessDTO dto : busiList) {
            if (dto.getBusiness_category_code() == category) {
                searchBusiList.add(dto);
            }
        }
    }


    ///Extendlistview Set Data
    private void loadData() {
        setImageDTOS();
        ArrayList<CategoryDTO> categoryList = new ArrayList<>();
        ArrayList<CategoryDTO> categorymain = new ArrayList<>();
        ArrayList<Category_SubDTO> categorysub = new ArrayList<>();
        ProgressDialog progressDialog = null;
        CategorySelect categorySelect = new CategorySelect(categoryList, progressDialog, adpt);
        try {
            int count = 0;
            categorySelect.execute().get();
            for (int i = 0; i < categoryList.size(); i++) {
                if (categoryList.get(i).getCategory_code() == categoryList.get(i).getCategory_parent_code()) {
                    categorymain.add(categoryList.get(i));
                }
            }

            for (int i = 0; i < categorymain.size(); i++) {
                ArrayList<Category_SubDTO> categorysubs = new ArrayList<>();
                for (int j = 0; j < categoryList.size(); j++) {
                    if (categorymain.get(i).getCategory_parent_code() == categoryList.get(j).getCategory_parent_code()
                            && categorymain.get(i).getCategory_parent_code() != categoryList.get(j).getCategory_code()) {
                       if(  categoryList.get(j).getCnt() != 0){
                           String zgzg = "";

                       }
                        Category_SubDTO sub = new Category_SubDTO(
                                categoryList.get(j).getCategory_code(),
                                categoryList.get(j).getCategory_parent_code(),
                                categoryList.get(j).getCategory_name(),
                                categoryList.get(j).getCategory_info(),
                                categoryList.get(j).getCnt()
                        );
                        categorysub.add(sub);
                        categorysubs.add(sub);
                    }
                }
                categorymain.get(i).setItems(categorysubs);

            }


            adpt = new ExpandableListAdapter(getContext(), lstView, categorymain, categorysub, imageDTOS);
            lstView.setAdapter(adpt);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setListViewHeightBasedOnChildren(lstView);

    }

    //메인카테고리용 이미지
    private void setImageDTOS() {
        imageDTOS = new ArrayList<>();
        imageDTOS.add(new CategoryImageDTO(R.drawable.hosp_img, 100, "#E8E2AE"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.hos_subimg, 101 , "#FAF4C0"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.rest_img, 200 , "#B7F0B1"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.rest_sub_img, 201 , "#C9FFC3"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.culture_img, 300  , "#6EE3F7"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.culture_sub_img, 301 , "#80F5FF"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.hotel_img, 400,  "#8F8AFF"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.hotel_sub_img, 401, "#A19CFF"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.exer_img, 500 ,"#4374D9"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.exer_img_sub, 500  , "#6798FD"));
        imageDTOS.add(new CategoryImageDTO(R.drawable.empty_img, 99999 , "#ffffff"));


    }
    ///Extendlistview Height조절용 메소드
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight+50;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }


}