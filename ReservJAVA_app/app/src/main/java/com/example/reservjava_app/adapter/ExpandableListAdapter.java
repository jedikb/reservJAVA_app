package com.example.reservjava_app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.reservjava_app.DTO.CategoryDTO;
import com.example.reservjava_app.DTO.CategoryImageDTO;
import com.example.reservjava_app.DTO.Category_SubDTO;
import com.example.reservjava_app.Listner.OnGroupClickListener;
import com.example.reservjava_app.Listner.OnTextClickListener;
import com.example.reservjava_app.R;
import com.readystatesoftware.viewbadger.BadgeView;

import java.util.ArrayList;


public class ExpandableListAdapter extends BaseExpandableListAdapter
        implements OnTextClickListener , OnGroupClickListener {

    public Context mContext;
    public ExpandableListView mListView;
    public ArrayList<CategoryDTO> category_main;
    public ArrayList<Category_SubDTO> category_sub;
    static OnTextClickListener listener;
    static OnGroupClickListener glistener;
    public ArrayList<CategoryImageDTO> imageDTOS;

    public ExpandableListAdapter(Context pContext, ExpandableListView pListView, ArrayList<CategoryDTO> category_main
            , ArrayList<Category_SubDTO> category_sub
            , ArrayList<CategoryImageDTO> imageDTOS) {
        this.mContext = pContext;
        this.mListView = pListView;
        this.category_main = category_main;
        this.category_sub = category_sub;
        this.imageDTOS = imageDTOS;
    }

    public void addItem(CategoryDTO groupData, Category_SubDTO item) {
        if (!category_main.contains(groupData)) {
            category_main.add(groupData);
        }
        int ind = category_main.indexOf(groupData);
        ArrayList<Category_SubDTO> lstItems = category_main.get(ind).getItems();
        lstItems.add(item);
        //   category_main.get(ind).setItems(lstItems);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Category_SubDTO> item = (category_main.get(groupPosition)).getItems();
        return item.get(childPosition);
    }


    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    public void setOnClickListener(OnTextClickListener listener) {
        this.listener = listener;
    }
    public void setOnClickListener(OnGroupClickListener glistener) {
        this.glistener = glistener;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View view,
                             ViewGroup parent) {
        Category_SubDTO item = (Category_SubDTO) getChild(groupPosition, childPosition);
       // if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null,false);
       // }

       /* if (childPosition % 2 == 0){
            view.setBackgroundColor(Color.parseColor("#F6F6F6"));
        }else{
            view.setBackgroundColor(Color.parseColor("#F9F9F9"));
        }*/

        TextView txt_category_sub = view.findViewById(R.id.txt_category_sub);
        LinearLayout ln_list_item = view.findViewById(R.id.ln_list_item);
        ImageView imgv_category_sub = view.findViewById(R.id.imgv_category_sub);
        ln_list_item.setTag(item.getCategory_code() + "");
        txt_category_sub.setTag(item.getCategory_code() + "");
        txt_category_sub.setText(item.getCategory_name());


        BadgeView badgeView ;
        Drawable img = null;
        for (int i = 0; i < imageDTOS.size(); i++) {
            if (imageDTOS.get(i).getCategory_code() == (item.getCategory_parent_code()+1)) {
                img = ContextCompat.getDrawable(
                        mContext,
                        imageDTOS.get(i).getCategory_img()
                ) ;
               // img.setBounds( 0, 0, 120, 120 );
                imgv_category_sub.setImageDrawable(img);
               // view.setBackgroundColor(Color.parseColor(imageDTOS.get(i).getBack_color()));
            }
        }
        if (img==null){
                img = ContextCompat.getDrawable(
                        mContext,
                        R.drawable.empty_img
                ) ;
               // img.setBounds( 0, 0, 120, 120 );
        }

        badgeView = new BadgeView(view.getContext(), imgv_category_sub);
        if (item.getCnt() < 1) {
            badgeView.setText("준비중..");
            imgv_category_sub.setImageDrawable(img);
            badgeView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            badgeView.setBackgroundColor(Color.parseColor("#80808080"));
        } else {
            badgeView.setText(item.getCnt()+"");
        }
        badgeView.show();



        ln_list_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onClick(view);
            }
        });
        return view;
    }


    @Override

    public int getChildrenCount(int groupPosition) {
        return category_main.get(groupPosition).getItems().size();
    }


    @Override
    public Object getGroup(int groupPosition) {
        return category_main.get(groupPosition);
    }


    @Override
    public int getGroupCount() {
        return category_main.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isLastChild, View view, ViewGroup parent) {
        CategoryDTO model = (CategoryDTO) getGroup(groupPosition);
      //  if (view == null) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.group_item, null);
      //  }


        TextView txt_category_main = view.findViewById(R.id.txt_category_main);
        ImageView imgv_category = view.findViewById(R.id.imgv_category);
        txt_category_main.setText(model.getCategory_name());


        ////
        BadgeView badgeView ;
        Drawable img = null;


        for (int i = 0; i < imageDTOS.size(); i++) {
            if (imageDTOS.get(i).getCategory_code() == model.getCategory_code()) {
                img = ContextCompat.getDrawable(
                        mContext,
                        imageDTOS.get(i).getCategory_img()
                ) ;
               //view.setBackgroundResource(R.drawable.ripple_effect);
               // view.setBackgroundColor(Color.parseColor(imageDTOS.get(i).getBack_color()));
                //img.setBounds( 50, 50, 50, 50 );

                imgv_category.setImageDrawable(img);
            }
        }

        if (img==null){
            img = ContextCompat.getDrawable(
                    mContext,
                    R.drawable.empty_img
            ) ;
            img.setBounds( 0, 0, 120, 120 );
        }
        badgeView = new BadgeView(view.getContext(), imgv_category);
        if (model.getCnt() < 1) {
            badgeView.setText("준비중..");
            badgeView.setBadgeMargin(10);
            imgv_category.setImageDrawable(img);
            badgeView.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_END);
            badgeView.setBackgroundColor(Color.parseColor("#90808080"));
        } else {
            badgeView.setText(model.getCnt()+"");
        }
        badgeView.show();

        return view;

    }


    @Override
    public boolean hasStableIds() {
        return true;
    }


    @Override
    public boolean isChildSelectable(int arg0, int arg1) {
        return true;
    }


    @Override
    public void onClick(View v) {

    }
}

