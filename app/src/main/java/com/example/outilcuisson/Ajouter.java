package com.example.outilcuisson;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

public class Ajouter extends Fragment implements View.OnClickListener {

    private View vueDuFragmentAjouter;
    private Ecouteur ecouteur;

    private Button effacer;
    private Button valider;

    private EditText libellePlat;
    private EditText temperature;

    private TimePicker timePicker;

    public Ajouter() {
    }

    public static Ajouter newInstance() {
        Ajouter fragment = new Ajouter();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vueDuFragmentAjouter = inflater.inflate(R.layout.fragment_un,container,false);

        effacer = vueDuFragmentAjouter.findViewById(R.id.btnEffacer);
        effacer.setOnClickListener(this);

        valider = vueDuFragmentAjouter.findViewById(R.id.btnValider);
        valider.setOnClickListener(this);

        libellePlat = vueDuFragmentAjouter.findViewById(R.id.txtPlat);
        temperature = vueDuFragmentAjouter.findViewById(R.id.txtTempérature);

        timePicker = (TimePicker) vueDuFragmentAjouter.findViewById(R.id.timePicker);
        /* on passe au mode français -> 24h */
        timePicker.setIs24HourView(true);
        /* initialise le timePicker à 40min */
        timePicker.setHour(0);
        timePicker.setMinute(40);

        return vueDuFragmentAjouter;
    }



    @SuppressLint("LongLogTag")
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
            if ( !(timePicker.getHour() == 0 && timePicker.getMinute() == 0) // durée est nulle
                    && (OutilCuisson.heureCuissonValide(timePicker.getHour()) // heure est valide
                    && OutilCuisson.minuteCuissonValide(timePicker.getMinute()) // minutes sont valides
                    && OutilCuisson.platValide(libellePlat.getText().toString()) // nom du plat est valide
                    && !(temperature.getText().toString().equals("")) // temp n'est pas vide
                    && OutilCuisson.temperatureValide(Integer.parseInt(temperature.getText().toString())))) { // temp valide


                Toast.makeText(getActivity(),
                        getString(R.string.toast,libellePlat.getText().toString()),
                        Toast.LENGTH_LONG).show();

                ecouteur.recevoirPlat(OutilCuisson.transformeEnChaine(libellePlat.getText().toString(),
                        timePicker.getHour(),timePicker.getMinute(),
                        Integer.parseInt(temperature.getText().toString())));
            } else {
                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.titreAlerte)
                        .setMessage(R.string.messageAlerte)
                        .setNeutralButton(R.string.retourAlerte,null)
                        .show();
            }
        }
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        ecouteur = (Ecouteur) context;
    }

    public interface Ecouteur {
        void recevoirPlat(String lePlat);
    }

}
