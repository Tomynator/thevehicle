package com.weiapps.myVehicle;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentStatistik extends Fragment  {

    DialogFragment FrgDatePicker = null;
    View view = null;

    public fragmentStatistik() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_statistik,
                container, false);




        return(view);
        //return inflater.inflate(R.layout.fragment_tankstopp, container, false);

    }


}
