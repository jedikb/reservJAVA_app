package com.example.reservjava_app.ATask;

import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;

import com.example.reservjava_app.DTO.BookingDTO;
import com.example.reservjava_app.DTO.MemberDTO;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.reservjava_app.Common.CommonMethod.ipConfig;
import static com.example.reservjava_app.Common.CommonMethod.pServer;

public class BookingView extends AsyncTask<Void, Void, BookingDTO> {
  private static final String TAG = "main:BookingView";
  String booking_code;
  public static BookingDTO bookingData = null;

  HttpClient httpClient;
  HttpPost httpPost;
  HttpResponse httpResponse;
  HttpEntity httpEntity;

  public BookingView(String booking_code) {
    this.booking_code = booking_code;
  }

  @Override
  protected BookingDTO doInBackground(Void... voids) {

    try {
      // MultipartEntityBuild 생성
      MultipartEntityBuilder builder = MultipartEntityBuilder.create();
      builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
      builder.setCharset(Charset.forName("UTF-8"));

      // 문자열 및 데이터 추가
      builder.addTextBody("booking_code", booking_code, ContentType.create("Multipart/related", "UTF-8"));

      Log.d(TAG, "doInBackground: booking_code= "+ booking_code);

      //접속 경로
      String postURL = ipConfig + pServer + "/anBookingView";
      //Log.d(TAG, "doInBackground: "+ipConfig);

      // 전송
      InputStream inputStream = null;
      httpClient = AndroidHttpClient.newInstance("Android");
      httpPost = new HttpPost(postURL);
      httpPost.setEntity(builder.build());
      httpResponse = httpClient.execute(httpPost);
      httpEntity = httpResponse.getEntity();
      inputStream = httpEntity.getContent();

      // 하나의 오브젝트 가져올때
      bookingData = readMessage(inputStream);

      inputStream.close();

    } catch (Exception e) {
      Log.d(TAG, "doInBackground: " + e.getMessage() );
      e.printStackTrace();
    }finally {
      if(httpEntity != null){
        httpEntity = null;
      }
      if(httpResponse != null){
        httpResponse = null;
      }
      if(httpPost != null){
        httpPost = null;
      }
      if(httpClient != null){
        httpClient = null;
      }

    }
    return bookingData;
  }

  public BookingDTO readMessage(InputStream inputStream) throws IOException {
    JsonReader reader = new JsonReader(new InputStreamReader(inputStream, "UTF-8"));

    int booking_code = 0;
    int booking_kind = 0;
    int booking_member_code = 0;
    int booking_business_code = 0;
    int booking_product_code = 0;
    int booking_price = 0;
    int booking_price_deposit = 0;
    int booking_num = 0;
    Date booking_date = null;
    Date booking_date_reservation = null;
    String booking_etc = null;
    int booking_appraisal_star = 0;
    String booking_appraisal = null;

    reader.beginObject();
    while (reader.hasNext()) {
      SimpleDateFormat fm = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      String readStr = reader.nextName();

      if      (readStr.equals("booking_code"))           booking_code          = reader.nextInt();
      else if (readStr.equals("booking_kind"))           booking_kind          = reader.nextInt();
      else if (readStr.equals("booking_member_code"))    booking_member_code   = reader.nextInt();
      else if (readStr.equals("booking_business_code"))  booking_business_code = reader.nextInt();
      else if (readStr.equals("booking_product_code"))   booking_product_code  = reader.nextInt();
      else if (readStr.equals("booking_price"))          booking_price         = reader.nextInt();
      else if (readStr.equals("booking_price_deposit"))  booking_price_deposit = reader.nextInt();
      else if (readStr.equals("booking_num"))            booking_num           = reader.nextInt();
      else if (readStr.equals("booking_date"))     try { booking_date          = fm.parse( reader.nextString() );
                                                   } catch (Exception e)         { e.printStackTrace(); }
      else if (readStr.equals("booking_date_reservation")) try { booking_date_reservation = fm.parse( reader.nextString() );
                                                   } catch (Exception e)         { e.printStackTrace(); }
      else if (readStr.equals("booking_etc"))            booking_etc           = reader.nextString();
      else if (readStr.equals("booking_appraisal_star")) booking_appraisal_star= reader.nextInt();
      else if (readStr.equals("booking_appraisal"))      booking_appraisal     = reader.nextString();
      else    reader.skipValue();

    }//while()

    reader.endObject();
    Log.d(TAG, "readMessage: " + " : " + booking_code + " : " + booking_kind + " : " + booking_member_code + " : " + booking_business_code + " : " + booking_product_code + " : " + booking_price + " : " + booking_price_deposit + " : " + booking_num + " : " + booking_date + " : " + booking_date_reservation + " : " + booking_etc + " : " + booking_appraisal_star + " : " + booking_appraisal);
    return new BookingDTO(booking_code, booking_kind, booking_member_code, booking_business_code, booking_product_code, booking_price, booking_price_deposit, booking_num, booking_date, booking_date_reservation, booking_etc, booking_appraisal_star, booking_appraisal);
  }
}