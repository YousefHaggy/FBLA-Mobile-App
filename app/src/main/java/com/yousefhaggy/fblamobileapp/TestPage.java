package com.yousefhaggy.fblamobileapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class TestPage extends AppCompatActivity {
    List<Question> questionList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);
        RecyclerView testRecyclerView=(RecyclerView) findViewById(R.id.TestRecyclerView);
        LinearLayoutManager llm=new LinearLayoutManager(this);
        testRecyclerView.setLayoutManager(llm);
        String[] choices={"An apple","NO"};
        questionList.add(new Question("What is life?",choices,"NO"));
        String[] choices2={"An orange","NO"};
        questionList.add(new Question("What is 2+2?",choices2,"NO"));
        Log.e("choices ",questionList.get(0).question);
        QuestionRecyclerViewAdapter questionRecyclerView=new QuestionRecyclerViewAdapter(questionList);
        testRecyclerView.setAdapter(questionRecyclerView);
    }
}
