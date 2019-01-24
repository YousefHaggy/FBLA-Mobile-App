package com.yousefhaggy.fblamobileapp;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {
    //Bar at the top of app the shows page title,
    //indicating to the user where they are on the app
    private ActionBar toolbar;
    //Bottom nav bar that controls navigation
    private BottomNavigationView bottomNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNav = (BottomNavigationView) findViewById(R.id.navigationView);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //When a menu item is selected, display the corresponding page
                DisplayFrame(menuItem.getItemId());
                return true;
            }
        });
    }
    private void DisplayFrame(int id)
    {
        Fragment fragment=null;
        //Assign fragment based off of the inputted id
        switch(id)
        {
            case R.id.nav_home:
                fragment=new HomePage();
                break;
            case R.id.nav_search:
                fragment=new SearchEvents();
                break;

        }
        if(fragment!=null)
        {
            FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
            //Replace main fragment container with new fragment
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.commit();
        }

    }
}
