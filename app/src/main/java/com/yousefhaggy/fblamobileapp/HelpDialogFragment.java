package com.yousefhaggy.fblamobileapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HelpDialogFragment extends DialogFragment {
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=requireActivity().getLayoutInflater();
        View mainView=inflater.inflate(R.layout.help_dialog,null);
        String helpContent=getArguments().getString("HelpContent");
        TextView helpContentTextView=(TextView) mainView.findViewById(R.id.helpContentTextView);
        helpContentTextView.setText(helpContent);
        Button termsOfUseButton=(Button)mainView.findViewById(R.id.tosButton);
        termsOfUseButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                TermsOfUseDialog dialog= new TermsOfUseDialog();
                dialog.show(getActivity().getSupportFragmentManager(),null);
                dismiss();
            }
        });
        Button reportBugButton=(Button) mainView.findViewById(R.id.reportBugButton);
        reportBugButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                ReportBugDialog dialog= new ReportBugDialog();
                dialog.show(getActivity().getSupportFragmentManager(),null);
                dismiss();
            }
        });
        builder.setView(mainView).setTitle("Page Info");
        return builder.create();
    }
}
