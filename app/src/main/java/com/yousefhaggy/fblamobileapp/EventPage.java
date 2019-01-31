package com.yousefhaggy.fblamobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class EventPage extends AppCompatActivity {
    private Toolbar toolbar;
    String[] exampleTestList={"TEST 1","TEST 2","TEST 3","TEST 4","TEST 5","TEST 6","TEST 7"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_page);
        toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ListView testListView=(ListView) findViewById(R.id.fullTestListView);
        ArrayAdapter<String> listAdapter=new ArrayAdapter<String>(this,R.layout.simple_list_item,exampleTestList);
        testListView.setAdapter(listAdapter);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(
                R.menu.empty_action_bar,menu);
    return true;
    }
}
