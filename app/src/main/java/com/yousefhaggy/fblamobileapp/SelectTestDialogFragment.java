package com.yousefhaggy.fblamobileapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

// Dialog for selecting from list of pre-made tests
public class SelectTestDialogFragment extends DialogFragment {
    private String selectedTest;
    private boolean generateQuiz;
    private String categoryName;
    String[] testList;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ContextThemeWrapper cw = new ContextThemeWrapper(getActivity(), R.style.AlertDialogTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(cw);
        categoryName = getArguments().getString("CategoryName");
        testList = new String[getArguments().getStringArrayList("TestList").size()];
        testList = getArguments().getStringArrayList("TestList").toArray(testList);
        selectedTest = testList[0];
        builder.setTitle("Select a Test").setSingleChoiceItems(testList, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                selectedTest = testList[i];
            }
        }).setPositiveButton("Begin", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent launchTest = new Intent(getActivity(), TestPage.class);
                launchTest.putExtra("TestName", selectedTest);
                launchTest.putExtra("CategoryName", categoryName);
                startActivity(launchTest);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
