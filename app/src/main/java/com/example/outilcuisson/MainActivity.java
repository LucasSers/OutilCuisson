package com.example.outilcuisson;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button effacer;
    private Button valider;

    private EditText libellePlat;
    private EditText temperature;

    private TimePicker timePicker;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        effacer = findViewById(R.id.btnEffacer);
        effacer.setOnClickListener(this);
        valider = findViewById(R.id.btnValider);
        valider.setOnClickListener(this);
        libellePlat = findViewById(R.id.txtPlat);
        temperature = findViewById(R.id.txtTempérature);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        /* on passe au mode français -> 24h */
        timePicker.setIs24HourView(true);
        /* initialise le timePicker à 40min */
        timePicker.setHour(0);
        timePicker.setMinute(40);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnEffacer) {
            temperature.setText("");
            libellePlat.setText("");
            timePicker.setHour(0);
            timePicker.setMinute(40);
        }
        if (view.getId() == R.id.btnValider) {
            if ( OutilCuisson.heureCuissonValide(timePicker.getHour())
                 && OutilCuisson.minuteCuissonValide(timePicker.getMinute())
                 && OutilCuisson.platValide(libellePlat.getText().toString())
                 && OutilCuisson.temperatureValide(Integer.parseInt(temperature.getText().toString()))) {

                Toast.makeText(this, getString(R.string.toast,libellePlat.getText().toString())
                        ,Toast.LENGTH_LONG).show();
            } else {
                new AlertDialog.Builder(this)
                        .setTitle(R.string.titreAlerte)
                        .setMessage(R.string.messageAlerte)
                        .setNeutralButton(R.string.retourAlerte,null)
                        .show();
            }
        }

    }
}