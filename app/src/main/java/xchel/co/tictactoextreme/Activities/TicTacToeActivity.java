package xchel.co.tictactoextreme.Activities;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.CalendarContract;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import xchel.co.tictactoextreme.Dialog.OnBackDialog;
import xchel.co.tictactoextreme.Entities.Coordenate;
import xchel.co.tictactoextreme.Entities.Winner;
import xchel.co.tictactoextreme.R;
import xchel.co.tictactoextreme.Util;

public class TicTacToeActivity extends AppCompatActivity {

    private Button[][][][] buttons = new Button[3][3][3][3];
    private TextView turnoTextView;
    private LinearLayout linearLayout;

    private final int playerOneConst = 2;
    private final int playerTwoConst = 3;


    private boolean[][][][] selected = new boolean[3][3][3][3];
    private boolean turn = false;

    private List<Coordenate> coordenateList = new ArrayList<>(81);
    private List<Winner> winnerList = new ArrayList<>();
    //private Winner[][] winners = new Winner[3][3];

    private Coordenate coordenate;
    private Winner winner;

    private List<Integer> integerList = new ArrayList<>();
    private List<Integer> aux = new ArrayList<>();

    private String winnerName = "";

    private int azar;

    private boolean prueba = false;

    private int valAux;

    private boolean getName = true;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tic_tac_toe);
        TicTacToeActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        inits();
        Bundle bundle = getIntent().getExtras();
        azar = bundle.getInt("azar");
        turn = azar == 2;
        firebaseDatabase = FirebaseDatabase.getInstance();
        if (azar == 3)
            turnoTextView.setText("Esperando al otro jugador");
        else
            turnoTextView.setText("Usted empieza");
        valAux = (azar == 2) ? 3 : 2;
        for (int i = 0; i < 81; i++) coordenateList.add(new Coordenate());
        for (int i = 0; i < 9; i++) {
            winnerList.add(new Winner());
            aux.add(i);
        }
        firebaseListener();
        waitPlayer(!turn);
        clearButtons();
        //android:background="#008DFF" color azul
        //addAuxVal();
    }

    private void firebaseListener() {
        try {
            FirebaseDatabase.getInstance().getReference(Util.root + Util.id + "/" + Util.id + valAux).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try {
                        Coordenate c = dataSnapshot.getValue(Coordenate.class);
                        saveCoordedante(buttons[c.getI()][c.getJ()][c.getK()][c.getL()], c);
                        win(buttons[c.getI()][c.getJ()][c.getK()][c.getL()].getLabelFor());
                        finalWin();
                        wichTurn(buttons[c.getI()][c.getJ()][c.getK()][c.getL()]);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setValueIntoFirebase(Coordenate c) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Util.root + Util.id + "/" + Util.id + azar);
        databaseReference.setValue(c);
    }

    private void finalWin() {
        final Intent intent = new Intent(TicTacToeActivity.this, MainActivity.class);
        int auxWinner = 0;
        boolean isWinner = false;
        try {
            for (int i = 0; i < 9; i++) {
                //
                //comentar segunda fila de cada if
                //
                if (i % 3 == 0) {
                    if (winnerList.get(i).isWinner() && winnerList.get(i + 1).isWinner() && winnerList.get(i + 2).isWinner() &&
                            (winnerList.get(i).getPlayer() == winnerList.get(i + 1).getPlayer() && winnerList.get(i).getPlayer() == winnerList.get(i + 2).getPlayer())) {
                        winnerName = String.valueOf(winnerList.get(i).getPlayer());
                        isWinner = true;
                        auxWinner = winnerList.get(i).getPlayer();
                        continue;
                    }
                }
                if (i == 0 || i == 3 || i == 6) {
                    if (winnerList.get(i).isWinner() && winnerList.get(i + 3).isWinner() && winnerList.get(i + 6).isWinner() &&
                            (winnerList.get(i).getPlayer() == winnerList.get(i + 3).getPlayer() && winnerList.get(i).getPlayer() == winnerList.get(i + 6).getPlayer())) {
                        isWinner = true;
                        auxWinner = winnerList.get(i).getPlayer();
                        continue;
                    }
                }
                if (i == 0) {
                    if (winnerList.get(i).isWinner() && winnerList.get(i + 4).isWinner() && winnerList.get(i + 8).isWinner() &&
                            (winnerList.get(i).getPlayer() == winnerList.get(i + 4).getPlayer() && winnerList.get(i).getPlayer() == winnerList.get(i + 8).getPlayer())) {
                        isWinner = true;
                        auxWinner = winnerList.get(i).getPlayer();
                        continue;
                    }
                }
                if (i == 2) {
                    if (winnerList.get(i).isWinner() && winnerList.get(i + 2).isWinner() && winnerList.get(i + 4).isWinner() &&
                            (winnerList.get(i).getPlayer() == winnerList.get(i + 2).getPlayer() && winnerList.get(i).getPlayer() == winnerList.get(i + 4).getPlayer())) {
                        isWinner = true;
                        auxWinner = winnerList.get(i).getPlayer();
                        continue;
                    }
                }
            }
            if (isWinner) {
                if (auxWinner == azar)
                    winnerName = Util.namePlayer;
                else
                    winnerName = "Perdiste";
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("GANADOR").
                        setMessage("Felicades: " + winnerName).
                        setPositiveButton("Salir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                finish();
                                startActivity(intent);

                            }
                        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        for (int i = 0; i < 3; i++)
                            for (int j = 0; j < 3; j++)
                                for (int k = 0; k < 3; k++)
                                    for (int l = 0; l < 3; l++)
                                        buttons[i][j][k][l].setEnabled(false);
                    }
                }).setCancelable(false);
                dialog.create().show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void inits() {
        int aux = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        final String buttonID = "a" + i + j + k + l;
                        int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                        buttons[i][j][k][l] = findViewById(resID);
                        buttons[i][j][k][l].setLabelFor(aux);
                        aux++;
                        selected[i][j][k][l] = false;
                        final int finalI = i;
                        final int finalJ = j;
                        final int finalK = k;
                        final int finalL = l;
                        buttons[i][j][k][l].setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                saveCoordedante(buttonID, buttons[finalI][finalJ][finalK][finalL]);
                                setValueIntoFirebase(coordenate);
                                win(buttons[finalI][finalJ][finalK][finalL].getLabelFor());
                                finalWin();
                                wichTurn(view);
                            }
                        });
                    }
                }
            }
        }
        turnoTextView = findViewById(R.id.turnoTextView);
        linearLayout = findViewById(R.id.linearLayout);
    }

    @SuppressLint("ResourceAsColor")
    private void saveCoordedante(String buttonID, Button b) {
        int turnInt = (turn) ? playerOneConst : playerTwoConst;
        coordenate = new Coordenate(//enviar a firebase
                Integer.parseInt(String.valueOf(buttonID.charAt(1))),
                Integer.parseInt(String.valueOf(buttonID.charAt(2))),
                Integer.parseInt(String.valueOf(buttonID.charAt(3))),
                Integer.parseInt(String.valueOf(buttonID.charAt(4))),
                azar);
        coordenateList.set(b.getLabelFor(), coordenate);
        selected[coordenate.getI()][coordenate.getJ()][coordenate.getK()][coordenate.getL()] = true;
        selectionable();
        buttons[coordenate.getI()][coordenate.getJ()][coordenate.getK()][coordenate.getL()].setEnabled(false);
    }

    @SuppressLint("ResourceAsColor")
    private void saveCoordedante(Button b, Coordenate c) {
        coordenateList.set(b.getLabelFor(), c);
        selected[c.getI()][c.getJ()][c.getK()][c.getL()] = true;
        selectionable(c);
        buttons[c.getI()][c.getJ()][c.getK()][c.getL()].setEnabled(false);
    }

    private void selectionable() {
        String cadenaDestino = String.valueOf(coordenate.getK()) + String.valueOf(coordenate.getL());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        String cadenaAux = String.valueOf(i) + String.valueOf(j);
                        if (cadenaAux.equals(cadenaDestino)) {
                            //condicional para mantener colores
                            if (!selected[i][j][k][l])
                                buttons[i][j][k][l].setEnabled(true);
                            if (buttons[i][j][k][l].getTop() == 5) {
                            } else {
                            }//descomentar todo lo de esta funcion
                            //buttons[i][j][k][l].setBackgroundColor(Color.CYAN);
                        } else {
                            if (buttons[i][j][k][l].getTop() != 5) {
                            }
                            //buttons[i][j][k][l].setBackgroundColor(Color.GRAY);
                            buttons[i][j][k][l].setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    private void selectionable(Coordenate c) {
        String cadenaDestino = String.valueOf(c.getK()) + String.valueOf(c.getL());
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {
                    for (int l = 0; l < 3; l++) {
                        String cadenaAux = String.valueOf(i) + String.valueOf(j);
                        if (cadenaAux.equals(cadenaDestino)) {
                            //condicional para mantener colores
                            if (!selected[i][j][k][l])
                                buttons[i][j][k][l].setEnabled(true);
                            if (buttons[i][j][k][l].getTop() == 5) {
                            } else {
                            }//descomentar todo lo de esta funcion
                            //buttons[i][j][k][l].setBackgroundColor(Color.CYAN);
                        } else {
                            if (buttons[i][j][k][l].getTop() != 5) {
                            }
                            //buttons[i][j][k][l].setBackgroundColor(Color.GRAY);
                            buttons[i][j][k][l].setEnabled(false);
                        }
                    }
                }
            }
        }
    }

    private void win(int idButton) {
        int k = 0;
        try {
            for (int i = 0; i < idButton; i++) {
                if (!integerList.containsAll(aux) && !winnerList.get(i / 9).isWinner()) {
                    if (i % 3 == 0) {
                        if (coordenateList.get(i).getState() == coordenateList.get(i + 1).getState() &&
                                coordenateList.get(i + 2).getState() == coordenateList.get(i).getState() &&
                                coordenateList.get(i).getState() != 0) {//ganador por fila
                            integerList.add(k);
                            paintcells(coordenateList.get(i).getState(), 0, i);
                            //Toast.makeText(this, "ganador" + coordenateList.get(i).getState(), Toast.LENGTH_SHORT).show();
                            continue;
                        }
                    }
                    if (i % 9 == 0 || i % 9 == 1 || i % 9 == 2) {
                        if (coordenateList.get(i).getState() == coordenateList.get(i + 3).getState() &&
                                coordenateList.get(i + 6).getState() == coordenateList.get(i).getState() &&
                                coordenateList.get(i).getState() != 0) {
                            integerList.add(k);
                            paintcells(coordenateList.get(i).getState(), 1, i);
                            //Toast.makeText(this, "ganador" + coordenateList.get(i).getState(), Toast.LENGTH_SHORT).show();
                            continue;
                        }
                    }
                    if (i % 9 == 0) {
                        if (coordenateList.get(i).getState() == coordenateList.get(i + 4).getState() &&
                                coordenateList.get(i + 8).getState() == coordenateList.get(i).getState() &&
                                coordenateList.get(i).getState() != 0) {
                            integerList.add(k);
                            paintcells(coordenateList.get(i).getState(), 2, i);
                            //Toast.makeText(this, "ganador" + coordenateList.get(i).getState(), Toast.LENGTH_SHORT).show();
                            continue;
                        }
                    }
                    if (i % 9 == 2) {
                        if (coordenateList.get(i).getState() == coordenateList.get(i + 2).getState() &&
                                coordenateList.get(i + 4).getState() == coordenateList.get(i).getState() &&
                                coordenateList.get(i).getState() != 0) {
                            integerList.add(k);
                            paintcells(coordenateList.get(i).getState(), 3, i);
                            //Toast.makeText(this, "ganador" + coordenateList.get(i).getState(), Toast.LENGTH_SHORT).show();
                            continue;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void paintcells(int player, int form, int aux) {
        int i = coordenateList.get(aux).getI();
        int j = coordenateList.get(aux).getJ();
        int k = coordenateList.get(aux).getK();
        int l = coordenateList.get(aux).getL();
        coordenateList.get(aux).setWinner(true);
        winner = new Winner(i, j, player, true);
        winnerList.set(aux / 9, winner);
        switch (player) {
            case 2://Horizontal
                if (form == 0) {//horizontal
                    buttons[i][j][k][l].setBackgroundColor(Color.parseColor("#AD4DCF"));
                    buttons[i][j][k][l + 1].setBackgroundColor(Color.parseColor("#AD4DCF"));
                    buttons[i][j][k][l + 2].setBackgroundColor(Color.parseColor("#AD4DCF"));
                } else if (form == 1) {//vertical
                    buttons[i][j][k][l].setBackgroundColor(Color.parseColor("#AD4DCF"));
                    buttons[i][j][k + 1][l].setBackgroundColor(Color.parseColor("#AD4DCF"));
                    buttons[i][j][k + 2][l].setBackgroundColor(Color.parseColor("#AD4DCF"));
                } else if (form == 3) {//diagonal
                    buttons[i][j][k][l].setBackgroundColor(Color.parseColor("#AD4DCF"));
                    buttons[i][j][k + 1][l - 1].setBackgroundColor(Color.parseColor("#AD4DCF"));
                    buttons[i][j][k + 2][l - 2].setBackgroundColor(Color.parseColor("#AD4DCF"));
                } else if (form == 2) {//diagonal
                    buttons[i][j][k][l].setBackgroundColor(Color.parseColor("#AD4DCF"));
                    buttons[i][j][k + 1][l + 1].setBackgroundColor(Color.parseColor("#AD4DCF"));
                    buttons[i][j][k + 2][l + 2].setBackgroundColor(Color.parseColor("#AD4DCF"));
                }
                break;
            case 3://vertical
                if (form == 0) {//horizontal
                    buttons[i][j][k][l].setBackgroundColor(Color.parseColor("#008DFF"));
                    buttons[i][j][k][l + 1].setBackgroundColor(Color.parseColor("#008DFF"));
                    buttons[i][j][k][l + 2].setBackgroundColor(Color.parseColor("#008DFF"));
                } else if (form == 1) {//vertical
                    buttons[i][j][k][l].setBackgroundColor(Color.parseColor("#008DFF"));
                    buttons[i][j][k + 1][l].setBackgroundColor(Color.parseColor("#008DFF"));
                    buttons[i][j][k + 2][l].setBackgroundColor(Color.parseColor("#008DFF"));
                } else if (form == 3) {//diagonal
                    buttons[i][j][k][l].setBackgroundColor(Color.parseColor("#008DFF"));
                    buttons[i][j][k + 1][l - 1].setBackgroundColor(Color.parseColor("#008DFF"));
                    buttons[i][j][k + 2][l - 2].setBackgroundColor(Color.parseColor("#008DFF"));
                } else if (form == 2) {//diagonal
                    buttons[i][j][k][l].setBackgroundColor(Color.parseColor("#008DFF"));
                    buttons[i][j][k + 1][l + 1].setBackgroundColor(Color.parseColor("#008DFF"));
                    buttons[i][j][k + 2][l + 2].setBackgroundColor(Color.parseColor("#008DFF"));
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + player);
        }
    }

    private void wichTurn(View view) {
        boolean flag;
        if (turn) {
            turnoTextView.setText("Esperando jugador");
            view.setForeground(ContextCompat.getDrawable(this, R.drawable.ic_close_black_24dp));
            turn = false;
            flag = true;
            if (getName)
                getName = false;
        } else {
            turnoTextView.setText("Tu turno " + Util.namePlayer);
            view.setForeground(ContextCompat.getDrawable(this, R.drawable.ic_pets_black_24dp));
            turn = true;
            flag = false;
        }
        waitPlayer(flag);
    }

    @Override
    public void onBackPressed() {
        if (true != !!false) {
            OnBackDialog onBackDialog = new OnBackDialog();
            onBackDialog.setCancelable(true);
            onBackDialog.show(getSupportFragmentManager(), "dialog");
        } else
            super.onBackPressed();
    }

    private void firebaseAux() {
        FirebaseDatabase.getInstance().getReference(Util.root + Util.id + "/" + Util.id + azar).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    wichTurn(buttons[dataSnapshot.getValue(Coordenate.class).getI()][dataSnapshot.getValue(Coordenate.class).getJ()][dataSnapshot.getValue(Coordenate.class).getK()][dataSnapshot.getValue(Coordenate.class).getL()]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void waitPlayer(boolean flag) {
        if (flag)
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    for (int k = 0; k < 3; k++)
                        for (int l = 0; l < 3; l++)
                            buttons[i][j][k][l].setClickable(false);
        else
            for (int i = 0; i < 3; i++)
                for (int j = 0; j < 3; j++)
                    for (int k = 0; k < 3; k++)
                        for (int l = 0; l < 3; l++)
                            buttons[i][j][k][l].setClickable(true);
    }

    public void clearButtons() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                for (int k = 0; k < 3; k++)
                    for (int l = 0; l < 3; l++)
                        buttons[i][j][k][l].setText("");
    }
}

//for (int i = 0; i < 3; i++) {
//    for (int j = 0; j < 3; j++) {
//        for (int k = 0; k < 3; k++) {
//            for (int l = 0; l < 3; l++) {

//            }
//        }
//    }
//}

