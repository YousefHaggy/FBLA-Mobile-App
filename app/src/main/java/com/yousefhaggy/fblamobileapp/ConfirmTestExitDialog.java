package com.yousefhaggy.fblamobileapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

public class ConfirmTestExitDialog extends DialogFragment {
    public interface ConfirmTestExitInterface{
        public void onConfirmExit();

    }
    ConfirmTestExitInterface listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to quit this test?").setPositiveButton("Confirm",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int id)
            {
                listener.onConfirmExit();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setTitle("Confirm Exit");
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(ConfirmTestExitInterface) context;
    }
}