package com.yousefhaggy.fblamobileapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

// Logs bugs via Crashlytics
public class ReportBugDialog extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Fabric.with(getActivity(), new Crashlytics());
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=requireActivity().getLayoutInflater();
        View mainView=inflater.inflate(R.layout.report_bug_dialog,null);
        final TextView bugTextInput=(TextView) mainView.findViewById(R.id.bugTextInput);
        Button submitButton=(Button)mainView.findViewById(R.id.submitBugReportButton);
        submitButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
    Crashlytics.logException(new Exception(bugTextInput.getText().toString()));
                Toast.makeText(getActivity(),"Report Submitted",Toast.LENGTH_SHORT).show();
    dismiss();

            }
        });
        builder.setView(mainView).setTitle("Bug Report");
        return builder.create();
    }
}
