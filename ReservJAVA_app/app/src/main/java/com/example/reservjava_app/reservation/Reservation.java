package com.example.reservjava_app.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reservjava_app.ATask.BookingInsert;
import com.example.reservjava_app.ATask.ProductSelect;
import com.example.reservjava_app.ATask.Timelist;
import com.example.reservjava_app.DTO.ProductDTO;
import com.example.reservjava_app.Listner.TimeItemClickListener;
import com.example.reservjava_app.MainActivity;
import com.example.reservjava_app.R;
import com.example.reservjava_app.adapter.ProductAdapter;
import com.example.reservjava_app.adapter.TimeListAdapter;
import com.example.reservjava_app.reservation.decorators.EventDecorator;
import com.example.reservjava_app.reservation.decorators.OneDayDecorator;
import com.example.reservjava_app.reservation.decorators.SaturdayDecorator;
import com.example.reservjava_app.reservation.decorators.SundayDecorator;
import com.example.reservjava_app.ui.b_where.WhereListActivity;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import static com.example.reservjava_app.Common.CommonMethod.loginDTO;


public class Reservation extends AppCompatActivity implements View.OnClickListener {

    ProductSelect productSelect;
    Timelist timelistAsync;
    BookingInsert bookinginsert;
    TimeListAdapter timeListAdapter;
    ProductAdapter productAdapter;
    ProgressDialog progressDialog;
    RecyclerView recyclerView_product, recyclerView_time;
    ArrayList<ProductDTO> productList;
    ArrayList<String> timeList;

