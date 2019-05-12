package com.yousefhaggy.fblamobileapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

// Confirm dialog for submitting a test
public class ConfirmTestSubmitDialog extends DialogFragment {
    public interface ConfirmTestSubmitInterface{
        public void onConfirmSubmit();

    }
    ConfirmTestSubmitInterface listener;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure you want to submit this test?").setPositiveButton("Confirm",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog,int id)
            {
                listener.onConfirmSubmit();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setTitle("Confirm Submit");
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener=(ConfirmTestSubmitInterface) context;
    }
}
