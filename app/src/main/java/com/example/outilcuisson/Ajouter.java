package com.example.outilcuisson;

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

    private View fragment1;
    private Ecouteur ecouteur;
    //private View fragment2;

//    ListView listeCuissons;
//    ArrayList<String> lesCuissons = new ArrayList<>();
//    ArrayAdapter<String> adaptateur;

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
        fragment1 = inflater.inflate(R.layout.fragment_un,container,false);
        //fragment2 = inflater.inflate(R.layout.fragment_deux,container,false);

        effacer = fragment1.findViewById(R.id.btnEffacer);
        effacer.setOnClickListener(this);

        valider = fragment1.findViewById(R.id.btnValider);
        valider.setOnClickListener(this);

        libellePlat = fragment1.findViewById(R.id.txtPlat);
        temperature = fragment1.findViewById(R.id.txtTempérature);

        timePicker = (TimePicker) fragment1.findViewById(R.id.timePicker);
        /* on passe au mode français -> 24h */
        timePicker.setIs24HourView(true);
        /* initialise le timePicker à 40min */
        timePicker.setHour(0);
        timePicker.setMinute(40);

        //adaptateur = new ArrayAdapter<String>(getActivity(), R.layout.list_item_layout, lesCuissons);
        //listeCuissons = fragment2.findViewById(R.id.liste);

        return fragment1;
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


                Toast.makeText(getActivity(),
                        getString(R.string.toast,libellePlat.getText().toString()),
                        Toast.LENGTH_LONG).show();
                ecouteur.recevoirPlat(OutilCuisson.transformeEnChaine(libellePlat.getText().toString(),
                        timePicker.getHour(),timePicker.getMinute(),
                        Integer.parseInt(temperature.getText().toString())));
                //adaptateur.notifyDataSetChanged();
                //listeCuissons.requestLayout();
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
