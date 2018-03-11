package com.weiapps.myVehicle;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.weiapps.myVehicle.ListViewConstants.FIFTH_COLUMN;
import static com.weiapps.myVehicle.ListViewConstants.FIRST_COLUMN;
import static com.weiapps.myVehicle.ListViewConstants.FOURTH_COLUMN;
import static com.weiapps.myVehicle.ListViewConstants.SECOND_COLUMN;
import static com.weiapps.myVehicle.ListViewConstants.THIRD_COLUMN;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentTankstoppList extends Fragment  {

    DialogFragment FrgDatePicker = null;
    View view = null;
    ArrayList listTs = null;

    public fragmentTankstoppList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
 //       return inflater.inflate(R.layout.fragment_tankstopp, container, false);

        view = inflater.inflate(R.layout.fragment_tankstopplist,
                container, false);

        ListView lvTankstopp = (ListView)view.findViewById(R.id.lv_Tankstopp);

        listTs = new ArrayList<HashMap<String,String>>();
        loadData(listTs);

        LvAdapterTankstopp adapter = new LvAdapterTankstopp(getActivity(), listTs);
        lvTankstopp.setAdapter(adapter);

        lvTankstopp.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id)
            {
                int pos=position+1;
                Toast.makeText(getContext(), Integer.toString(pos)+" Clicked", Toast.LENGTH_SHORT).show();

                //getFragmentManager().popBackStack();
                HashMap<String,String> selectedHM = new HashMap<String, String>();
                selectedHM = (HashMap)listTs.get(position);
                String datum = selectedHM.get(FIRST_COLUMN);

                fragmentTankstopp frgTs = new fragmentTankstopp();  //Get Fragment Instance
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("data", datum);//put string, int, etc in bundle with a key value
                frgTs.setArguments(data);//Finally set argument bundle to fragment

                getFragmentManager().beginTransaction().replace(R.id.layout_main_fragment, frgTs).addToBackStack(null).commit();//now replace the argument fragment

/*
                fragmentTankstopp frgTs = new fragmentTankstopp();
                //FragmentManager frgMan = getFragmentManager() ;
                getFragmentManager().beginTransaction().replace(R.id.layout_main_fragment, frgTs, frgTs.getTag()).addToBackStack(null).commit();
*/
            }

        });




        return(view);
        //return inflater.inflate(R.layout.fragment_tankstopp, container, false);
    }

    private void loadData(ArrayList listTs){

        Database db = new Database(getContext());
        List<Tankstopp> tsList = db.getAllTankstopps();

        for (Tankstopp ts : tsList) {
            String log = "Id: " + ts.getID() + " ,Name: " + ts.getDatum()
                    + " ,Strecke: " + ts.getStrecke() + " ,Menge:"
                    + ts.getMenge() + " ,Preis:" + ts.getBetrag();
            // Writing Contacts to log
            Log.d("Name: ", log);

            HashMap<String,String> temp=new HashMap<String, String>();
            //temp.put(FIRST_COLUMN, ts.getDatum());
            temp.put(FIRST_COLUMN, helpers.DateFormatter(ts.getDatum(),"yyyy-MM-dd","dd.MM.yyyy"));
            temp.put(SECOND_COLUMN, ts.getStreckeFmt());
            temp.put(THIRD_COLUMN, ts.getBetragFmt());
            temp.put(FOURTH_COLUMN, ts.getMengeFmt());
            temp.put(FIFTH_COLUMN, ts.getNotiz());
            listTs.add(temp);

        }


    }

}
