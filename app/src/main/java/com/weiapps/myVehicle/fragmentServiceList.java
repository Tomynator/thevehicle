package com.weiapps.myVehicle;

import static com.weiapps.myVehicle.ListViewConstants.*;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;



/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentServiceList extends Fragment {

    View view = null;

    public fragmentServiceList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_servicelist,
                container, false);

        TextView head = (TextView)view.findViewById(R.id.lbl_head);
        System.out.println(head.getText());

        //ListView lvService = (ListView)view.findViewById(R.id.lv_Service);
        ListView lvService = (ListView)view.findViewById(R.id.lv_Service);
        System.out.println(view.findViewById(R.id.lv_Service));

        ArrayList list=new ArrayList<HashMap<String,String>>();

        HashMap<String,String> temp=new HashMap<String, String>();
        temp.put(FIRST_COLUMN, "02.02.2018");
        temp.put(SECOND_COLUMN, "Service");
        temp.put(THIRD_COLUMN, "200");
        temp.put(FOURTH_COLUMN, "Jagersberger");
        list.add(temp);

        HashMap<String,String> temp2=new HashMap<String, String>();
        temp2.put(FIRST_COLUMN, "17.12.2017");
        temp2.put(SECOND_COLUMN, "Pickerl");
        temp2.put(THIRD_COLUMN, "225");
        temp2.put(FOURTH_COLUMN, "§57 muss sein");
        list.add(temp2);

        ListViewAdapter adapter = new ListViewAdapter(getActivity(), list, R.layout.service_row);
        lvService.setAdapter(adapter);

        lvService.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                int pos=position+1;
                Toast.makeText(getContext(), Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();
            }

        });





        //return inflater.inflate(R.layout.fragment_service, container, false);
        return view;
    }

}
