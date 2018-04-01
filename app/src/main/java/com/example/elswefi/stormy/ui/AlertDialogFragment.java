package com.example.elswefi.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.elswefi.stormy.R;

/**
 * Created by elswe on 29-Mar-18 At 3:43 AM.
 */

public class AlertDialogFragment extends DialogFragment{
//    private String dialogTitle ;
//    private String dialogMessage;
//    private String dialogButtonText;
//
//    public void setDialogTitle(String dialogTitle) {
//        this.dialogTitle = dialogTitle;
//    }
//
//    public void setDialogMessage(String dialogMessage) {
//        this.dialogMessage = dialogMessage;
//    }
//
//    public void setDialogButtonText(String dialogButtonText) {
//        this.dialogButtonText = dialogButtonText;
//    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog
                .Builder(context)
                .setTitle(R.string.errorTitle)
                .setMessage(R.string.dialogMessage)
                .setPositiveButton(R.string.dialog_button_text, null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
