package com.example.reservjava_app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.Listner.OnItemClickListener;
import com.example.reservjava_app.R;
import com.example.reservjava_app.reservation.Reservation;

import java.io.File;
import java.util.ArrayList;

import static com.example.reservjava_app.Common.CommonMethod.loginDTO;

public class ProductAdapter extends
        RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    OnItemClickListener listener;

    Context context;
    ArrayList<ProductDTO> productList;
    ProductDTO dto;

    private TextView product_text;
    private Button rev_item_btn;
    private Drawable check_img ;
    private int position;



    public ProductAdapter(Context context, ArrayList<ProductDTO> productList) {
        this.context = context;
        this.productList = productList;
    }


    //화면(xml)연결
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_product, parent, false);

        return new ViewHolder(itemView, listener);
    }

    //데이터 연결
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        ProductDTO productDTO = productList.get(position);
        holder.onBind(productDTO);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product_text.setText(productList.get(position).getProduct_name());
                //check_img.setImageBitmap(productList.get(position).getProduct_image());
                dto = productList.get(position);
                rev_item_btn.setCompoundDrawablesWithIntrinsicBounds(  null,null, check_img, null);
            }
        });
    }

    public ProductDTO getDTO(){
        return dto;
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }

    public void addItem(ProductDTO productDTO){
        productList.add(productDTO);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView product_image;
        TextView product_name;
        TextView product_price;

        public LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            product_image = itemView.findViewById(R.id.product_image);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            parentLayout = itemView.findViewById(R.id.product_list_lay);

        }

        public void onBind(ProductDTO productDTO){
            String name = productDTO.getProduct_name();
            int price = productDTO.getProduct_price();
            Glide.with(itemView.getContext()).load(productDTO.getProduct_image_path()).error(R.drawable.food1).into(product_image);
            //Log.d("main: ", "onBind: " + productDTO.getProduct_image_path());
            product_name.setText(name);
            product_price.setText(price + "원");
        }

    }

    public ProductDTO getItem(int position){
        return productList.get(position);
    }

    public void setproduct_text(TextView product_text){
        this.product_text = product_text;
    }
    public void setProduct_item(Button rev_item_btn, Drawable check_img){
        this.rev_item_btn = rev_item_btn;
        this.check_img = check_img;
        rev_item_btn.setCompoundDrawablesWithIntrinsicBounds(  null,null, check_img, null);
        product_text.setCompoundDrawablesWithIntrinsicBounds(  null,null, check_img, null);
    }
}
