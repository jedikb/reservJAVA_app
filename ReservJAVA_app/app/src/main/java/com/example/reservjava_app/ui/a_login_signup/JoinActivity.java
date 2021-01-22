package com.example.reservjava_app.ui.a_login_signup;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reservjava_app.ATask.JoinInsert;
import com.example.reservjava_app.R;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {

    String state;

    EditText Id_text, Pw_text, Pwchk_text, nick_text, Email_text, Phone_text;
    Button signupbtn, resetbtn;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.activity_signup, container, false);

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Id_text = findViewById(R.id.Id_text);
        Pw_text = findViewById(R.id.Pw_text);
        Pwchk_text = findViewById(R.id.Pwchk_text);
        nick_text = findViewById(R.id.nick_text);
        Email_text = findViewById(R.id.Email_text);
        Phone_text = findViewById(R.id.Phone_text);
        signupbtn = findViewById(R.id.signupbtn);
        resetbtn = findViewById(R.id.resetbtn);
*/
        //취소버튼 누르면 로그인페이지로 넘어갑니다
        resetbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        //회원가입
        /*signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = Id_text.getText().toString();
                String passwd = Pw_text.getText().toString();
                String passwdchk = Pwchk_text.getText().toString();
                String name = nick_text.getText().toString();
                String phonenumber = Phone_text.getText().toString();
                String email = Email_text.getText().toString();

                JoinInsert joinInsert = new JoinInsert(id, passwd, passwdchk, name, phonenumber, email);
                try {
                    state = joinInsert.execute().get().trim();
                    Log.d("main:joinact0 : ", state);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (state.equals("1")) {
                    Toast.makeText(JoinActivity.this, "가입성공 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "가입성공 !!!");
                    finish();
                } else {
                    Toast.makeText(JoinActivity.this, "가입실패 !!!", Toast.LENGTH_SHORT).show();
                    Log.d("main:joinact", "가입실패 !!!");
                    finish();
                }
            }
        });
*/

        //가입확인 승인되면 회원가입완료창으로 넘어갑니다
        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        return root;
    }
}
