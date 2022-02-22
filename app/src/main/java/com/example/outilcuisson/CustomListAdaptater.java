package com.example.outilcuisson;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class CustomListAdaptater<String> extends ArrayAdapter<String> {

    /** Identifiant de la vue permettant d'afficher chaque item de la liste */
    private int identifiantVueItem;
    /**
     * Objet utilitaire permettant de désérialiser une vue
     */
    private LayoutInflater inflater;
    /** Regroupe les 2 TextView présents sur la vue d'un item de la liste */
    static class SauvegardeTextView {
        TextView uneCuisson;
    }
/**
 * Constructeur de l'adaptateur
 * @param contexte contexte de création de l'adaptateur
 * @param vueItem identifiant de la vue permettant d'afficher chaque
 * item de la liste
 * @param lesItems Liste de items à afficher
 */
public CustomListAdaptater(Context contexte, int vueItem,
                           ArrayList<String> lesItems) {
    super(contexte, vueItem, lesItems);
    this.identifiantVueItem = vueItem;
    inflater = (LayoutInflater)getContext()
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
}
    /**
     * Permet d'affecter à chaque item de la liste, les valeurs qui
     * doivent être affichées
     * @param position position de l'élément qui doit être affiché
     * (position au sein de la liste associée à l'adaptateur)
     * @param uneVue contient soit la valeur null, soit une ancienne vue
     * pour l'élément à afficher. La méthode pourra alors se
     * contenter de réactualiser cette vue
     * @param parent vue parente à laquelle la vue à renvoyer peut être rattachée
     * @return une vue qui affichera les informations adéquates dans l'item de la liste
     * situé à la position p
     */
    @Override
    public View getView(int position, View uneVue, ViewGroup parent) {

        // on récupère la valeur de l'item à afficher, via sa position
        String ligneCuisson = getItem(position);

        TextView vueItemListe; // layout décrivant un item de la liste

        SauvegardeTextView sauve; // regroupe les 2 TextView présent sur la vue


        // destinée à afficher l'item
        if (uneVue == null) {
            /*
             * la vue décrivant chaque item de la liste n'est pas encore créée
             * Il faut désérialiser le layout correspondant à cette vue.
             */
            uneVue = inflater.inflate(identifiantVueItem, parent, false);

            // on récupère un accès sur le TextView qu'il faudra renseigner
            sauve = new SauvegardeTextView();
            sauve.uneCuisson = uneVue.findViewById(R.id.text1);

            // on stocke les identifiants du TextView dans la vue elle-même
            uneVue.setTag(sauve);
        } else {
            // on récupère les identifiants du TextView stockés dans la vue
            sauve = (SauvegardeTextView) uneVue.getTag();
        }

        // on place dans le TextView les valeurs de l'item à afficher
        sauve.uneCuisson.setText(ligneCuisson.toString());
        return uneVue;
    }
}


