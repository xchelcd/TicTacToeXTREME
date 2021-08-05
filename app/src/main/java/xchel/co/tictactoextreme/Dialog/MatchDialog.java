package xchel.co.tictactoextreme.Dialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.math.BigInteger;
import java.security.SecureRandom;

import xchel.co.tictactoextreme.Activities.MainActivity;
import xchel.co.tictactoextreme.R;
import xchel.co.tictactoextreme.Util;


public class MatchDialog extends DialogFragment {

    private final int findMatch = 5;
    private final int createMatch = 9;

    private Button okDialogButton;
    private Button cancelDialogButton;
    private Button generateIdDialogButton;
    private EditText idOfMatchTextView;
    //private EditText nameDialogEditText;

    public String text;

    public android.app.Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.match_dialog, null, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        findById(dialogView);
        typeMatch();
        listeners();
        alertDialog.show();
        alertDialog.dismiss();
        return alertDialog;
    }

    private void listeners() {
        okDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!idOfMatchTextView.getText().toString().equals("")) {
                    if (Util.typeOfMatch == createMatch) {
                        Util.planerOne = true;
                        MainActivity.idMatchTextView.setText("id: " + text);
                        MainActivity.playButton.setEnabled(true);
                    }else{
                        //llevar a activity
                        Util.planerOne = false;
                        Util.id = idOfMatchTextView.getText().toString().trim();
                        MainActivity.idMatchTextView.setText("id: " + idOfMatchTextView.getText().toString().trim());
                        MainActivity.playButton.setEnabled(true);
                    }
                    dismiss();
                }else Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
            }
        });
        generateIdDialogButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                SecureRandom random = new SecureRandom();
                text = new BigInteger(130, random).toString(32);
                Util.id = text;
                //Util.id = "12345";
                //idOfMatchTextView.setText("12345");//text
                idOfMatchTextView.setText(text);//text
                Toast.makeText(getActivity(), "Code successful", Toast.LENGTH_SHORT).show();
            }
        });
        cancelDialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    private void typeMatch(){
        if (Util.typeOfMatch == findMatch){
            generateIdDialogButton.setVisibility(View.INVISIBLE);
            okDialogButton.setText("Play");
        }else if(Util.typeOfMatch == createMatch){
            generateIdDialogButton.setVisibility(View.VISIBLE);
            okDialogButton.setText("Ok");
            idOfMatchTextView.setEnabled(false);
        }
    }

    private void findById(View v) {
        idOfMatchTextView = v.findViewById(R.id.idOfMatchTextView);
        okDialogButton = v.findViewById(R.id.okDialogButton);
        cancelDialogButton = v.findViewById(R.id.cancelDialogButton);
        generateIdDialogButton = v.findViewById(R.id.generateIdButton);
        //nameDialogEditText = v.findViewById(R.id.nameDialogEditText);
    }
}