package com.yousefhaggy.fblamobileapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.chyrta.onboarder.OnboarderActivity;
import com.chyrta.onboarder.OnboarderPage;

import java.util.ArrayList;
import java.util.List;

public class OnboardingActivity extends OnboarderActivity {
    List<OnboarderPage> onboarderPages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onboarderPages = new ArrayList<OnboarderPage>();

        //Creating all the pages for onboarder
        OnboarderPage onboarderPage1 = new OnboarderPage("Welcome to Business Bud", "Your best friend when taking and preparing for official FBLA exams",R.drawable.logo_foreground);
        OnboarderPage onboarderPage2 = new OnboarderPage("Home Page", "The home page provides shortcuts to recently taken tests and their corresponding categories.",R.drawable.ic_home_onboarding);
        OnboarderPage onboarderPage3 = new OnboarderPage("Search Page", "Access 15 different FBLA events and thousands of official test questions",R.drawable.ic_search_onboarding);
        OnboarderPage onboarderPage4 = new OnboarderPage("Profile", "Track your progress and unlock achievements for taking tests and quizzes",R.drawable.ic_person_onboarding);
        OnboarderPage onboarderPage5 = new OnboarderPage("Issues?", "Click the question mark at the bottom of app pages to get help or report bugs ",R.drawable.ic_help_onboarding);

        onboarderPages.add(onboarderPage1);
        onboarderPages.add(onboarderPage2);
        onboarderPages.add(onboarderPage3);
        onboarderPages.add(onboarderPage4);
        onboarderPages.add(onboarderPage5);

        for(int i=0; i<onboarderPages.size();i++)
        {
            onboarderPages.get(i).setTitleTextSize(24);
            onboarderPages.get(i).setDescriptionTextSize(15);
            onboarderPages.get(i).setBackgroundColor(R.color.colorPrimary);
        }
        setDividerHeight(56);
        setDividerVisibility(View.GONE);
        setSkipButtonHidden();
        setFinishButtonTitle("Get Started");
        setOnboardPagesReady(onboarderPages);

    }

    @Override
    public void onFinishButtonPressed() {
        SharedPreferences sharedPref=this.getSharedPreferences("com.yousefhaggy.fblamobileapp.PREFERENCE_FILE_KEY",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("ONBOARDING_COMPLETE",true);
        editor.commit();
        finish();
    }
    @Override
    public void onBackPressed()
    {

    }
}
