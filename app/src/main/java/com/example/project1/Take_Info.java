package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import com.example.project1.Adapter.Company_Adapter;

import org.json.JSONArray;
import org.json.JSONObject;

public class Take_Info extends AppCompatActivity implements View.OnClickListener {
    private ListView listview;
    JSONArray peoples = null;
    ArrayList<HashMap<String, String>> personList;
    String myJSON;
    ListAdapter adapter;
    int count = 0;
    private Company_Adapter adapter2;

   // private String[] company_name = {"우리다", "동원"};
    private String[] company_boss = {"123", "몰라"};
    private String[] company_location = {"456", "몰라"};

    String [] company_name = new String[30];
    String [] latitude = new String[30];
    String [] longitude = new String[30];

    final long INTERVAL_TIME = 1000;
    long previousTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take__info);

        Intent intent = getIntent();
        final String latitude1 = intent.getStringExtra("latitude");
        final String longitude1 = intent.getStringExtra("longitude");

        adapter = new Company_Adapter();

        listview = (ListView)findViewById(R.id.list);
        personList = new ArrayList<HashMap<String, String>>();

        getData("https://scv0319.cafe24.com/weall/company_list.php?latitude1="+latitude1+"&longitude1="+longitude1+"");
        listview.setAdapter(adapter);

       /* for(int i = 0; i< company_name.length; i++){
            adapter2.addVO(company_name[i], company_boss[i], company_location[i]);
        }*/

    }

    public void showList(){
        try{
            JSONObject jsonObject = new JSONObject(myJSON );
            peoples = jsonObject.getJSONArray("response");
            //JSON 배열 길이만큼 반복문을 실행
            while(count < peoples.length()){
                JSONObject object = peoples.getJSONObject(count);
                company_name[count] = object.getString("company_name");

                latitude[count] = object.getString("latitude");
                longitude[count] = object.getString("longitude");
                HashMap<String, String> persons = new HashMap<>();
                persons.put("company_name", company_name[count]);
                persons.put("latitude", latitude[count]);
                persons.put("longitude", longitude[count]);
                personList.add(persons);
                adapter = new SimpleAdapter(
                        Take_Info.this, personList, R.layout.activity_take_info_list,
                        new String[] {"company_name", "latitude", "longitude"},
                        new int[] {R.id.company_name, R.id.company_boss, R.id.company_location}
                );
                listview.setAdapter(adapter);
                count++;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void getData(String url) {
        class GetDataJSON extends AsyncTask<String, Void, String> {

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    return sb.toString().trim();

                } catch (Exception e) {
                    return null;
                }


            }

            @Override
            protected void onPostExecute(String result) {
                myJSON = result;
                showList();
            }
        }
        GetDataJSON g = new GetDataJSON();
        g.execute(url);
    }

    @Override
    public void onClick(View v){

    }

    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();

        if((currentTime - previousTime) <= INTERVAL_TIME) {
            super.onBackPressed();
        }
    }
}
