package com.example.reservjava_app.ui.b_where;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.example.reservjava_app.R;

public class WhereListActivity extends AppCompatActivity {

  ListView listView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_where_list);

    listView = findViewById(R.id.listView);

  }
}