package com.example.reservjava_app.ui.b_where;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.R;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;

public class AddrListActivity extends AppCompatActivity {

  private WebView addrListV;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_addr_list);

    addrListV = (WebView) findViewById(R.id.addrListView);
    addrListV.getSettings().setJavaScriptEnabled(true);
    addrListV.addJavascriptInterface(new MyJavaScriptInterface(), "Android");

    addrListV.setWebViewClient(new WebViewClient() {
      @Override
      public void onPageFinished(WebView view, String url) {
        addrListV.loadUrl("javascript:sample2_execDaumPostcode();");
      }
    });
    addrListV.loadUrl( ipConfig + "/reservjava_app/resources/daum.html");

  }

  class MyJavaScriptInterface
  {
    @JavascriptInterface
    @SuppressWarnings("unused")
    public void processDATA(String data) {
      Bundle extra = new Bundle();
      Intent intent = new Intent();
      extra.putString("data", data);
      extra.putSerializable("newAddr", 1);
      intent.putExtras(extra);
      setResult(RESULT_OK, intent);
      finish();
    }
  }
}