package com.example.outilcuisson;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class FragmentDeux extends Fragment {

    private ListView listeCuissons;
    private ArrayList<String> lesCuissons = new ArrayList<>();
    private ArrayAdapter<String> adaptateur;

    String text; // Pour le toast

    public FragmentDeux() {
        // empty body
    }

    public static FragmentDeux newInstance() {
        FragmentDeux fragment = new FragmentDeux();
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View vueDuFragment = inflater.inflate(R.layout.fragment_deux,container,false);
        listeCuissons = vueDuFragment.findViewById(R.id.liste);

        lesCuissons.add(OutilCuisson.transformeEnChaine("Pizza",0, 20, 205));
        lesCuissons.add(OutilCuisson.transformeEnChaine("Gratin dauphinois",0, 50, 180));
        lesCuissons.add(OutilCuisson.transformeEnChaine("Tarte aux pommes",0, 40, 205));
        lesCuissons.add(OutilCuisson.transformeEnChaine("Poulet",1, 10, 200));

        /* on affecte la layout défini par les items de la liste */
        adaptateur = new ArrayAdapter<String>(getActivity(), R.layout.list_item_layout, lesCuissons);

        listeCuissons.setAdapter(adaptateur);

        // on précise qu'un menu est associé à la liste qui correspond à l'activité
        registerForContextMenu(listeCuissons);
        return vueDuFragment;
    }

    /**
     * Méthode invoquée automatiquement lorsque l'utilisateur active un menu contextuel
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        /*
         * on désérialise le fichier XML décriant le menu et on l'associe
         * au menu argument (celui qui a été activé)
         */
        new MenuInflater(getActivity()).inflate(R.menu.menu_general, menu);
    }

    /**
     * Méthode invoquée automatiquement lorsque l'utilisateur choisira une option
     * dans un menu contextuel
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        /*
         * on accède à des informations supplémentaires sur la vue associée
         * au menu activé. L'information qui nous intéresse est la position
         * de l'élément de la liste sur lequel l'utilisateur a cliqué pour
         * activer le menu.
         */
        AdapterView.AdapterContextMenuInfo information =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();

        // selon l'option sélectionnée dans le menu, on réalise le traitement adéquat
        switch(item.getItemId()) {

            case R.id.supprimer: // on supprime de l'adaptateur l'article courant
                adaptateur.remove(lesCuissons.get(information.position));
                break;

            case R.id.thermostat: // affiche le thermostat dans une AlertDialog

                String plat = OutilCuisson.extrairePlat(lesCuissons.get(information.position));
                int temperature = OutilCuisson.extraireTemperature(lesCuissons.get(information.position));

                new AlertDialog.Builder(getActivity())
                        .setTitle(R.string.titreThermostat)
                        .setMessage(getString(R.string.messageThermostat, plat, temperature, OutilCuisson.thermostat(temperature)))
                        .setNeutralButton(R.string.retourAlerte,null)
                        .show();
                break;

            case R.id.annuler : // retour à la liste principale
                break;
        }
        return (super.onContextItemSelected(item));
    }
}
