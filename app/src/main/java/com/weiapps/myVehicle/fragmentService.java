package com.weiapps.myVehicle;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentService extends Fragment  {

    DialogFragment FrgDatePicker = null;
    View view = null;

    private Database db = null;

    private Button btnDatum = null ;
    private Button btnSave = null;
    private Button btnDelete = null;

    private EditText Art = null;
    private EditText Betrag = null;
    private EditText Notiz = null;

    public fragmentService() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_service,
                container, false);


        //Get Argument that passed from activity in "data" key value
        String getArgument = getArguments().getString("data");

        btnDatum = (Button) view.findViewById(R.id.btn_datum);
        btnDatum.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Snackbar.make(v, "Button Datum was pressed", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                DatePickerDialog dpDialog = new DatePickerDialog(getContext());
                dpDialog.show();

                dpDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        Snackbar.make(view, "Button Datum was changed to "+String.valueOf(i), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();

                        Date date = new Date();
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(date);
                        calendar.set(Calendar.YEAR, i);// for 6 hour
                        calendar.set(Calendar.MONTH, i1);// for 0 min
                        calendar.set(Calendar.DAY_OF_MONTH, i2);// for 0 sec
                        btnDatum.setText(android.text.format.DateFormat.format("dd.MM.yyyy", calendar.getTime()));
                    }
                });
            }
        });

        //btnDatum.setText(android.text.format.DateFormat.format("dd.MM.yyyy", new java.util.Date()));
        btnDatum.setText(getArgument);

        Art = (EditText) view.findViewById(R.id.Art);
        Betrag = (EditText) view.findViewById(R.id.Betrag);
        Notiz = (EditText) view.findViewById(R.id.Notiz);


        btnSave = (Button)view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Save();
            }
        });
        btnDelete = (Button)view.findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DeleteDialog();
            }
        });








        return(view);
        //return inflater.inflate(R.layout.fragment_tankstopp, container, false);

    }


    private void Save() {
        boolean retc;

        db = new Database(getContext());
        retc = db.addTankstopp(new Tankstopp(btnDatum.getText().toString(),
                floatStrecke, floatMenge, floatBetrag, Notiz.getText().toString()));

        if (retc) {
        } else {
            retc = db.updateTankstopp(new Tankstopp(
                    btnDatum.getText().toString(),
                    floatStrecke, floatMenge, floatBetrag, Notiz.getText().toString()));
        }

        if (retc) {
            // Msg "Satz wurde gespeichert"
            Snackbar.make(this.view,  getResources().getString(R.string.Msg_06), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    private void DeleteDialog() {

        this.buttonID = R.id.btnDelete;
        // Msg "Datensatz wirklich löschen?"
        boxOKCancel(getResources().getString(R.string.Msg_04));

    }
    private void Delete() {
        boolean retc;

        db = new Database(getContext());
        String dateISO = helpers.DateFormatter(btnDatum.getText().toString(),"dd.MM.yyyy","yyyy-MM-dd");

        int rows_affected = db.deleteTankstopp(dateISO);

        if (rows_affected > 0) {
            Reset();
            // Msg "Datensatz gelöscht!";
            Snackbar.make(this.view,  getResources().getString(R.string.Msg_02), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        } else {
            // Msg "Datensatz nicht vorhanden!"
            Snackbar.make(this.view,  getResources().getString(R.string.Msg_03), Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }



}
