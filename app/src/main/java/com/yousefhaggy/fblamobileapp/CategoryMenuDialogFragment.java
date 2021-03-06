package com.yousefhaggy.fblamobileapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;

// Dialog fragment that displays the testing options for a category
// If a user choose to take a random quiz, a random quiz is started
// Otherwise, a full test selection dialog is called
public class CategoryMenuDialogFragment extends DialogFragment {
    ArrayList<String> testList = new ArrayList<>();
    private String categoryName;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        String[] choices = {"Random 10 Question Quiz", "Official full-length test"};
        categoryName = getArguments().getString("CategoryName");
        testList = getArguments().getStringArrayList("TestList");
        builder.setTitle("Select Test Type").setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 1) {
                    SelectTestDialogFragment dialogFragment = new SelectTestDialogFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("TestList", testList);
                    bundle.putString("CategoryName", categoryName);
                    dialogFragment.setArguments(bundle);
                    dialogFragment.show(getActivity().getSupportFragmentManager(), null);
                } else {
                    Intent launchTest = new Intent(getActivity(), TestPage.class);
                    launchTest.putExtra("TestName", "Random " + categoryName + " Quiz");
                    launchTest.putExtra("CategoryName", categoryName);
                    startActivity(launchTest);
                }
            }
        });
        return builder.create();
    }
}
