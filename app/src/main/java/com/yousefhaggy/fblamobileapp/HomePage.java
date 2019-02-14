package com.yousefhaggy.fblamobileapp;

import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class HomePage extends Fragment {
    List<ScoreHistoryItem> recentCategoryList = new ArrayList<>();
    List<ScoreHistoryItem> recentTestList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TestBankDatabaseHelper databaseHelper = new TestBankDatabaseHelper(getActivity());
        try {
            databaseHelper.createDatabase();
        } catch (IOException e) {
            throw new Error("cant make database");
        }
        try {
            databaseHelper.openDatabase();
        } catch (SQLException e) {
            throw e;

        }
        recentCategoryList = databaseHelper.getRecentCategories();
        recentTestList = databaseHelper.getRecentTestScores();
        databaseHelper.close();
        return inflater.inflate(R.layout.home_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.homePage);
        LayoutInflater layoutInflater = (LayoutInflater) getActivity().getLayoutInflater();
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.helpFloatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HelpDialogFragment dialogFragment = new HelpDialogFragment();
                Bundle bundle = new Bundle();
                bundle.putString("HelpContent", "Recently taken tests, their corresponding categories, and your scores appear on this page. You can tap on a category card to launch test selection for that category or tap on a test card to retake that test.");
                dialogFragment.setArguments(bundle);
                dialogFragment.show(getActivity().getSupportFragmentManager(), null);
            }
        });
        TextView emptyTextView = (TextView) view.findViewById(R.id.emptyTextView);
        if (recentTestList.size() == 0) {
            emptyTextView.setVisibility(View.VISIBLE);
            linearLayout.setVisibility(View.GONE);
        }
        for (ScoreHistoryItem s : recentCategoryList) {
            final View cardView = layoutInflater.inflate(R.layout.test_and_category_history_card, null);
            TextView categoryTextView = (TextView) cardView.findViewById(R.id.testOrCategoryTitle);
            categoryTextView.setText(s.getItemTitle());
            TextView scoreTextView = (TextView) cardView.findViewById(R.id.scoreTextView);
            DecimalFormat df = new DecimalFormat("#%");
            scoreTextView.setText(df.format(s.getItemScore()));
            TextView scoreTypeTextview = (TextView) cardView.findViewById(R.id.typeOfScoreTextView);
            scoreTypeTextview.setText("Average Score");
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String categoryName = ((TextView) cardView.findViewById(R.id.testOrCategoryTitle)).getText().toString();
                    launchSelectTestDialog(categoryName);
                }
            });
            linearLayout.addView(cardView, 1);
        }
        for (ScoreHistoryItem s : recentTestList) {
            View cardView = layoutInflater.inflate(R.layout.test_and_category_history_card, null);
            TextView categoryTextView = (TextView) cardView.findViewById(R.id.testOrCategoryTitle);
            categoryTextView.setText(s.getItemTitle());
            TextView scoreTextView = (TextView) cardView.findViewById(R.id.scoreTextView);
            DecimalFormat df = new DecimalFormat("#%");
            scoreTextView.setText(df.format(s.getItemScore()));
            TextView recentTestsHeader = (TextView) linearLayout.findViewById(R.id.recentTestsHeader);
            int index = ((ViewGroup) linearLayout).indexOfChild(recentTestsHeader);
            linearLayout.addView(cardView, index + 1);
        }
    }

    public void launchSelectTestDialog(String categoryname) {
        CategoryMenuDialogFragment categoryMenuDialogFragment = new CategoryMenuDialogFragment();
        Bundle bundle = new Bundle();
        TestBankDatabaseHelper databaseHelper = new TestBankDatabaseHelper(getActivity());
        try {
            databaseHelper.createDatabase();
        } catch (IOException e) {
            throw new Error("cant make database");
        }
        try {
            databaseHelper.openDatabase();
        } catch (SQLException e) {
            throw e;

        }
        List<String> testList = databaseHelper.getTests(categoryname);
        databaseHelper.close();
        bundle.putStringArrayList("TestList", (ArrayList) testList);
        bundle.putString("CategoryName", categoryname);
        categoryMenuDialogFragment.setArguments(bundle);
        categoryMenuDialogFragment.show(getActivity().getSupportFragmentManager(), null);
    }

    @Override
    public void setUserVisibleHint(boolean visible) {
        super.setUserVisibleHint(visible);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        Log.e("HIDDEN", "fds");
    }
}
