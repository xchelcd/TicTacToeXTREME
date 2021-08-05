package xchel.co.tictactoextreme.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xchel.co.tictactoextreme.R;

public class PlayActivity extends AppCompatActivity {

    private final long timeStart = 20000;

    private Button startStopButton;
    private Button resetButton;
    private TextView timeTextView;


    private CountDownTimer countDownTimer;
    private long timeLeft = timeStart;
    private boolean timeRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        init();
        timeTextView.setText("00:00");
        listener();
    }

    private void listener() {
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startStop();
            }
        });
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetTimer();

            }
        });
    }

    private void resetTimer() {
        stopTimer();
        timeLeft = timeStart;
        timeTextView.setText("00:10");
        startStopButton.setEnabled(true);
        startStopButton.setText("START");
        countDownTimer.cancel();
    }

    private void startStop() {
        if(timeRunning){
            stopTimer();
        }else{
            startTimer();   
        }
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long l) {
                timeLeft = l;
                updateTextView();
            }

            @Override
            public void onFinish() {
                startStopButton.setEnabled(false);
                timeLeft = timeStart;
            }
        }.start();
        timeRunning = true;
        startStopButton.setText("PAUSE");
    }

    private void updateTextView() {
        int minutes = (int) timeLeft / 60000;
        int seconds = (int) timeLeft % 60000 / 1000;
        String timeLeftString = "" + minutes;
        timeLeftString += ":";
        if(seconds < 10)
            timeLeftString += "0";
        timeLeftString += seconds;
        timeTextView.setText(timeLeftString);
    }

    private void stopTimer() { countDownTimer.cancel(); timeRunning = false; startStopButton.setText("START");}

    private void init() {
        resetButton = findViewById(R.id.resetButton);
        startStopButton = findViewById(R.id.startStopButton);
        timeTextView = findViewById(R.id.timeTextView);
    }
}
