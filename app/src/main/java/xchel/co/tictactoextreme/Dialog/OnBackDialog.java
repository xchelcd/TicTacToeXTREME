package xchel.co.tictactoextreme.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.math.BigInteger;
import java.security.SecureRandom;

import xchel.co.tictactoextreme.Activities.MainActivity;
import xchel.co.tictactoextreme.R;
import xchel.co.tictactoextreme.Util;


public class OnBackDialog extends DialogFragment {

    private Button okDialogButton;
    private Button cancelDialogButton;

    public String text;

    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.on_back_dialog, null, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        findById(dialogView);
        listeners();
        alertDialog.show();
        alertDialog.dismiss();
        return alertDialog;
    }

    private void listeners() {
        okDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
            }
        });
        cancelDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void findById(View v) {
        okDialogButton = v.findViewById(R.id.okDialogBackButton);
        cancelDialogButton = v.findViewById(R.id.cancelDialogBackButton);
    }
}