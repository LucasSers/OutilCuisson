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
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Afficher extends Fragment {

    private ListView listeCuissons;
    private ArrayList<String> lesCuissons = new ArrayList<>();
    private ArrayAdapter<String> adaptateur;

    String text; // Pour le toast

    public Afficher() {
        // empty body
    }

    public static Afficher newInstance() {
        Afficher fragment = new Afficher();
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


        /* on affecte le layout défini par les items de la liste */
        adaptateur = new ArrayAdapter<String>(getActivity(), R.layout.list_item_layout, lesCuissons);

        listeCuissons.setAdapter(adaptateur);

        // on précise qu'un menu est associé à la liste qui correspond à l'activité
        registerForContextMenu(listeCuissons);

        String nouveauPlat;
        nouveauPlat = ((MainActivity) getActivity()).getPlatAGerer();
        if ( nouveauPlat != null ) {
            ajouterPlat(nouveauPlat);
        }

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
                // met à jour le layout de la liste
                setListViewHeightBasedOnChildren(listeCuissons);
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

    public void ajouterPlat(String lePlat) {
        adaptateur.add(lePlat);
        // met à jour le layout de la liste
        setListViewHeightBasedOnChildren(listeCuissons);
    }


    /* met à jour la taille du layout en fonction de son contenu
     * évite de n'avoir rien d'affiché à l'écran même si la liste contient des cuissons
     * évite d'avoir une liste vide à l'écran s'il n'y pas de cuissons
     * Source : internet
     */
    private void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        // si on arrive au bout de la fenêtre Android, cad 7 items, alors
        // l'affichage ne se met plus à jour et un scroll apparaît automatiquement
        if (listAdapter == null || listAdapter.getCount() > 7) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }
}