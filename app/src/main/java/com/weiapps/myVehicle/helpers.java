package com.weiapps.myVehicle;

import android.widget.EditText;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Thomas on 27.02.2018.
 */

public abstract class helpers {

    private static double f1;
    private static double rwert;

    public static float Round(float input, double s) {

        f1 = Math.pow(10, s);
        rwert = 5 / (f1 * 10);

        input += rwert; // z.b. 0.005
        input *= f1;
        int x = Math.round(input); // round: runden auf Integer
        input = x;
        input /= f1;

        return (input);
    }

    public static float floatOfEditText(EditText input){

        String tmpStr = input.getText().toString();

        if (tmpStr.equals("")) {
            return (0);
        } else {
            return (Float.valueOf(tmpStr).floatValue());
        }

    }

    public static String StringOfFloat(float input){

        if (input != 0) {
            return(String.valueOf(input));
        } else {
            return ("");
        }
     }


    public static String DateFormatter(String datum, String patterFrom, String patternTo ) {

        DateFormat sourceFormat = new SimpleDateFormat(patterFrom);
        Date tmpDate = null;
        try {
            tmpDate = sourceFormat.parse(datum);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateFormat targetFormat = new SimpleDateFormat(patternTo);
        return(targetFormat.format(tmpDate));
    }

    public static String today (){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return(dateFormat.format(date));
    }

}
