package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
    EditText username, userage, userlocation;
    Button find_location, check1;
    AlertDialog alertDialog;
    SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username=(EditText)findViewById(R.id.username);
        userage=(EditText)findViewById(R.id.userage);
        userlocation=(EditText)findViewById(R.id.userlacation);
        find_location = (Button)findViewById(R.id.find_location);
        check1 = (Button)findViewById(R.id.check1);
        shared = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
        String token = shared.getString( "refreshedToken", "" );

        //gps값 찾기 버튼 클릭 이벤트
        find_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Take_Info_Check.class);
                startActivity(intent);
            }
        });


        //등록하기 버튼 클릭 이벤트
        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog = new AlertDialog.Builder(MainActivity.this).create();

                alertDialog.setTitle("등록확인");
                alertDialog.setMessage("등록 하시겠습니까?");
                alertDialog.setCancelable(false);
                alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, Success_Register.class);
                        startActivity(intent);
                    }
                });
                alertDialog.show();
            }
        });
    }
}