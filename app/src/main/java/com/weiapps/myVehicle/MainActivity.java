package com.weiapps.myVehicle;

import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager frgMan = getFragmentManager() ;
    private int active_fragment = 0;
    private FloatingActionButton fab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(active_fragment== R.id.nav_tankstopp) {
                    fab.hide();
                    fragmentTankstopp frgTs = new fragmentTankstopp();
                    //FragmentManager frgMan = getFragmentManager() ;

                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("data", helpers.today());//put string, int, etc in bundle with a key value
                    frgTs.setArguments(data);//Finally set argument bundle to fragment

                    frgMan.beginTransaction().replace(R.id.layout_main_fragment, frgTs, frgTs.getTag()).addToBackStack(null).commit();
                } else if (active_fragment== R.id.nav_service){
                    fragmentService frgSrv = new fragmentService();
                    //FragmentManager frgMan = getFragmentManager() ;

                    Bundle data = new Bundle();//Use bundle to pass data
                    data.putString("data", helpers.today());//put string, int, etc in bundle with a key value
                    frgSrv.setArguments(data);//Finally set argument bundle to fragment

                    frgMan.beginTransaction().replace(R.id.layout_main_fragment, frgSrv, frgSrv.getTag()).addToBackStack(null).commit();
                } else {
                   Snackbar.make(view, "Do tamma no wos " + String.valueOf(active_fragment), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Start mit Tankstopps
        fragmentTankstoppList frgTs = new fragmentTankstoppList();
        //FragmentManager frgMan = getFragmentManager() ;
        frgMan.beginTransaction().replace(R.id.layout_main_fragment, frgTs, frgTs.getTag()).addToBackStack(null).commit();
        active_fragment= R.id.nav_tankstopp;
    }

    @Override
    public void onBackPressed() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.menue_backup){
            manage_Backup("backup");

        } else if (id == R.id.menue_restore){
            manage_Backup("restore");
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        this.active_fragment = id;
        fab.show();

        if (id == R.id.nav_tankstopp) {

            fragmentTankstoppList frgTs = new fragmentTankstoppList();
            //FragmentManager frgMan = getFragmentManager() ;
            frgMan.beginTransaction().replace(R.id.layout_main_fragment, frgTs, frgTs.getTag()).addToBackStack(null).commit();

        } else if (id == R.id.nav_service) {

            fragmentServiceList srvFrg = new fragmentServiceList();
            //FragmentManager frgMan = getFragmentManager() ;
            frgMan.beginTransaction().replace(R.id.layout_main_fragment, srvFrg, srvFrg.getTag()).addToBackStack(null).commit();

        } else if (id == R.id.nav_statistics) {

            fragmentStatistik frgStat = new fragmentStatistik();
            //FragmentManager frgMan = getFragmentManager() ;
            frgMan.beginTransaction().replace(R.id.layout_main_fragment, frgStat, frgStat.getTag()).addToBackStack(null).commit();

        } else if (id == R.id.nav_settings) {

            fragmentSettings frgSetgs = new fragmentSettings();
            //FragmentManager frgMan = getFragmentManager() ;
            frgMan.beginTransaction().replace(R.id.layout_main_fragment, frgSetgs, frgSetgs.getTag()).addToBackStack(null).commit();

        } else if (id == R.id.nav_close) {
               finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    void manage_Backup(String method){

        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            Log.d("", state);
        }

        // File "from"
        String dbName = Database.getDbName();
        File from = getApplicationContext().getDatabasePath(dbName);

        if(!from.isFile()) {
            boxOK(from.getAbsolutePath() + " not found or not a file");
            return;
        }

        // File "to": external and private
        File to = null;
        File bernd = getApplicationContext().getFilesDir();
        Log.d("", bernd.getAbsolutePath());

        if(bernd.exists()){

            String fileName = bernd.getAbsoluteFile()+ File.separator + Database.getDbName();
            to = new File(fileName);
            if(!to.exists()){
                boolean retc ;
                try {
                    retc = to.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{
                Log.d("adf", "to exists");
            }

        }else{
            boolean retc = bernd.mkdirs();
            if(retc){
                Log.d("adsf","yes");
            }
            else{
                Log.d("adsf","no");
            }
        }



        if (method == "backup") {

            try {
                copyFileUsingChannels(from, to);
                String tmpString = getResources().getString(R.string.Msg_08);
                tmpString = tmpString.replace("#dbName#", dbName);
                boxOK(tmpString + "\nPfad: " + bernd.getAbsolutePath());
            } catch (IOException ex) {
                // TODO Auto-generated catch block
                ex.printStackTrace();
            }

        } else {
            if (method == "restore") {
                try {
                    copyFileUsingChannels(to, from);
                    String tmpString = getResources().getString(R.string.Msg_09);
                    tmpString = tmpString.replace("#dbName#", dbName);
                    boxOK(tmpString + "\nPfad: " + bernd.getAbsolutePath());
                } catch (IOException ex) {
                    // TODO Auto-generated catch block
                    ex.printStackTrace();
                }

            } else {
                boxOK(getResources().getString(R.string.Msg_07) + " " + method);
            }
        }

        Toast.makeText(getApplicationContext(), dbName, Toast.LENGTH_SHORT).show();

    }

    private static void copyFileUsingChannels(File src, File dst)
            throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(src).getChannel();
            outputChannel = new FileOutputStream(dst).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (inputChannel != null)
                inputChannel.close();
            if (outputChannel != null)
                outputChannel.close();
        }
    }

    private void boxOK(String Message) {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setTitle(this.getTitle());
        builder1.setMessage(Message);
        builder1.setCancelable(true);
        builder1.setIcon(R.drawable.ic_vehicle_old);

        builder1.setNeutralButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

}
