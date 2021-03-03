package com.example.reservjava_app.Listner;

import android.view.View;

import com.example.reservjava_app.adapter.TimeListAdapter;

public interface TimeItemClickListener {
    public void onItemClick(TimeListAdapter.ViewHolder holder, View view, int position);
}
