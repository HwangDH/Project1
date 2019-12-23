package com.example.project1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class Success_Register extends Activity {
    Button check1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success__register);

        check1 = (Button) findViewById(R.id.check1);

        check1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Success_Register.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}