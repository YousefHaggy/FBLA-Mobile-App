package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.crashlytics.android.Crashlytics;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import io.fabric.sdk.android.Fabric;

// This file mainly facilities navigation between app fragments and
// other navigation events. It controls the bottom app bar
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setIcon(R.drawable.logo_no_background);
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
        //If first time, launch onboarding
        SharedPreferences sharedPref= this.getSharedPreferences("com.yousefhaggy.fblamobileapp.PREFERENCE_FILE_KEY", Context.MODE_PRIVATE);
        boolean hasOnboarded=sharedPref.getBoolean("ONBOARDING_COMPLETE",false);
        if(!hasOnboarded) {
            Intent launchOnboarding = new Intent(this, OnboardingActivity.class);
            startActivity(launchOnboarding);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

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
        if (currentFragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //Replace main fragment container with new fragment
            Log.e("ss", "CurrentFra");
            fragmentTransaction.detach(currentFragment);
            fragmentTransaction.attach(currentFragment);
            fragmentTransaction.commit();
        }
        super.onResume();
    }

    private void DisplayFrame(int id) {
        Fragment fragment = null;
        //Assign fragment based off of the inputted id
        switch (id) {
            case R.id.nav_home:
                fragment = new HomePage();
                setTitle("Home Page");
                break;
            case R.id.nav_search:
                fragment = new SearchCategories();
                setTitle("Search Categories");
                break;
            case R.id.nav_profile:
                fragment = new ProfilePage();
                setTitle("Profile");
                break;

        }
        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            //Replace main fragment container with new fragment
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.commit();
            currentFragment = fragment;
        }

    }
}
