package com.weiapps.myVehicle;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;


public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {

	private int mYear;
	private int mMonth;
	private int mDay;
	
	public static String dateAsString;
 

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getContext(), this, year, month, day);
	}

	
	public void onDateSet(DatePicker view, int year, int month, int day) {
		// Do something with the date chosen by the user
		mYear = year;
		mMonth = month;
		mDay = day;

		StringBuilder strB = new StringBuilder();
		strB.append(mYear).append("-");
		if (mMonth + 1 < 10) {
			strB.append("0");
		}
		strB.append(mMonth + 1).append("-"); // Month is 0 based so add 1
		if (mDay < 10) {
			strB.append("0");
		}
		strB.append(mDay);
		dateAsString = strB.toString();
		
		DatePickerDialogListener activity = (DatePickerDialogListener) getActivity();
		activity.onFinishDialog(dateAsString);
		this.dismiss(); 	
		
			}
	
	public interface DatePickerDialogListener {
	    void onFinishDialog(String dateAsString);
	}

	
	public void setStartDate(){
		
	}
	
	
}