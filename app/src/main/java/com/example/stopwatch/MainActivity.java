package com.example.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView timeView;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;
    private Button lapButton;
    private TextView lapView;

    private int seconds = 0;
    private boolean running;
    private Handler handler;
    private int lapNumber = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        timeView = findViewById(R.id.time_view);
        startButton = findViewById(R.id.start_button);
        stopButton = findViewById(R.id.stop_button);
        resetButton = findViewById(R.id.reset_button);
        lapButton =findViewById(R.id.lap_button);
        lapView = findViewById(R.id.lap_view);

        if (savedInstanceState!= null) {
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
        }

        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
    }


    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (running) {
            runTimer();
        }
    }

    public void onClickStart(View view) {
        running = true;
        runTimer();
    }
    public void onClickStop(View view) {
        running = false;
    }

    public void onClickReset(View view) {
        running = false;
        seconds = 0;
        timeView.setText(" 00:00:00");
    }
    public void onClickLap(View view) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int sec = seconds % 60;

        String lapTime = String.format(Locale.getDefault(),
                "Lap %d: %d: %02d: %02d\n", lapNumber, hours, minutes, sec);
        lapView.append(lapTime); // Append lap time to lap view
        lapNumber++; // Increment lap number
    }

    public void runTimer() {
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int minutes = (seconds % 3600) / 60;
                int sec = seconds % 60;

                String time = String.format(Locale.getDefault(),
                        "%d: %02d: %02d", hours, minutes, sec);
                timeView.setText(time);
                if (running) {
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });
    }
}