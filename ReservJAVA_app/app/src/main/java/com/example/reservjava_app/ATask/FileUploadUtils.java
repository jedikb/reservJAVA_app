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

import static com.example.reservjava_app.Common.CommonMethod.imgPath;
import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.pServer;

public class FileUploadUtils {
  public static void send2Server(File file, String Path) {
    RequestBody requestBody = new MultipartBody.Builder()
      .setType(MultipartBody.FORM)
      .addFormDataPart("files", file.getName(), RequestBody.create(MultipartBody.FORM, file))
      .build();
        //다른 파일을 업로드 할 경우 url 변경에 대한 if문
        String savePath = "";
        if(Path == "1") {
          savePath = ipConfig+pServer+imgPath;
        }
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
