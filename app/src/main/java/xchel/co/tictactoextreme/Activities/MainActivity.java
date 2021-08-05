package xchel.co.tictactoextreme.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.security.SecureRandom;

import xchel.co.tictactoextreme.Dialog.MatchDialog;
import xchel.co.tictactoextreme.Entities.Coordenate;
import xchel.co.tictactoextreme.Entities.Player;
import xchel.co.tictactoextreme.R;
import xchel.co.tictactoextreme.Util;

public class MainActivity extends AppCompatActivity {

    private final int findMatch = 5;
    private final int createMatch = 9;
    private final String SEND_REQUEST_CODE = "SEND_REQUEST_CODE";


    public static Button playButton;
    private Button findButton;
    private Button createButton;
    private Button localButton;
    public static TextView idMatchTextView;
    private EditText userName;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inits();
        listeners();

    }

    private void createTable(int val){
        firebaseDatabase = FirebaseDatabase.getInstance();
        //databaseReference = firebaseDatabase.getReference(Util.root + Util.playerReady + "/" + userName.getText().toString().trim());
        //Player player = new Player(val, userName.getText().toString().trim(), true);
        //databaseReference.setValue(player);
        databaseReference = firebaseDatabase.getReference(Util.root + Util.id + "/" + Util.id+val);
        databaseReference.setValue(new Coordenate(5,5,5,5));
    }

    private void listeners() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(MainActivity.this, TicTacToeActivity.class);
                if (!userName.getText().toString().trim().equals("") && userName.getText().toString().trim().length() > 2 && !idMatchTextView.getText().toString().equals("id: ")) {
                    //int valorDado = (int) Math.floor(Math.random() * 2 + 2);
                    int valorDado = (Util.planerOne) ? 2 : 3;
                    //if (valorDado == 2)
                    //    Toast.makeText(MainActivity.this, "Usted empieza " + (valorDado - 1), Toast.LENGTH_LONG).show();
                    //else
                    //    Toast.makeText(MainActivity.this, "Empeiza el otro jugador" + (valorDado - 1), Toast.LENGTH_LONG).show();
                    intent.putExtra("name", userName.getText().toString().trim());
                    Util.namePlayer = userName.getText().toString().trim();
                    intent.putExtra("azar", valorDado);
                    createTable(valorDado);
                    setNameInTable(valorDado);
                    startActivity(intent);
                    finish();
                } else
                    Toast.makeText(MainActivity.this, "Necesita un nombre mínimo de 3 caracteres", Toast.LENGTH_SHORT).show();

            }
        });
        localButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent = new Intent(MainActivity.this, TicTacToeActivity.class);
                //startActivity(intent);
                //finish();
                Toast.makeText(MainActivity.this, "Comming soon", Toast.LENGTH_SHORT).show();
                //databaseReference = FirebaseDatabase.getInstance().getReference(Util.root+Util.id);
                //databaseReference.removeValue();
            }
        });
        findButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Util.typeOfMatch = findMatch;
                MatchDialog matchDialog = new MatchDialog();
                matchDialog.setCancelable(false);
                matchDialog.show(getSupportFragmentManager(), "dialog");
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //
                //Coordenate c = new Coordenate(0,0,0,1);
                //firebaseDatabase = FirebaseDatabase.getInstance();
                //databaseReference = firebaseDatabase.getReference(Util.root + Util.id + "/" + Util.id);
                //databaseReference.setValue(c);

                //no abajo
                //databaseReference = firebaseDatabase.getReference("pruebaNumeroUno_2/prueba" + "/" + i);
                //databaseReference.setValue("xchel"+i);
                //i++;
                //Toast.makeText(MainActivity.this, "Comming soon0", Toast.LENGTH_SHORT).show();
                Util.typeOfMatch = createMatch;
                MatchDialog matchDialog = new MatchDialog();
                matchDialog.setCancelable(false);
                matchDialog.show(getSupportFragmentManager(), "dialog");
            }
        });
        idMatchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String text = idMatchTextView.getText().toString().substring(4);
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("text", text);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "No id", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setNameInTable(int valorDado){
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(Util.root + Util.player + "/" + userName.getText().toString().trim()+Util.root);
        databaseReference.setValue(new Player(valorDado, userName.getText().toString().trim()));
    }

    private void inits() {
        playButton = findViewById(R.id.playButton);
        findButton = findViewById(R.id.findButton);
        createButton = findViewById(R.id.createButton);
        idMatchTextView = findViewById(R.id.idMatchTextView);
        localButton = findViewById(R.id.localButton);
        userName = findViewById(R.id.userName);
    }
}
