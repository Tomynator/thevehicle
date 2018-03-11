package com.weiapps.myVehicle;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import static com.weiapps.myVehicle.ListViewConstants.FIFTH_COLUMN;
import static com.weiapps.myVehicle.ListViewConstants.FIRST_COLUMN;
import static com.weiapps.myVehicle.ListViewConstants.FOURTH_COLUMN;
import static com.weiapps.myVehicle.ListViewConstants.SECOND_COLUMN;
import static com.weiapps.myVehicle.ListViewConstants.THIRD_COLUMN;


/**
 * Created by Thomas on 15.02.2018.
 */

public class LvAdapterTankstopp extends BaseAdapter{

    private ArrayList<HashMap<String, String>> list;
    private Activity activity;
    private TextView txtFirst;
    private TextView txtSecond;
    private TextView txtThird;
    private TextView txtFourth;
    private TextView txtFifth;

    public LvAdapterTankstopp(Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.tankstopp_row, null);

            txtFirst=(TextView) convertView.findViewById(R.id.colDatum);
            txtSecond=(TextView) convertView.findViewById(R.id.colStrecke);
            txtThird=(TextView) convertView.findViewById(R.id.colBetrag);
            txtFourth=(TextView) convertView.findViewById(R.id.colMenge);
            txtFifth=(TextView) convertView.findViewById(R.id.colNotiz);

        }

        HashMap<String, String> map=list.get(position);
        txtFirst.setText(map.get(FIRST_COLUMN));
        txtSecond.setText(map.get(SECOND_COLUMN));
        txtThird.setText(map.get(THIRD_COLUMN));
        txtFourth.setText(map.get(FOURTH_COLUMN));
        txtFifth.setText(map.get(FIFTH_COLUMN));

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
