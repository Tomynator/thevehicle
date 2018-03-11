package com.weiapps.myVehicle;

import com.weiapps.myVehicle.ListViewConstants;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import static com.weiapps.myVehicle.ListViewConstants.*;


/**
 * Created by Thomas on 15.02.2018.
 */

public class ListViewAdapter extends BaseAdapter{

    private int ListViewId;
    private ArrayList<HashMap<String, String>> list;
    private Activity activity;
    private TextView txtFirst;
    private TextView txtSecond;
    private TextView txtThird;
    private TextView txtFourth;


    public ListViewAdapter(Activity activity,ArrayList<HashMap<String, String>> list, int ListViewID){
        super();
        this.activity=activity;
        this.list=list;
        this.ListViewId = ListViewID;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            //convertView=inflater.inflate(R.layout.service_row, null);
            convertView=inflater.inflate(this.ListViewId, null);

            txtFirst=(TextView) convertView.findViewById(R.id.colDatum);
            txtSecond=(TextView) convertView.findViewById(R.id.colArt);
            txtThird=(TextView) convertView.findViewById(R.id.colBetrag);
            txtFourth=(TextView) convertView.findViewById(R.id.colNotiz);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        txtFourth.setText(map.get(FOURTH_COLUMN));

        return convertView;
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }
}
