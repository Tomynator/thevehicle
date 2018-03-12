package com.weiapps.myVehicle;


import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.style.TtsSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragmentTankstopp extends Fragment  {
    static final String strEmpty = "";

    private Database db = null;

    DialogFragment FrgDatePicker = null;
    View view = null;
    private Button btnDatum = null ;
    private Button btnSave = null;
    private Button btnDelete = null;

    private EditText Strecke = null;
    private EditText Betrag = null;
    private EditText Menge = null;
    private EditText Notiz = null;

    private TextView BetragProVol = null;
    private TextView Verbrauch = null;
    private TextView KostenProStrecke = null;

    String strTmp = null;

    private float floatStrecke = 0;
    private float floatMenge = 0;
    private float floatBetrag = 0;
    private float floatBetragProVolEh = 0;
    private float floatVerbrauch = 0;
    private float floatKostenProStrecke = 0;

    private Tankstopp tankstopp = null;
    private int buttonID ;

    public fragmentTankstopp() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tankstopp,
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

        Strecke = (EditText) view.findViewById(R.id.Strecke);
        Betrag = (EditText) view.findViewById(R.id.Betrag);
        Menge = (EditText) view.findViewById(R.id.Menge);
        Notiz = (EditText) view.findViewById(R.id.Notiz);

        BetragProVol = (TextView) view.findViewById(R.id.BetragProVol);
        Verbrauch = (TextView) view.findViewById(R.id.Verbrauch);
        KostenProStrecke = (TextView) view.findViewById(R.id.KostenProStrecke);

        //Strecke.setImeOptions(EditorInfo.IME_ACTION_DONE);
        Strecke.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        Strecke.setOnEditorActionListener(new TextView.OnEditorActionListener() {
           @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    Betrag.requestFocus();
                    Berechne();
                    return true;
                }
                return false;
            }
        });
        Strecke.requestFocus();

        Betrag.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        Betrag.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT){
                    Menge.requestFocus();
                    Berechne();
                    return true;
                }
                return false;
            }
        });

        Menge.setImeOptions(EditorInfo.IME_ACTION_DONE);
        Menge.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE){
                    Notiz.requestFocus();
                    Berechne();
                    return true;
                }
                return false;
            }
        });

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

        getTs();



        return(view);
        //return inflater.inflate(R.layout.fragment_tankstopp, container, false);
    }

    private void getTs() {

        db = new Database(getContext());
        String dateISO = helpers.DateFormatter(btnDatum.getText().toString(),"dd.MM.yyyy","yyyy-MM-dd");
        tankstopp = db.getTankstopp(dateISO);

        if (tankstopp != null) {
            RecToMap();
            Berechne();
        }
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


    private void RecToMap() {

        if (tankstopp != null) {

            //btnDatum.setText(tankstopp.getDatum());
            floatStrecke = tankstopp.getStrecke();
            floatMenge = tankstopp.getMenge();
            floatBetrag = tankstopp.getBetrag();
            Notiz.setText(tankstopp.getNotiz());

            Strecke.setText(helpers.StringOfFloat(floatStrecke));
            Menge.setText(helpers.StringOfFloat(floatMenge));
            Betrag.setText(helpers.StringOfFloat(floatBetrag));

        }

    }

    private void Berechne() {

        getFloatInputs();

        BetragProVol.setText(strEmpty);
        Verbrauch.setText(strEmpty);
        KostenProStrecke.setText(strEmpty);

        // Berechnung Preis / Liter
        if (floatMenge != 0 && floatBetrag != 0 ) {
            floatBetragProVolEh = helpers.Round((floatBetrag / floatMenge), 3);
            BetragProVol.setText(String.valueOf(floatBetragProVolEh));
        }

        // Berechnung Verbrauch
        if (floatMenge != 0 && floatStrecke != 0) {
            floatVerbrauch = helpers.Round(((floatMenge * 100) / floatStrecke), 2);
            Verbrauch.setText(String.valueOf(floatVerbrauch));
        }

        // Berechnung Kosten pro Strecke
        if (floatBetrag != 0 && floatStrecke != 0) {
            floatKostenProStrecke = helpers.Round(((floatBetrag * 100) / floatStrecke), 2);
            KostenProStrecke.setText(String.valueOf(floatKostenProStrecke));
        }


    }

    private void getFloatInputs(){
        floatStrecke = helpers.floatOfEditText(Strecke);
        floatMenge = helpers.floatOfEditText(Menge);
        floatBetrag = helpers.floatOfEditText(Betrag);
    }

    public void Reset() {

        Strecke.setText(strEmpty);
        Menge.setText(strEmpty);
        Betrag.setText(strEmpty);
        Notiz.setText(strEmpty);

        floatStrecke = 0;
        floatMenge = 0;
        floatBetrag = 0;

        BetragProVol.setText(strEmpty);
        Verbrauch.setText(strEmpty);
        KostenProStrecke.setText(strEmpty);

        Strecke.requestFocus();
    }

    private void boxOKCancel(String Message) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
        builder1.setTitle(R.string.app_name);
        builder1.setMessage(Message);
        builder1.setCancelable(true);
        builder1.setIcon(R.drawable.ic_zapfi);

        builder1.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        OkCancelDialogHandler();
                        dialog.cancel();
                    }
                });
        builder1.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    private void OkCancelDialogHandler() {

        switch (this.buttonID) {

            case R.id.btnDelete:
                Delete();
                break;

            default:
                break;
        }

    }

}
