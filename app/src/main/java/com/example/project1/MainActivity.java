package com.example.project1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import java.util.HashMap;
import java.util.Map;
import static android.content.ContentValues.TAG;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends Activity {
    private RequestQueue requestQueue;
    StringRequest stringRequest;

    EditText username, userage, userlocation, item1, item2;
    Button find_location, check1;
    AlertDialog alertDialog;
    SharedPreferences sharedPreferences;

    public String token="";
    String user_name="", user_age="", items_1="", items_2="";

    final long INTERVAL_TIME = 1000;
    long previousTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }
                        // Get new Instance ID token
                        token = task.getResult().getToken();
                        Toast.makeText(MainActivity.this, token, Toast.LENGTH_SHORT).show();
                        System.out.println(token);
                    }
                });

        username=(EditText)findViewById(R.id.username);
        userage=(EditText)findViewById(R.id.userage);
        item1 = (EditText)findViewById(R.id.item1);
        item2 = (EditText)findViewById(R.id.item2);
        userlocation=(EditText)findViewById(R.id.userlacation);
        find_location = (Button)findViewById(R.id.find_location);

        check1 = (Button)findViewById(R.id.check1);

        Intent intent = getIntent();

        sharedPreferences = getSharedPreferences("Mypref", Context.MODE_PRIVATE);
      //  final String token = sharedPreferences.getString( "refreshedToken", "" );

        final String latitude = intent.getStringExtra("latitude");
        final String longitude = intent.getStringExtra("longitude");

        String name = intent.getStringExtra("name");
        String age = intent.getStringExtra("age");
        String it1 = intent.getStringExtra("item1");
        String it2 = intent.getStringExtra("item2");

        username.setText(name);
        userage.setText(age);
        item1.setText(it1);
        item2.setText(it2);
        userlocation.setText(latitude + ", " + longitude);


        //gps값 찾기 버튼 클릭 이벤트
        find_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_name = username.getText().toString();
                user_age = userage.getText().toString();
                items_1 = item1.getText().toString();
                items_2 = item2.getText().toString();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("name", user_name);
                editor.putString("age", user_age);
                editor.putString("item1", items_1);
                editor.putString("item2", items_2);

                editor.commit();
                Intent intent = new Intent(MainActivity.this, KakaoMap.class);
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
                        user_name = username.getText().toString();
                        user_age = userage.getText().toString();
                        items_1 = item1.getText().toString();
                        items_2 = item2.getText().toString();

                        if (user_name.isEmpty() || user_age.isEmpty() || items_1.isEmpty() || items_2.isEmpty()) {
                            Toast.makeText(MainActivity.this, "빈칸없이 입력해주세요.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            send_info(user_name, user_age, items_1, items_2, latitude, longitude, token);
                            show();
                        }
                    }
                });
                alertDialog.show();
            }
        });
    }

    public void show(){
        Intent intent = new Intent(MainActivity.this, Success_Register.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }


    public void send_info(final String user_name, final String user_age, final String item1, final String item2, final String latitude, final  String longitude, final String tokens){
        String url = "https://scv0319.cafe24.com/weall/register_customer.php";
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("Hitesh",""+response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Hitesh",""+error);
                Toast.makeText(MainActivity.this, ""+error, Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> stringMap = new HashMap<>();
                stringMap.put("username", user_name);
                stringMap.put("userage", user_age);
                stringMap.put("item1",item1);
                stringMap.put("item2",item2);
                stringMap.put("latitude", latitude);
                stringMap.put("longitude",longitude);
                stringMap.put("token", tokens);

                return stringMap;
            }
        };
        requestQueue.add(stringRequest);
    }


    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if((currentTime - previousTime) <= INTERVAL_TIME) {
            super.onBackPressed();
        } else {
            previousTime = currentTime;
            Toast.makeText(this, "한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
    }
}