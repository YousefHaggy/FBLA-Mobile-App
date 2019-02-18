package com.yousefhaggy.fblamobileapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.ContextThemeWrapper;
// A simple confirm dialog for when a user clicks on a previously taken test
// in test history. If users, select Yes, they are sent to a new instance of that test
public class ConfirmTestRetakeDialog extends DialogFragment {
    private String selectedTest;
    private String categoryName;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        ContextThemeWrapper cw = new ContextThemeWrapper(getActivity(), R.style.AlertDialogTheme);
        AlertDialog.Builder builder = new AlertDialog.Builder(cw);
        selectedTest=getArguments().getString("TestName");
        categoryName=getArguments().getString("CategoryName");
        builder.setTitle("Take this test again?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent launchTest = new Intent(getActivity(), TestPage.class);
                launchTest.putExtra("TestName", selectedTest);
                launchTest.putExtra("CategoryName", categoryName);
                startActivity(launchTest);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
