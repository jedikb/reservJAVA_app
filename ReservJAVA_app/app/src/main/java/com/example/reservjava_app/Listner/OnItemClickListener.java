package com.example.reservjava_app.Listner;

import android.view.View;

import com.example.reservjava_app.adapter.StoreListAdapter;

public interface OnItemClickListener {
    public void onItemClick(StoreListAdapter.ViewHolder holderm,
                            View view, int position);
}
