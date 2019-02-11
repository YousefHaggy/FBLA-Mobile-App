package com.yousefhaggy.fblamobileapp;

import android.database.SQLException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

public class ProfilePage extends Fragment {
    LevelInfo levelInfo;
    TextView levelTextView;
    ProgressBar levelProgressBar;
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
    }
}
