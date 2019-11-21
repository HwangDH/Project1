package com.example.project1.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project1.List.Company_List_VO;
import com.example.project1.R;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class Company_Adapter extends BaseAdapter{
    private ArrayList<Company_List_VO> listVO = new ArrayList<Company_List_VO>();

    public Company_Adapter(){

    }

    @Override
    public int getCount(){
        return listVO.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.activity_take_info_list, parent, false);
        }

        TextView company_name = (TextView)convertView.findViewById(R.id.compay_name);
        TextView company_boss = (TextView)convertView.findViewById(R.id.compay_boss);

        Company_List_VO listViewItem = listVO.get(position);

        company_name.setText(listViewItem.getCompany_name());
        company_boss.setText(listViewItem.getCompany_boss());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (pos+1)+"번째가 클릭되었습니다.", Toast.LENGTH_SHORT).show();
            }
        });

        return  convertView;
    }

    @Override
    public long getItemId(int position){
        return position;
    }
    @Override
    public Object getItem(int position){
        return listVO.get(position);
    }

    public void addVO(String company_name, String company_boss){
        Company_List_VO item = new Company_List_VO();

        item.setCompany_name(company_name);
        item.setCompany_boss(company_boss);

        listVO.add(item);
    }
}
