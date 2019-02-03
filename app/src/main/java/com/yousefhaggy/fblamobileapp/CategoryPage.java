package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class CategoryPage extends AppCompatActivity {
    private Toolbar toolbar;
    String[] testList;
    private String categoryName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);
        categoryName =getIntent().getStringExtra("CategoryName");
        getTestList();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(categoryName);
        ListView testListView = (ListView) findViewById(R.id.fullTestListView);
        final ArrayAdapter<String> listAdapter = new ArrayAdapter<String>(this, R.layout.simple_list_item, testList);
        testListView.setAdapter(listAdapter);
        final Context context = this;
        testListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent launchTest = new Intent(context, TestPage.class);
                launchTest.putExtra("TestName",testList[i]);
                startActivity(launchTest);

            }
        });

    }
    public void getTestList(){
        TestBankDatabaseHelper databaseHelper=new TestBankDatabaseHelper(this);
        try{
            databaseHelper.openDatabase();
        }catch(SQLException e)
        {
            throw e;
        }
        List<String> dbTestList=databaseHelper.getTests(categoryName);
        databaseHelper.close();
        testList=new String[dbTestList.size()];
        testList=dbTestList.toArray(testList);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(
                R.menu.empty_action_bar, menu);
        return true;
    }
    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
