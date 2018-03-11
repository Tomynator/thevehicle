package com.weiapps.myVehicle;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

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



}
