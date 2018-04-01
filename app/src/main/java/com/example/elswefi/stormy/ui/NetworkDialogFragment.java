package com.example.elswefi.stormy.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;

import com.example.elswefi.stormy.R;

/**
 * Created by elswe on 29-Mar-18 At 8:03 AM.
 */

public class NetworkDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.network_dialog_title)
                .setMessage(R.string.network_dialog_message)
                .setPositiveButton(R.string.network_dialog_button_text, null);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
