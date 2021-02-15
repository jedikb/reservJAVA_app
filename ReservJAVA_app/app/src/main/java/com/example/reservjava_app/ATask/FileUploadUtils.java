package com.example.reservjava_app.ATask;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.reservjava_app.ATask.Common.CommonMethod.business_upImgPath;
import static com.example.reservjava_app.ATask.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.ATask.Common.CommonMethod.logo_upImgPath;
import static com.example.reservjava_app.ATask.Common.CommonMethod.member_upImgPath;
import static com.example.reservjava_app.ATask.Common.CommonMethod.pServer;
import static com.example.reservjava_app.ATask.Common.CommonMethod.product_upImgPath;

// 보류.. 기능 구현이 너무 힘듬.. 그냥 쌤꺼 쓰자..
// 나중에 사진들을 자유롭게 업로드 하려면 필요할 듯하니 보존해둔다

public class FileUploadUtils {
  private static final String TAG = "main:FileUploadUtils";
  public static void send2Server(File file, String Path) {
    RequestBody requestBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("files", file.getName(), RequestBody.create(MultipartBody.FORM, file))
        .build();

/*    //다른 곳 참조한것...
    RequestBody requestBody = new MultipartBody.Builder()
        .setType(MultipartBody.FORM)
        .addFormDataPart("title", "STORE Camera")
        .addFormDataPart("original_photo","original_photo.jpg", RequestBody.create(MultipartBody.FORM, new File(mFilePath01)))
        .addFormDataPart("guide_photo","guide_photo.jpg", RequestBody.create(MultipartBody.FORM, new File(mFilePath02)))
        .build();*/

    //다른 파일을 업로드 할 경우 url 변경에 대한 if문
    String savePath = "";
    if(Path == "1") {
      savePath = ipConfig+pServer+member_upImgPath;
    } else if(Path == "2") {
      savePath = ipConfig+pServer+logo_upImgPath;
    } else if(Path == "3") {
      savePath = ipConfig+pServer+business_upImgPath;
    } else if(Path == "4") {
      savePath = ipConfig+pServer+product_upImgPath;
    }

    Log.d(TAG, "send2Server: " + savePath);

    Request request = new Request.Builder()
        .url(savePath)
        .post(requestBody)
        .build();

    OkHttpClient client = new OkHttpClient();
    client.newCall(request).enqueue(new Callback() {

      @Override
      public void onFailure(@NotNull Call call, @NotNull IOException e) {
        e.printStackTrace();
      }

      @Override
      public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
        Log.d("main:FileUploadUtils  ", response.body().string());
      }
    });
  }
}
