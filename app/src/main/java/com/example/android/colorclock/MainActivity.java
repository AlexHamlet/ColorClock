package com.example.android.colorclock;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    private TextView txtTime;
    private ConstraintLayout background;
    private Date d;
    private String r, g, b, fontColor;
    private int hour, min, sec, ms, redValue, greenValue, blueValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtTime =  findViewById( R.id.txtTime);
        background = findViewById(R.id.bckgrnd);


        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                d = Calendar.getInstance().getTime();
                hour = d.getHours();
                min = d.getMinutes();
                sec = d.getSeconds();

                r = hourWave(hour, min, sec);
                g = minWave(hour, min, sec);
                b = secWave(hour, min, sec);

                txtTime.setText(String.format("%02d:%02d:%02d", hour, min, sec));
                background.setBackgroundColor(Color.parseColor(String.format("#%s%s%s", r, g, b)));
                fontColor = ((redValue*0.299 + greenValue*0.587 + blueValue*0.114) > 186 ? "#000000" : "#FFFFFF");
                txtTime.setTextColor(Color.parseColor(fontColor));

                handler.postDelayed(this, 100);
            }
        }, 100);
    }

    private String secWave(int hour, int min, int sec) {
        double colorVal;
        //colorVal = 127.5 * Math.sin((Math.PI/30)*(sec)) + 127.5;
        colorVal = 8.533 * Math.abs(sec - 30);
        blueValue = (int)colorVal;
        return String.format("%02X", (int)colorVal & 0xFFFFF);
    }

    private String minWave(int hour, int min, int sec) {
        double colorVal;
        //colorVal = 127.5 * Math.sin((Math.PI/14)*(60 * min + sec)) + 127.5;
        colorVal = Math.abs((60 * min + sec)/1.7578125 % 511 - 255);
        greenValue = (int)colorVal;
        return String.format("%02X", (int)colorVal & 0xFFFFF);
    }

    private String hourWave(int hour, int min, int sec) {
        double colorVal;
        //colorVal = 127.5 * Math.sin((Math.PI/5)*(60 * hour + min)) + 127.5;
        colorVal = Math.abs((60 * hour + min)/1.7578125 % 511 - 255);
        redValue = (int)colorVal;
        return String.format("%02X", (int)colorVal & 0xFFFFF);
    }
}

