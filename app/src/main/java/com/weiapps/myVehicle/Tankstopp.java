package com.weiapps.myVehicle;

import java.text.DecimalFormat;

/**
 * Created by Thomas on 01.03.2018.
 */

public  class Tankstopp {

    int _id;
    String _datum;
    float _strecke;
    float _menge;
    float _betrag;
    String _notiz;

    // Empty constructor
    public Tankstopp() {
    }

    // constructor
    public Tankstopp(String _datum, float _strecke, float _menge, float _betrag, String _notiz) {

        this._datum = helpers.DateFormatter(_datum, "dd.MM.yyyy", "yyyy-MM-dd");
        this._strecke = _strecke;
        this._menge = _menge;
        this._betrag = _betrag;
        this._notiz = _notiz;
    }

    // getting ID
    public int getID() {
        return this._id;
    }

    // setting id
    public void setID(int id) {
        this._id = id;
    }

    // getting name
    public String getDatum() {
        return this._datum;
    }

    // setting name
    public void setDatum(String datum) {
        this._datum = datum;
    }

    // getting strecke
    public float getStrecke() {
        return this._strecke;
    }
    public String getStreckeFmt() {
        DecimalFormat fmt = new DecimalFormat("#,##0.0");
        return fmt.format(this._strecke);
    }

    // setting strecke
    public void setStrecke(float strecke) {
        this._strecke = strecke;
    }

    // getting menge
    public float getMenge() {
        return this._menge;
    }
    public String getMengeFmt() {
        DecimalFormat fmt = new DecimalFormat("##0.00");
        return fmt.format(this._menge);
    }
    // setting menge
    public void setMenge(float menge) {
        this._menge = menge;
    }

    // getting preis
    public float getBetrag() {
        return this._betrag;
    }
    public String getBetragFmt() {
        DecimalFormat fmt = new DecimalFormat("##0.00");
        return fmt.format(this._betrag);
    }
    // setting preis
    public void setBetrag(float betrag) {
        this._betrag = betrag;
    }

    // getting preis
    public String getNotiz() {
        return this._notiz;
    }

    // setting preis
    public void setNotiz(String notiz) {
        this._notiz = notiz;
    }


}
