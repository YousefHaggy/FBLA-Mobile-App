package com.yousefhaggy.fblamobileapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Spinner;

public class SelectTestDialogFragment extends DialogFragment {
    private String selectedTest;
    private  boolean generateQuiz;
    private String categoryName;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater layoutInflater=getActivity().getLayoutInflater();
        categoryName=getArguments().getString("CategoryName");
        final View view=layoutInflater.inflate(R.layout.select_test_dialog,null);
        final Spinner spinner= (Spinner) view.findViewById(R.id.testSelectSpinner);
        final CheckBox generateQuizCheckBox=(CheckBox) view.findViewById(R.id.checkBoxGenerateQuiz);
        generateQuizCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                {
                    spinner.setEnabled(false);
                    generateQuiz=true;
                }
                else{
                    spinner.setEnabled(true);
                    generateQuiz=false;
                }
            }
        });
        ArrayAdapter<String> spinnerAdapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,getArguments().getStringArrayList("TestList"));
        spinnerAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedTest=spinner.getAdapter().getItem(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        builder.setView(view).setPositiveButton("Begin", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(!generateQuiz) {
                    Intent launchTest = new Intent(getActivity(), TestPage.class);
                    launchTest.putExtra("TestName", selectedTest);
                    launchTest.putExtra("CategoryName",categoryName);
                    startActivity(launchTest);
                }
                else{
                    Intent launchTest=new Intent(getActivity(),TestPage.class);
                    launchTest.putExtra("TestName","Random "+categoryName+" Quiz");
                    launchTest.putExtra("CategoryName",categoryName);
                    startActivity(launchTest);
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setTitle("Test Selection");
        return builder.create();
    }
}
