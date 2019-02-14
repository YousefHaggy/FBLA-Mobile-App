package com.yousefhaggy.fblamobileapp;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {
    //Bar at the top of app the shows page title,
    //indicating to the user where they are on the app
    private Toolbar toolbar;
    private Fragment currentFragment;
    //Bottom nav bar that controls navigation
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        bottomNav = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //When a menu item is selected, display the corresponding page
                DisplayFrame(menuItem.getItemId());
                return true;
            }
        });
        DisplayFrame(R.id.nav_home);
    }
    @Override
    public boolean onPrepareOptionsMenu(Menu menu)
    {

        super.onPrepareOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.empty_action_bar, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return false;

    }
    @Override
    protected void onResume() {
        if(currentFragment!=null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //Replace main fragment container with new fragment
            Log.e("ss","CurrentFra");
            fragmentTransaction.detach(currentFragment);
            fragmentTransaction.attach(currentFragment);
            fragmentTransaction.commit();
        }
        super.onResume();
    }
    private void DisplayFrame(int id)
    {
        Fragment fragment=null;
        //Assign fragment based off of the inputted id
        switch(id)
        {
            case R.id.nav_home:
                fragment=new HomePage();
                setTitle("Home Page");
                break;
            case R.id.nav_search:
                fragment=new SearchCategories();
                setTitle("Search Categories");
                break;
            case R.id.nav_profile:
                fragment=new ProfilePage();
                setTitle("Profile");
                break;

        }
        if(fragment!=null)
        {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            //Replace main fragment container with new fragment
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();
            currentFragment=fragment;
        }

    }
}
