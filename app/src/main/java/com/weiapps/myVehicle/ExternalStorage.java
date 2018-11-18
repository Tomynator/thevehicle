package com.weiapps.myVehicle;

import java.io.File;

import android.content.Context;
import android.os.Environment;

public class ExternalStorage {

	public boolean mExternalStorageAvailable;
	public boolean mExternalStorageWriteable;
	private Context context;
	private String fileName;

	public ExternalStorage(Context context, String fileName) {
		chk_Storage();
		this.fileName = fileName;
		this.context = context;
	}

	private void chk_Storage() {

		this.mExternalStorageAvailable = false;
		this.mExternalStorageWriteable = false;
		String state = Environment.getExternalStorageState();

	
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			// We can read and write the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			// We can only read the media
			mExternalStorageAvailable = true;
			mExternalStorageWriteable = false;
		} else {
			// Something else is wrong. It may be one of many other states, but
			// all we need
			// to know is we can neither read nor write
			mExternalStorageAvailable = false;
		}

		android.util.Log.d("SD Status",state); 
		
	}

	public void createExternalStoragePrivateFile() {
	    // Create a path where we will place our private file on external
	    // storage.
		
	    File file = new File(this.context.getExternalFilesDir(null), this.fileName);

	   /*
	    try {
	        // Very simple code to copy a picture from the application's
	        // resource into the external file.  Note that this code does
	        // no error checking, and assumes the picture is small (does not
	        // try to copy it in chunks).  Note that if external storage is
	        // not currently mounted this will silently fail.
	        InputStream is = getResources().openRawResource(R.drawable.balloons);
	        OutputStream os = new FileOutputStream(file);
	        byte[] data = new byte[is.available()];
	        is.read(data);
	        os.write(data);
	        is.close();
	        os.close();
	    } catch (IOException e) {
	        // Unable to create file, likely because external storage is
	        // not currently mounted.
	        Log.w("ExternalStorage", "Error writing " + file, e);
	    }
	    */
	}

	void deleteExternalStoragePrivateFile() {
	    // Get path for the file on external storage.  If external
	    // storage is not currently mounted this will fail.
	    File file = new File(this.context.getExternalFilesDir(null), this.fileName);
	    if (file != null) {
	        file.delete();
	    }
	}

	boolean hasExternalStoragePrivateFile() {
	    
		// Get path for the file on external storage.  If external
	    // storage is not currently mounted this will fail.
	    File file = new File(this.context.getExternalFilesDir(null), this.fileName);
	    if (file != null) {
	        return file.exists();
	    }
	    
	    return (false);
	}

}
