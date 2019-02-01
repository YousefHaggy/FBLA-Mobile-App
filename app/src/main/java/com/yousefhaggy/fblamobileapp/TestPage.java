package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class TestPage extends AppCompatActivity {
    List<Question> questionList = new ArrayList<>();
    RecyclerView testRecyclerView;
    Button submitTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);
        testRecyclerView = (RecyclerView) findViewById(R.id.TestRecyclerView);
        submitTest= (Button)findViewById(R.id.submitTestButton);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        testRecyclerView.setLayoutManager(llm);
        int quesNum=1;
        for (int i = 0; i < 5; i++) {
            String[] choices = {"An apple", "NO"};
            questionList.add(new Question(quesNum+". What is life?", choices, "NO"));
            quesNum++;
            String[] choices2 = {"An orange", "NO"};
            questionList.add(new Question(quesNum+". What is 2+2?", choices2, "NO"));
            quesNum++;
        }
        for(Question q: questionList)
        Log.e("sdfdsf",q.question+"");
        final QuestionRecyclerViewAdapter testRecyclerViewAdapter = new QuestionRecyclerViewAdapter(questionList);

        testRecyclerView.setAdapter(testRecyclerViewAdapter);

        submitTest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
               for(int i=0; i<questionList.size();i++)
               {
                   if(!questionList.get(i).answer.equals(questionList.get(i).getSelectedChoice()))
                   {
                        questionList.get(i).isWrong=true;
                   }
                   testRecyclerViewAdapter.notifyItemChanged(i);
               }
               testRecyclerViewAdapter.testSubmitted=true;
               testRecyclerView.smoothScrollToPosition(0);
               submitTest.setVisibility(View.GONE);
            }
        });
    }

}
