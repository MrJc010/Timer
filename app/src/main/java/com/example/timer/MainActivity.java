package com.example.timer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    SeekBar seekBar;
    TextView timerTextVIew;
    Button button;
    CountDownTimer countDownTimer;
    Boolean counterIsActive = false;

    public void controlTimer(View view){

        if(!counterIsActive){
            button.setText("Stop!");
            counterIsActive = true;
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000){
                @Override
                public void onTick(long l) {
                    timer((int) l/1000);
                }
                @Override
                public void onFinish() {
                    resetTimer();
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.airhorn);
                    mediaPlayer.start();
                }
            }.start();

        }else{
            resetTimer();
        }
    }

    public void resetTimer() {
        timerTextVIew.setText("0:30");
        seekBar.setProgress(30);
        countDownTimer.cancel();
        button.setText("Go!");
        seekBar.setEnabled(true);
        counterIsActive = false;
    }

    public void timer(int secs){
        int mins = (int) secs/60;
        int sec = secs - mins * 60;

        if(sec== 0) timerTextVIew.setText(mins+":00");
        else if(sec < 10) timerTextVIew.setText(mins+":0"+sec);
        else timerTextVIew.setText(mins+":"+sec);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //This method of countdown will be destroyed when it's finished.
        button = (Button) findViewById(R.id.controllerButton);

        timerTextVIew = (TextView) findViewById(R.id.timerTextView);

       seekBar = (SeekBar) findViewById(R.id.timerSeekBar);

        seekBar.setMax(600);
        seekBar.setProgress(30);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                timer(i);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }
}
