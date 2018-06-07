package com.example.chris.stopwatch;

import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView waktu;
    Button start, pause, reset;
    long StartTime, waktumilidetik, buffwaktu, updatewaktu = 0L;
    int jam, menit, detik, milidetik;
    Handler handler;
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        waktu = (TextView)findViewById(R.id.waktu);
        start = (Button)findViewById(R.id.start);
        pause = (Button)findViewById(R.id.pause);
        reset = (Button)findViewById(R.id.reset);
        handler = new Handler();

        runnable = new Runnable() {
            @Override
            public void run() {
                waktumilidetik = SystemClock.uptimeMillis() - StartTime;
                updatewaktu = buffwaktu + waktumilidetik;
                detik = (int)(updatewaktu/1000);
                jam = menit/60;
                menit = detik/60;
                detik = detik%60;
                milidetik = (int)(updatewaktu%1000);

                waktu.setText(""+String.format("%02d",jam)+":"+String.format("%02d",menit)+":"+String.format("%02d",detik)+":"+String.format("%03d",milidetik));
                handler.postDelayed(this, 0);
            }
        };

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset.setEnabled(false);
                start.setEnabled(false);
                pause.setEnabled(true);
                StartTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buffwaktu += waktumilidetik;
                handler.removeCallbacks(runnable);
                pause.setEnabled(false);
                reset.setEnabled(true);
                start.setEnabled(true);
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jam = 0;
                menit = 0;
                detik = 0;
                StartTime = 0L;
                buffwaktu = 0L;
                waktumilidetik = 0L;
                updatewaktu = 0L;

                waktu.setText("00:00:00:000");
            }
        });

    }
}
