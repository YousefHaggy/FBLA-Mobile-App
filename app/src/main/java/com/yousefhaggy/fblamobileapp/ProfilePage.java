package com.yousefhaggy.fblamobileapp;

import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProfilePage extends Fragment {
    LevelInfo levelInfo;
    TextView levelTextView;
    RecyclerView achievementRecyclerView;
    AchievementRecyclerViewAdapter achievementRecyclerViewAdapter;
    ProgressBar levelProgressBar;
    Map<String,Integer> achievementStats;
    List<Achievement> achievementList=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        TestBankDatabaseHelper databaseHelper = new TestBankDatabaseHelper(getActivity());
        try {
            databaseHelper.createDatabase();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            databaseHelper.openDatabase();
        } catch (SQLException e) {
            throw e;
        }
        levelInfo=databaseHelper.getLevelInfo();
        achievementStats=databaseHelper.getAchievementStats();
        databaseHelper.close();
        achievementList.clear();
        achievementList.add(new Achievement("Take 5 tests",achievementStats.get("NumberOfTestsTaken"),5));
        achievementList.add(new Achievement("Take 10 tests",achievementStats.get("NumberOfTestsTaken"),10));
        achievementList.add(new Achievement("Take 50 tests",achievementStats.get("NumberOfTestsTaken"),50));
        achievementList.add(new Achievement("Take 100 tests",achievementStats.get("NumberOfTestsTaken"),100));
        achievementList.add(new Achievement("Score 70% on a test or quiz",achievementStats.get("HighestTestScore")>=70));
        achievementList.add(new Achievement("Score 80% on a test or quiz",achievementStats.get("HighestTestScore")>=80));
        achievementList.add(new Achievement("Score 90% on a test or quiz",achievementStats.get("HighestTestScore")>=90));
        achievementList.add(new Achievement("Score 100% on a test or quiz",achievementStats.get("HighestTestScore")==100));
        achievementList.add(new Achievement("Take tests from 5 different categories",achievementStats.get("NumberOfCategories"),5));
        achievementList.add(new Achievement("Take tests from 10 different categories",achievementStats.get("NumberOfCategories"),10));
        achievementList.add(new Achievement("Take tests from 20 different categories",achievementStats.get("NumberOfCategories"),20));
        return inflater.inflate(R.layout.profile_page, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        levelTextView=(TextView) view.findViewById(R.id.levelTextView);
        levelTextView.setText(levelInfo.getLevel()+"");
        levelProgressBar=(ProgressBar) view.findViewById(R.id.levelProgressBar);
        levelProgressBar.setMax(levelInfo.getMaxExp());
        levelProgressBar.setProgress(levelInfo.getCurrentExp());
        achievementRecyclerView=(RecyclerView) view.findViewById(R.id.achievementRecyclerView);
        achievementRecyclerViewAdapter=new AchievementRecyclerViewAdapter(achievementList);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        achievementRecyclerView.setLayoutManager(linearLayoutManager);
        achievementRecyclerView.setAdapter(achievementRecyclerViewAdapter);
    }
}
