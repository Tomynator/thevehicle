package com.weiapps.myVehicle;


import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentService extends Fragment  {

    DialogFragment FrgDatePicker = null;
    View view = null;

    public fragmentService() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_service,
                container, false);



        return(view);
        //return inflater.inflate(R.layout.fragment_tankstopp, container, false);

    }


}
