package com.example.outilcuisson;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    private TimePicker timePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        /* on passe au mode français -> 24h */
        timePicker.setIs24HourView(true);
        /* initialise le timePicker à 40min */
        timePicker.setHour(0);
        timePicker.setMinute(40);
    }
}