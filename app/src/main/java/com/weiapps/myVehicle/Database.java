package com.weiapps.myVehicle;

/**
 * Created by Thomas on 01.03.2018.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


    public class Database extends SQLiteOpenHelper {

        // All Static variables
        // Database Version
        private static final int DATABASE_VERSION = 1;

        // Database Name
        private static final String DATABASE_NAME = "myVehicle";

        // Tankstopps table name
        private static final String TABLE_TANKSTOPPS = "tankstopps";

        // Tankstopps Table Columns names
        private static final String KEY_DATUM = "datum";
        private static final String KEY_STRECKE = "strecke";
        private static final String KEY_MENGE = "menge";
        private static final String KEY_PREIS = "preis";
        private static final String KEY_NOTIZ = "notiz";

        private static int rowsAffected;
        Context context = null;

        public Database(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);

        }

        // Creating Tables
        @Override
        public void onCreate(SQLiteDatabase db) {

            String CREATE_TABLE = "CREATE TABLE " + TABLE_TANKSTOPPS + "("
                    // + KEY_ID + " INTEGER PRIMARY KEY,"
                    + KEY_DATUM + " TEXT PRIMARY KEY,"
                    + KEY_STRECKE + " FLOAT, " //
                    + KEY_MENGE + " FLOAT, " //
                    + KEY_PREIS + " FLOAT, " //
                    + KEY_NOTIZ + " TEXT" + ")";
            db.execSQL(CREATE_TABLE);
        }

        // Upgrading database
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Drop older table if existed
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TANKSTOPPS);

            // Create tables again
            onCreate(db);
        }

        void recreateTable() {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_TANKSTOPPS);
            onCreate(db);
        }

        public static String getDbName(){
            return(DATABASE_NAME);
        }

        /**
         * All CRUD(Create, Read, Update, Delete) Operations
         */

        // Adding new Record
        boolean addTankstopp(Tankstopp tankstopp) {
            boolean retc = true;

            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_DATUM, tankstopp.getDatum()); // Datum
            values.put(KEY_STRECKE, tankstopp.getStrecke()); // Strecke
            values.put(KEY_MENGE, tankstopp.getMenge()); // Menge
            values.put(KEY_PREIS, tankstopp.getBetrag()); // Preis
            values.put(KEY_NOTIZ, tankstopp.getNotiz()); // Notiz

            // -1 ... Fehler
            if (db.insert(TABLE_TANKSTOPPS, null, values) == -1) {
                retc = false;
            } else {
                retc = true;
            }

            db.close(); // Closing database connection
            return (retc);
        }

        // Getting single Record
        Tankstopp getTankstopp(String dateISO) {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.query(TABLE_TANKSTOPPS, new String[] { KEY_STRECKE,
                            KEY_MENGE, KEY_PREIS, KEY_NOTIZ }, KEY_DATUM + "= ?",
                    new String[] { dateISO }, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();

            if (cursor.getCount() != 0) {
                Tankstopp tankstopp = new Tankstopp();
          //      Tankstopp.setDatum(datum);
                tankstopp.setStrecke(Float.valueOf(cursor.getString(0)));
                tankstopp.setMenge(Float.valueOf(cursor.getString(1)));
                tankstopp.setBetrag(Float.valueOf(cursor.getString(2)));
                tankstopp.setNotiz(cursor.getString(3));
                return tankstopp;
            } else {
                return null;
            }
        }

        // Getting single Record
        Tankstopp getPreviousTankstopp(String dateISO) {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT max(" + KEY_DATUM + ") FROM "
                    + TABLE_TANKSTOPPS + " WHERE " + KEY_DATUM + " < ? ";
            Cursor cursor = db.rawQuery(selectQuery, new String[] { dateISO });

            if (cursor != null)
                cursor.moveToFirst();

            if (!cursor.isNull(0)) {
                Tankstopp tankstopp = new Tankstopp();
                tankstopp = this.getTankstopp(cursor.getString(0));
                return tankstopp;
            } else {
                return null;
            }
        }


        Tankstopp getNextTankstopp(String dateISO) {
            SQLiteDatabase db = this.getReadableDatabase();

            String selectQuery = "SELECT min(" + KEY_DATUM + ") FROM "
                    + TABLE_TANKSTOPPS + " WHERE " + KEY_DATUM + " > ? ";
            Cursor cursor = db.rawQuery(selectQuery, new String[] { dateISO });

            if (cursor != null)
                cursor.moveToFirst();

            if (!cursor.isNull(0)) {
                Tankstopp tankstopp = new Tankstopp();
                tankstopp = this.getTankstopp(cursor.getString(0));
                return tankstopp;
            } else {
                return null;
            }
        }


        // Getting All Records
        public List<Tankstopp> getAllTankstopps() {
            List<Tankstopp> tankstoppList = new ArrayList<Tankstopp>();
            // Select All Query
            String selectQuery = "SELECT * FROM " + TABLE_TANKSTOPPS + " ORDER BY "
                    + KEY_DATUM;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    Tankstopp tankstopp = new Tankstopp();
                    // tankstopp.setID(Integer.parseInt(cursor.getString(0)));
                    tankstopp.setDatum(cursor.getString(0));
                    tankstopp.setStrecke(Float.valueOf(cursor.getString(1)));
                    tankstopp.setMenge(Float.valueOf(cursor.getString(2)));
                    tankstopp.setBetrag(Float.valueOf(cursor.getString(3)));
                    tankstopp.setNotiz(cursor.getString(4));

                    // Adding tankstopp to list
                    tankstoppList.add(tankstopp);
                } while (cursor.moveToNext());
            }

            // return tankstopp list
            return tankstoppList;
        }

        // Updating single Record
        public boolean updateTankstopp(Tankstopp tankstopp) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_DATUM, tankstopp.getDatum());
            values.put(KEY_STRECKE, tankstopp.getStrecke());
            values.put(KEY_MENGE, tankstopp.getMenge());
            values.put(KEY_PREIS, tankstopp.getBetrag());
            values.put(KEY_NOTIZ, tankstopp.getNotiz());

            // updating row
            rowsAffected = db
                    .update(TABLE_TANKSTOPPS, values, KEY_DATUM + " = ?",
                            new String[] { String.valueOf(tankstopp.getDatum()) });

            return true;
        }

        // Deleting single Record
        public int deleteTankstopp(String dateISO) {

            SQLiteDatabase db = this.getWritableDatabase();
            rowsAffected = db.delete(TABLE_TANKSTOPPS, KEY_DATUM + " = ?",
                    new String[] { dateISO });
            db.close();
            return(rowsAffected);
        }

        // Getting Records Count
        public int getTankstoppCount() {
            String countQuery = "SELECT * FROM " + TABLE_TANKSTOPPS;
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(countQuery, null);
            cursor.close();

            // return count
            return cursor.getCount();
        }

    }




