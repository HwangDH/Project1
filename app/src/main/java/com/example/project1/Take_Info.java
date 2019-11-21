package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import com.example.project1.Adapter.Company_Adapter;

public class Take_Info extends AppCompatActivity {
    private ListView listview;
    private Company_Adapter adapter;
    private String[] company_name = {"우리다", "동원"};
    private String[] company_boss = {"양동진", "몰라"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take__info);

        adapter = new Company_Adapter();
        listview = (ListView)findViewById(R.id.list);

        listview.setAdapter(adapter);

        for(int i = 0; i< company_name.length; i++){
            adapter.addVO(company_name[i], company_boss[i]);
        }
    }
}