    String Resrv_date = null, state, time = null;
    TextView calendar_text, time_text, product_text, person_text, per , tv_business_code,date_tv;
    EditText edt_etc;
    int person = 1, maxPerson = 5, minPerson = 1;
    int business_code;
    Button reservBtn, cancleBtn , rev_btn_date , rev_btn_time , rev_btn_item;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();
    private Drawable check_img ;
    MaterialCalendarView materialCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);
        Intent getintent = getIntent();
        //Check img 값 선택시 이미지 변경용
        check_img = this.getResources().getDrawable( R.drawable.check_img);

        business_code = getintent.getIntExtra("business_code", -1);
        rev_btn_date = findViewById(R.id.rev_btn_date);
        rev_btn_time = findViewById(R.id.rev_btn_time);
        rev_btn_item = findViewById(R.id.rev_btn_item);
        rev_btn_date.setOnClickListener(this);
        rev_btn_time.setOnClickListener(this);
        rev_btn_item.setOnClickListener(this);
        rev_btn_date.setTag("N");
        rev_btn_time.setTag("N");
        rev_btn_item.setTag("N");
        recyclerView_time = findViewById(R.id.time_list);
        recyclerView_product = findViewById(R.id.product_list);
        //Text View find & Event
        calendar_text = findViewById(R.id.calendar_text);
        time_text = findViewById(R.id.time_text);
        product_text = findViewById(R.id.product_text);

        calendar_text.setOnClickListener(this);
        time_text.setOnClickListener(this);
        product_text.setOnClickListener(this);
        edt_etc = findViewById(R.id.edt_etc);


        Date currentTime = Calendar.getInstance().getTime();
        materialCalendarView = findViewById(R.id.calendarView);
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());
        SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
        String syear = yearFormat.format(currentTime);
        String smonth = monthFormat.format(currentTime);
        String sday = dayFormat.format(currentTime);
        int yyyy = Integer.parseInt(syear);
        int MM = Integer.parseInt(smonth);
        int dd = Integer.parseInt(sday);
        MM = MM-1;
        materialCalendarView.state().edit()
                .setFirstDayOfWeek(Calendar.SUNDAY)
                .setMinimumDate(CalendarDay.from( yyyy, MM, dd)) // 달력의 시작
                .setMaximumDate(CalendarDay.from(yyyy, MM+5, 31)) // 달력의 끝
                .setCalendarDisplayMode(CalendarMode.MONTHS)
                .commit();

        materialCalendarView.addDecorators(
                new SundayDecorator(),
                new SaturdayDecorator(),
                oneDayDecorator);

        String[] result = {"2021,03,16","2021,05,19","2020,09,20","2020,09,21"};
        new ApiSimulator(result).executeOnExecutor(Executors.newSingleThreadExecutor());
        materialCalendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                try{


                int Year = date.getYear();
                int Month = date.getMonth()+1;
                int Day = date.getDay();

                String shot_Day = Year + "," + Month + "," + Day;

                Resrv_date = String.format("%d년 %d월 %d일", Year, Month, Day);
                Toast.makeText(Reservation.this, Resrv_date, Toast.LENGTH_SHORT).show();
                materialCalendarView.clearSelection();
                calendar_text.setText(Resrv_date);
                ChangeVisible(2, rev_btn_time);
                rev_btn_date.setCompoundDrawablesWithIntrinsicBounds(  null,null, check_img, null);
                calendar_text.setCompoundDrawablesWithIntrinsicBounds(  null,null, check_img, null);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        //시간  리사이클 뷰 처리
        if(business_code != -1){
            timeList = new ArrayList<>();
            tv_business_code = findViewById(R.id.tv_bs_code);
            tv_business_code.setText(business_code+"");
            timeListAdapter = new TimeListAdapter(Reservation.this, timeList);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
            recyclerView_time.setLayoutManager(layoutManager);
            recyclerView_time.setAdapter(timeListAdapter);
            timelistAsync = new Timelist(timeList, timeListAdapter, progressDialog, business_code);
            try {
                timelistAsync.execute().get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            timeListAdapter.settime_Text(time_text);
            if (timeList.size() > 0){
            time = timeList.get(0);
            }

            timeListAdapter.setOnItemClickListener(new TimeItemClickListener() {
                @Override
                public void onItemClick(TimeListAdapter.ViewHolder holder, View view, int position) {
                    time_text.setText(timeList.get(position));
                    time = timeList.get(position);
                    productList = new ArrayList<>();
                    productAdapter = new ProductAdapter(Reservation.this, productList);

                    LinearLayoutManager layoutManager1 = new LinearLayoutManager(Reservation.this, RecyclerView.VERTICAL, false);
                    recyclerView_product.setLayoutManager(layoutManager1);
                    recyclerView_product.setAdapter(productAdapter);
                    productSelect = new ProductSelect(productAdapter, productList, progressDialog, business_code, time);
                    rev_btn_time.setCompoundDrawablesWithIntrinsicBounds(  null,null, check_img, null);
                    time_text.setCompoundDrawablesWithIntrinsicBounds(  null,null, check_img, null);
                    ChangeVisible(3, rev_btn_item);
                    try {
                        productSelect.execute().get();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    productAdapter.setproduct_text(product_text);
                    productAdapter.setProduct_item(rev_btn_item,check_img);

                }
            });
        }




        //상품리스트 표시

        //인원 textView 숫자 처리 및 인원 제한 걸어 보기
        person_text = findViewById(R.id.person_text);
        per = findViewById(R.id.person);

        person_text.setText(Integer.toString(person));
        per.setText(Integer.toString(person));

        findViewById(R.id.person_Plus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person++;
                if (person > maxPerson){
                    person = maxPerson;
                    Toast.makeText(Reservation.this, "최대인원수", Toast.LENGTH_SHORT).show();
                }else{
                    person_text.setText(Integer.toString(person));
                    per.setText(Integer.toString(person));
                }
            }
        });
        findViewById(R.id.person_Minus).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                person--;
                if (person < minPerson) {
                    person = minPerson;
                    Toast.makeText(Reservation.this, "최소인원수", Toast.LENGTH_SHORT).show();
                } else {
                    person_text.setText(Integer.toString(person));
                    per.setText(Integer.toString(person));
                }
            }
        });

        reservBtn = findViewById(R.id.rev_choice);
        reservBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //예약 insert할 데이터 DTO 에 담기
                ProductDTO productDTO = productAdapter.getDTO();
                if (Resrv_date == null) {
                    //날짜 선택 해주세요
                    return;
                } else if (time == null) {
                    //시간을 선택 해주세요
                    return;
                } else if (productDTO == null) {
                    return;
                    //상품을 선택 해주세요
                } else {
                    //작동
                    bookinginsert = new BookingInsert(loginDTO.getMember_code(), business_code, productDTO.getProduct_code(), productDTO.getProduct_price(), productDTO.getProduct_price_deposit()
                            , person, Resrv_date + " " + time , edt_etc.getText() + "");
                    try {
                        state = bookinginsert.execute().get().trim();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    Log.d("bookingInsert: ", "onClick: " + productDTO.getProduct_code() + ", " + time);

                    if (state.equals("1")) {
                        Toast.makeText(Reservation.this, "예약이 등록 되었습니다", Toast.LENGTH_SHORT).show();
                        Log.d("bookingInsert:", "예약이 등록 되었습니다");
                        finish();
                    } else {
                        Toast.makeText(Reservation.this, "예약에 실패하였습니다", Toast.LENGTH_SHORT).show();
                        Log.d("bookingInsert", "예약에 실패하였습니다");
                        finish();
                    }

                    finish();
                }//if else
            }
        });

        cancleBtn = findViewById(R.id.rev_cancle);
        cancleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }//onCreat()


    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        String[] Time_Result;

        ApiSimulator(String[] Time_Result){
            this.Time_Result = Time_Result;
        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Calendar calendar = Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();

            /*특정날짜 달력에 점표시해주는곳*/
            /*월은 0이 1월 년,일은 그대로*/
            //string 문자열인 Time_Result 을 받아와서 ,를 기준으로짜르고 string을 int 로 변환
            for(int i = 0 ; i < Time_Result.length ; i ++){
                CalendarDay day = CalendarDay.from(calendar);
                String[] time = Time_Result[i].split(",");
                int year = Integer.parseInt(time[0]);
                int month = Integer.parseInt(time[1]);
                int dayy = Integer.parseInt(time[2]);

                dates.add(day);
                calendar.set(year,month-1,dayy);
            }



            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }

            materialCalendarView.addDecorator(new EventDecorator(Color.GREEN, calendarDays, Reservation.this));
        }
    }

    //Button 클릭 이벤트
    @Override
    public void onClick(View view) {

        if(view instanceof  Button) {
            Button btn = (Button) view;
            switch (view.getId()) {
                case R.id.rev_btn_date:
                    ChangeVisible(1, btn);
                    break;
                case R.id.rev_btn_time:
                    if (calendar_text.getText().length() < 1) {
                        Toast.makeText(Reservation.this, "날짜를 먼저 선택해 주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        ChangeVisible(2, btn);
                    }
                    break;
                case R.id.rev_btn_item:
                    if (time_text.getText().length() < 1) {
                        Toast.makeText(Reservation.this, "시간을 먼저 선택해 주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        ChangeVisible(3, btn);
                    }
                    break;
            }
        }   else if(view instanceof TextView){
            TextView tv = (TextView) view;
            switch (tv.getId()){
                case R.id.calendar_text:
                    ChangeVisible(1, rev_btn_date);
                    break;
                case R.id.time_text:
                    if (calendar_text.getText().length() < 1) {
                        Toast.makeText(Reservation.this, "날짜를 먼저 선택해 주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        ChangeVisible(2, rev_btn_time);
                    }
                    break;
                case R.id.product_text:
                    if (time_text.getText().length() < 1) {
                        Toast.makeText(Reservation.this, "시간을 먼저 선택해 주세요", Toast.LENGTH_SHORT).show();
                    } else {
                        ChangeVisible(3, rev_btn_item);
                    }
                    break;
            }
        }
    }

    private void ChangeVisible(int flag , Button btn){
        Animation animation = new AlphaAnimation(0, 1);
        animation.setDuration(1000);
        Drawable img1 = this.getResources().getDrawable( R.drawable.btn_plus );
        Drawable img2 = this.getResources().getDrawable( R.drawable.btn_plust );

        if(btn.getTag().toString().equals("Y")){
            btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img1, null);
        }else{
            btn.setCompoundDrawablesWithIntrinsicBounds(null, null, img2, null);
        }
        if(flag == 1){
            if(btn.getTag().toString().equals("Y")){
                btn.setTag("N");
                materialCalendarView.setVisibility(View.GONE);
            }else{
                btn.setTag("Y");
                materialCalendarView.setVisibility(View.VISIBLE);
                materialCalendarView.setAnimation(animation);
            }
            recyclerView_time.setVisibility(View.GONE);
            recyclerView_product.setVisibility(View.GONE);
        }else if(flag == 2){
            if(btn.getTag().toString().equals("Y")){
                btn.setTag("N");
                recyclerView_time.setVisibility(View.GONE);
            }else{
                btn.setTag("Y");
                recyclerView_time.setVisibility(View.VISIBLE);
                recyclerView_time.setAnimation(animation);
            }
            materialCalendarView.setVisibility(View.GONE);
            recyclerView_product.setVisibility(View.GONE);
        }else if(flag == 3){
            if(btn.getTag().toString().equals("Y")){
                btn.setTag("N");
                recyclerView_product.setVisibility(View.GONE);
            }else {
                btn.setTag("Y");
                recyclerView_product.setVisibility(View.VISIBLE);
                recyclerView_product.setAnimation(animation);
                materialCalendarView.setVisibility(View.GONE);
                recyclerView_time.setVisibility(View.GONE);
            }
        }
    }

}