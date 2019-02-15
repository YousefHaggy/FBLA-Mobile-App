package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TestPage extends AppCompatActivity implements ConfirmTestExitDialog.ConfirmTestExitInterface, ConfirmTestSubmitDialog.ConfirmTestSubmitInterface {
    List<Question> questionList = new ArrayList<>();
    RecyclerView testRecyclerView;
    String testName;
    String categoryName;
    Button submitTest;
    Toolbar toolbar;
    double percentage;
    boolean testSubmitted;
    QuestionRecyclerViewAdapter testRecyclerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        testName = getIntent().getStringExtra("TestName");
        categoryName = getIntent().getStringExtra("CategoryName");
        setTitle(testName);
        getQuestionList();
        testRecyclerView = (RecyclerView) findViewById(R.id.TestRecyclerView);
        submitTest = (Button) findViewById(R.id.submitTestButton);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        testRecyclerView.setLayoutManager(llm);

        testRecyclerViewAdapter = new QuestionRecyclerViewAdapter(questionList);

        testRecyclerView.setAdapter(testRecyclerViewAdapter);

        submitTest.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmTestSubmitDialog dialog = new ConfirmTestSubmitDialog();
                dialog.show(getSupportFragmentManager(), null);
            }
        });
        Button shareButton= (Button) findViewById(R.id.shareButton);
        shareButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecimalFormat df = new DecimalFormat("#%");
                //Fixes errors with the twitter URL
                if(categoryName.contains("&"))
                    categoryName=categoryName.replace("&","and");
                String shareBody="I just scored "+df.format(percentage)+"25 on an FBLA "+categoryName+" Test! ";
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/intent/tweet?text="+shareBody));
                startActivity(browserIntent);

            }
        });
    }

    public void onConfirmSubmit() {
        submitTest();
    }

    public void submitTest() {
        int wrong = 0;
        for (int i = 0; i < questionList.size(); i++) {
            if (!questionList.get(i).answer.equals(questionList.get(i).getSelectedChoice())) {
                questionList.get(i).isWrong = true;
                wrong++;
            }
            testRecyclerViewAdapter.notifyItemChanged(i);
        }
        testRecyclerViewAdapter.testSubmitted = true;
        testRecyclerView.scrollToPosition(0);
        //testRecyclerView.smoothScrollToPosition(0);
        submitTest.setVisibility(View.GONE);
         percentage = (questionList.size() - wrong * 1.0) / questionList.size();
        TextView testPercentage = (TextView) findViewById(R.id.testPercentage);
        TextView testScore = (TextView) findViewById(R.id.testScore);
        Button shareButton = (Button) findViewById(R.id.shareButton);
        shareButton.setVisibility(View.VISIBLE);
        testScore.setText("You missed " + wrong + " questions");
        testScore.setVisibility(View.VISIBLE);
        DecimalFormat df = new DecimalFormat("#%");
        testPercentage.setText("Your score: " + df.format(percentage));
        testPercentage.setVisibility(View.VISIBLE);
        int exp = (questionList.size() - wrong) * 2;
        updateTestScoreHistory(percentage, exp);
        testSubmitted = true;
    }

    public void getQuestionList() {
        TestBankDatabaseHelper databaseHelper = new TestBankDatabaseHelper(this);
        try {
            databaseHelper.openDatabase();
        } catch (SQLException e) {
            throw e;
        }
        if (testName.contains("Random")) {
            String categoryName = getIntent().getStringExtra("CategoryName");
            questionList = databaseHelper.getRandomQuizQuestions(categoryName);
        } else {
            questionList = databaseHelper.getTestQuestions(testName);

        }
        databaseHelper.close();
    }

    public void updateTestScoreHistory(double score, int exp) {
        TestBankDatabaseHelper databaseHelper = new TestBankDatabaseHelper(this);
        try {
            databaseHelper.openDatabase();
        } catch (SQLException e) {
            throw e;
        }
        if (testName.contains("Random")) {
            String categoryName = getIntent().getStringExtra("CategoryName");
            questionList = databaseHelper.getRandomQuizQuestions(categoryName);
        } else {
            questionList = databaseHelper.getTestQuestions(testName);
        }
        databaseHelper.updateTestScoreHistory(testName, categoryName, score);
        databaseHelper.updateLevel(exp);
        databaseHelper.close();
    }

    @Override
    public void onConfirmExit() {
        finish();
    }

    @Override
    public void onBackPressed() {
        if (!testSubmitted) {
            ConfirmTestExitDialog confirmTestExitDialog = new ConfirmTestExitDialog();
            confirmTestExitDialog.show(getSupportFragmentManager(), null);
        } else {
            finish();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
